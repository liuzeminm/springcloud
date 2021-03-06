package com.xxl.job.core.executor;

import com.netflix.appinfo.InstanceInfo;
import com.xxl.job.core.rpc.netcom.NetComEurekaClientProxy;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.impl.ExecutorBizImpl;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobFileAppender;
import com.xxl.job.core.rpc.netcom.NetComClientProxy;
import com.xxl.job.core.rpc.netcom.NetComServerFactory;
import com.xxl.job.core.rpc.netcom.jetty.client.ScheduleClient;
import com.xxl.job.core.thread.JobLogFileCleanThread;
import com.xxl.job.core.thread.JobThread;
import com.xxl.job.core.thread.TriggerCallbackThread;
import com.xxl.job.core.util.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xuxueli on 2016/3/2 21:14.
 */
public class XxlJobExecutor implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobExecutor.class);

    // ---------------------- param ----------------------
    //eureka调度服务名
    private String scheduleServerName;
    private String adminAddresses;
    private String appName;
    private String ip;
    private int port;
    private String accessToken;
    private String logPath;
    private int logRetentionDays;

    public ScheduleClient scheduleClientx;

    public void setAdminAddresses(String adminAddresses) {
        this.adminAddresses = adminAddresses;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
    public void setLogRetentionDays(int logRetentionDays) {
        this.logRetentionDays = logRetentionDays;
    }
    public void setScheduleServerName(String scheduleServerName) {
        this.scheduleServerName = scheduleServerName;
    }

    public void setScheduleClientx(ScheduleClient scheduleClientx) {
        this.scheduleClientx = scheduleClientx;
    }

    // ---------------------- applicationContext ----------------------
    private static ApplicationContext applicationContext;

    public static DiscoveryClient discoveryClient;
    public static ScheduleClient scheduleClient;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        discoveryClient=applicationContext.getBean(DiscoveryClient.class);
        scheduleClient=applicationContext.getBean(ScheduleClient.class);
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    // ---------------------- start + stop ----------------------
    @PostConstruct
    public void start() throws Exception {
        // init admin-client
        initAdminBizList(adminAddresses,scheduleServerName, accessToken);

        // init executor-jobHandlerRepository
        initJobHandlerRepository(applicationContext);

        // init logpath
        XxlJobFileAppender.initLogPath(logPath);

        // init executor-server
        initExecutorServer(port, ip, appName, accessToken);

        // Start Callback-Server
        logger.info("XxlJobExecutor start TriggerCallbackThread");
        TriggerCallbackThread.getInstance().start();

        // init JobLogFileCleanThread
        JobLogFileCleanThread.getInstance().start(logRetentionDays);
    }

    @PreDestroy
    public void destroy(){
        // destory JobThreadRepository
        if (JobThreadRepository.size() > 0) {
            for (Map.Entry<Integer, JobThread> item: JobThreadRepository.entrySet()) {
                removeJobThread(item.getKey(), "web container destroy and kill the job.");
            }
            JobThreadRepository.clear();
        }
      // destory executor-server
        if(scheduleServerName==null){
            stopExecutorServer();
        }
        // destory executor-server
        TriggerCallbackThread.getInstance().toStop();

        // destory JobLogFileCleanThread
        JobLogFileCleanThread.getInstance().toStop();
    }

    static Timer timer = new Timer();
    // ---------------------- admin-client ----------------------
    private static Set<FactoryBean> adminBizList;
    private static void initAdminBizList(String adminAddresses,final String scheduleServerName,final String accessToken) throws Exception {
        if (adminAddresses!=null && adminAddresses.trim().length()>0) {
            for (String address: adminAddresses.trim().split(",")) {
                if (address!=null && address.trim().length()>0) {
                    String addressUrl = address.concat(AdminBiz.SERVER_MAPPING);
                    FactoryBean adminBiz = new NetComClientProxy(AdminBiz.class, addressUrl, accessToken);
                    if (adminBizList == null) {
                        adminBizList = new HashSet<FactoryBean>();
                    }
                    adminBizList.add(adminBiz);
                }
            }
        }else if(scheduleServerName!=null){
            //前一次执行程序结束后 30s 后开始执行下一次程序
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        List<ServiceInstance> instances = discoveryClient.getInstances(scheduleServerName);
                        Set<FactoryBean> adminBizSetTemp = new HashSet<>();
                        for (ServiceInstance instance: instances) {
                            InstanceInfo instanceInfo = ((EurekaDiscoveryClient.EurekaServiceInstance) instance).getInstanceInfo();
                            String address= String.format("%s:%s", instanceInfo.getIPAddr(), instanceInfo.getPort());
                            if (address!=null && address.trim().length()>0) {
                                String addressUrl = address.concat(AdminBiz.SERVER_MAPPING);
                                FactoryBean adminBiz = new NetComEurekaClientProxy(AdminBiz.class, addressUrl, accessToken,scheduleClient,scheduleServerName);
                                adminBizSetTemp.add(adminBiz);
                            }
                        }
                        adminBizList = adminBizSetTemp;
                    }catch (Exception e){
                        logger.error("error",e);
                    }
                }
            }, 0,30000);
        }
    }
    public static Set<FactoryBean> getAdminBizList(){
        return adminBizList;
    }


    // ---------------------- executor-server(jetty) ----------------------
    private NetComServerFactory serverFactory = new NetComServerFactory();
    private void initExecutorServer(int port, String ip, String appName, String accessToken) throws Exception {
        // valid param
        port = port>0?port: NetUtil.findAvailablePort(9999);

        // start server
        NetComServerFactory.putService(ExecutorBiz.class, new ExecutorBizImpl());   // rpc-service, base on jetty
        NetComServerFactory.setAccessToken(accessToken);
        serverFactory.start(port, ip, appName); // jetty + registry
    }
    private void stopExecutorServer() {
        serverFactory.destroy();    // jetty + registry + callback
    }


    // ---------------------- job handler repository ----------------------
    private static ConcurrentHashMap<String, IJobHandler> jobHandlerRepository = new ConcurrentHashMap<String, IJobHandler>();
    public static IJobHandler registJobHandler(String name, IJobHandler jobHandler){
        logger.info(">>>>>>>>>>> xxl-job register jobhandler success, name:{}, jobHandler:{}", name, jobHandler);
        return jobHandlerRepository.put(name, jobHandler);
    }
    public static IJobHandler loadJobHandler(String name){
        return jobHandlerRepository.get(name);
    }
    private static void initJobHandlerRepository(ApplicationContext applicationContext){
        if (applicationContext == null) {
            return;
        }

        // init job handler action
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(JobHandler.class);

        if (serviceBeanMap!=null && serviceBeanMap.size()>0) {
            for (Object serviceBean : serviceBeanMap.values()) {
                if (serviceBean instanceof IJobHandler){
                    String name = serviceBean.getClass().getAnnotation(JobHandler.class).value();
                    IJobHandler handler = (IJobHandler) serviceBean;
                    if (loadJobHandler(name) != null) {
                        throw new RuntimeException("xxl-job jobhandler naming conflicts.");
                    }
                    registJobHandler(name, handler);
                }
            }
        }
    }


    // ---------------------- job thread repository ----------------------
    private static ConcurrentHashMap<Integer, JobThread> JobThreadRepository = new ConcurrentHashMap<Integer, JobThread>();
    public static JobThread registJobThread(int jobId, IJobHandler handler, String removeOldReason){
        JobThread newJobThread = new JobThread(jobId, handler);
        newJobThread.start();
        logger.info(">>>>>>>>>>> xxl-job regist JobThread success, jobId:{}, handler:{}", new Object[]{jobId, handler});

        JobThread oldJobThread = JobThreadRepository.put(jobId, newJobThread);	// putIfAbsent | oh my god, map's put method return the old value!!!
        if (oldJobThread != null) {
            oldJobThread.toStop(removeOldReason);
            oldJobThread.interrupt();
        }

        return newJobThread;
    }
    public static void removeJobThread(int jobId, String removeOldReason){
        JobThread oldJobThread = JobThreadRepository.remove(jobId);
        if (oldJobThread != null) {
            oldJobThread.toStop(removeOldReason);
            oldJobThread.interrupt();
        }
    }
    public static JobThread loadJobThread(int jobId){
        JobThread jobThread = JobThreadRepository.get(jobId);
        return jobThread;
    }

}
