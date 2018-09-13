/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.consumerfeign.upload;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author laixiangqun
 * @since 2018-8-15
 */
@Controller("upload")
public class UploadController {
    /**
     * 文件上传实现
     * @param session
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/doUpload")
    public String doUpload(HttpSession session, HttpServletRequest request) throws IOException {
        long startTime = System.currentTimeMillis();
        //获取存储app文件夹的路径
        String appPath = session.getServletContext().getRealPath("/app");
        File appRootDir = new File(appPath);
        if (!appRootDir.exists()) {
            System.out.println("存储app的文件夹不存在 appPath= " + appPath);
            appRootDir.mkdirs();
        } else {
            System.out.println("存储app的文件夹存在 appPath= " + appPath);
        }
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> names = multiRequest.getFileNames();
            while (names.hasNext()) {
                MultipartFile file = multiRequest.getFile(names.next());
                if (file != null) {
                    File appFile = new File(appRootDir, file.getOriginalFilename());
                    file.transferTo(appFile);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("上传时间：" + String.valueOf(endTime - startTime) + "ms");
        return "home";
    }

    /**
     * upload 有进度的上传文件
     */
    @RequestMapping(value = "/testbar", method = RequestMethod.POST)
    public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile( iter.next());
                if (file != null) {
                    String fileName = file.getOriginalFilename();
                    String path = "D:/" + fileName;
                    File localFile = new File(path);
                    file.transferTo(localFile);
                }
            }
        }
//        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
//        MultipartFile file = multipartRequest.getFile("file");
//        InputStream inputStream = file.getInputStream();
//        FileUtils.copyInputStreamToFile(inputStream, new File("e://"+file.getOriginalFilename()));
        return mv;
    }


    /**
     * process 获取进度
     */
    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public @ResponseBody
    Object process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ProgressEntity)request.getSession().getAttribute("status");
    }
}
