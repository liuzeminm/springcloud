/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.consumerfeign.schedule;

import com.xxl.job.core.biz.api.JobApi;
import com.xxl.job.core.biz.api.JobInfoBuilder;
import com.xxl.job.core.biz.model.JobInfo;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author laixiangqun
 * @version 1.0.0
 * @ClassName ScheduleController.java
 * @Description TODO
 * @createTime 2018年09月13日 18:27
 */
@Controller
public class JobController {

    @RequestMapping(value = "addJob", method = RequestMethod.GET)
    public String addJob() throws Exception {
        //生成一些默认的配置项
        JobInfo jobInfo = JobInfoBuilder.newDefaultJob();
        //所在组件的微服务名称（跟application.properties里的spring.application.name名称一样）
        jobInfo.setAppName("微服务名称");
        jobInfo.setJobDesc("任务描述");
        //自己任务的cron表达式
        jobInfo.setJobCron("0/30 * * * * ?");
        //自己任务的executorHandler
        jobInfo.setExecutorHandler("hellojob");
        //负责人
        jobInfo.setAuthor("laixiangqun");

        //这个接口调用一次就会添加一次，不会判断是否重复
        ReturnT<Integer> result = JobApi.addJob(jobInfo);
        if (ReturnT.SUCCESS_CODE == result.getCode()) {
            //可以保存这个jobId，以方便后续调用删除接口
            int jobId = result.getContent();
        } else {
            //错误信息
            String msg = result.getMsg();
            System.out.println("添加失败，失败信息:" + msg);
        }
        return "OK";
    }

    @RequestMapping(value = "addJobIfNotExist", method = RequestMethod.GET)
    public String addJobIfNotExist() throws Exception {
        //生成一些默认的配置项
        JobInfo jobInfo = JobInfoBuilder.newDefaultJob();
        //所在组件的微服务名称（跟application.properties里的spring.application.name名称一样）
        jobInfo.setAppName("微服务名称");
        jobInfo.setJobDesc("任务描述");
        //自己任务的cron表达式
        jobInfo.setJobCron("0/30 * * * * ?");
        //自己任务的executorHandler
        jobInfo.setExecutorHandler("hellojob");
        //负责人
        jobInfo.setAuthor("laixiangqun");

        //这个接口调用一次就会添加一次，不会判断是否重复
        ReturnT<Integer> result = JobApi.addJobIfNotExist(jobInfo);
        if (ReturnT.SUCCESS_CODE == result.getCode()) {
            //可以保存这个jobId，以方便后续调用删除接口
            int jobId = result.getContent();
        } else {
            //错误信息
            String msg = result.getMsg();
            System.out.println("添加失败，失败信息:" + msg);
        }
        return "OK";
    }

    @RequestMapping(value = "delJobById", method = RequestMethod.GET)
    public String delJobById(int jobId) throws Exception {
        //根据jobId来删除（这个jobId是调用JobApi.addJob接口添加任务时，返回的jobId)
        //如果jobId不存在，一样会返回成功操作，只是返回的msg里带有说明任务不存在的信息
        ReturnT<String> result = JobApi.removeJob(jobId);
        if (ReturnT.SUCCESS_CODE == result.getCode()) {
            System.out.println("删除Job成功");
        } else {
            String msg = result.getMsg();
            System.out.println("删除失败，失败信息:" + msg);
        }
        return "OK";
    }
    /**
     *
     * @param appName 微服务名称
     * @param executorHandler 执行器名称
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "pauseJob", method = RequestMethod.GET)
    public String pauseJob(String appName,String executorHandler) throws Exception {
        //根据jobId来删除（这个jobId是调用JobApi.addJob接口添加任务时，返回的jobId)
        //如果jobId不存在，一样会返回成功操作，只是返回的msg里带有说明任务不存在的信息
        ReturnT<String> result = JobApi.pauseJob(appName, executorHandler);
        if (ReturnT.SUCCESS_CODE == result.getCode()) {
            //暂停成功
        } else {
            String msg = result.getMsg();
            System.out.println("暂停失败，错误信息:" + msg);
        }
        return "OK";
    }

    /**
     *
     * @param appName 微服务名称
     * @param executorHandler 执行器名称
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "reuseJob", method = RequestMethod.GET)
    public String reuseJob(String appName,String executorHandler) throws Exception {
        //根据jobId来删除（这个jobId是调用JobApi.addJob接口添加任务时，返回的jobId)
        //如果jobId不存在，一样会返回成功操作，只是返回的msg里带有说明任务不存在的信息
        ReturnT<String> result = JobApi.reuseJob(appName, executorHandler);
        if (ReturnT.SUCCESS_CODE == result.getCode()) {
            //暂停成功
        } else {
            String msg = result.getMsg();
            System.out.println("恢复失败，错误信息:" + msg);
        }
        return "OK";
    }

    /**
     *
     * @param appName 微服务名称
     * @param executorHandler 自己弄的定时任务上面的executorHandler的名称
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "delJob", method = RequestMethod.GET)
    public String delJob(String appName,String executorHandler) throws Exception {
        //根据appName和executorHandler来删除
        //根据jobId来删除（这个jobId是调用JobApi.addJob接口添加任务时，返回的jobId)
        //如果jobId不存在，一样会返回成功操作，只是返回的msg里带有说明任务不存在的信息
        ReturnT<String> result = JobApi.removeJob(appName, executorHandler);
        if (ReturnT.SUCCESS_CODE == result.getCode()) {
            System.out.println("删除Job成功");
        } else {
            String msg = result.getMsg();
            System.out.println("删除失败，失败信息:" + msg);
        }
        return "OK";
    }

    /**
     *
     * @param appName 微服务名称
     * @param executorHandler 自己弄的定时任务上面的executorHandler的名称
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "checkJobExist", method = RequestMethod.GET)
    public String checkJobExist(String appName,String executorHandler) throws Exception {
        //根据appName和executorHandler来删除
        //根据jobId来删除（这个jobId是调用JobApi.addJob接口添加任务时，返回的jobId)
        //如果jobId不存在，一样会返回成功操作，只是返回的msg里带有说明任务不存在的信息
        ReturnT<Boolean> result = JobApi.checkJobExist(appName, executorHandler);
        if (ReturnT.SUCCESS_CODE == result.getCode()) {
            boolean exist = result.getContent();
            if (exist) {
                System.out.println("任务存在");
            } else {
                System.out.println("任务不存在");
            }
        } else {
            //表示接口操作出错
            String msg = result.getMsg();
            System.out.println("接口出错，错误信息:" + msg);
        }
        return "OK";
    }
}
