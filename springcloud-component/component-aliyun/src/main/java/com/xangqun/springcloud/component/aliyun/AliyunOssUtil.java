/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author laixiangqun
 * @since 2018-8-29
 */
public class AliyunOssUtil {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AliyunOssUtil.class);

    private static final FastDateFormat TIMESTAMP_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd_HHmmss");

    protected static OssPoolManager ossPoolManager;

    public static void setOSSClient(OssPoolManager ossPoolManager) {
        AliyunOssUtil.ossPoolManager = ossPoolManager;
    }

    /**
     * 上传文件
     *
     * @param file
     * @param bucketName
     * @param fileHost
     * @return
     */
    public static String upload(File file, String bucketName, String fileHost) throws Exception {
        log.info("=========>OSS文件上传开始：" + file.getName());
        if (null == file) {
            return null;
        }
        String dateStr = TIMESTAMP_FORMAT.format(new Date());
        OSSClient ossClient = ossPoolManager.pool.borrowObject();
        try {
            //容器不存在，就创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            //创建文件路径
            String fileUrl = fileHost + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
            //上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            //设置权限 这里是公开读
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (null != result) {
                log.info("==========>OSS文件上传成功,OSS地址：" + fileUrl);
                return fileUrl;
            }
        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getMessage());
        } finally {
            ossPoolManager.pool.returnObject(ossClient);
        }
        return null;
    }

    /**
     * 删除Object
     *
     * @param fileKey
     * @return
     */
    public static String deleteBlog(String fileKey, String bucketName) throws Exception {
        log.info("=========>OSS文件删除开始");
        OSSClient ossClient = ossPoolManager.pool.borrowObject();
        try {
            if (!ossClient.doesBucketExist(bucketName)) {
                log.info("==============>您的Bucket不存在");
                return "您的Bucket不存在";
            } else {
                log.info("==============>开始删除Object");
                ossClient.deleteObject(bucketName, fileKey);
                log.info("==============>Object删除成功：" + fileKey);
                return "==============>Object删除成功：" + fileKey;
            }
        } catch (Exception ex) {
            log.info("删除Object失败", ex);
            return "删除Object失败";
        } finally {
            ossPoolManager.pool.returnObject(ossClient);
        }
    }

    /**
     * 查询文件名列表
     *
     * @param bucketName
     * @return
     */
    public static List<String> getObjectList(String bucketName) throws Exception {
        List<String> listRe = new ArrayList<>();
        OSSClient ossClient = ossPoolManager.pool.borrowObject();
        try {
            log.info("===========>查询文件名列表");
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
            //列出blog目录下今天所有文件
            listObjectsRequest.setPrefix(TIMESTAMP_FORMAT.format(new Date()) + "/");
            ObjectListing list = ossClient.listObjects(listObjectsRequest);
            for (OSSObjectSummary objectSummary : list.getObjectSummaries()) {
                listRe.add(objectSummary.getKey());
            }
            return listRe;
        } catch (Exception ex) {
            log.info("==========>查询列表失败", ex);
            return new ArrayList<>();
        } finally {
            ossPoolManager.pool.returnObject(ossClient);
        }
    }

}
