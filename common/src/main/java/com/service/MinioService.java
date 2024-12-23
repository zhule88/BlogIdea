package com.service;

import cn.hutool.core.util.StrUtil;
import io.minio.*;

import io.minio.messages.DeleteObject;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MinioService {

    public MinioService(String bucketName) {
        this.bucketName = bucketName;
    }

    String bucketName;
    MinioClient minioClient = MinioClient.builder().
            endpoint("http://192.168.88.130:9000/").
            credentials("admin", "admin123").
            build();

    //上传文件
    public String addFile(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        String name = UUID.randomUUID().toString();
        String filename = name + "." + suffix;
        //文件存储
        long filesize = file.getSize();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .stream(file.getInputStream(), filesize, -1)
                .build());
        return filename;
    }
    //删除文件
    public void del(String filename) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .build());
    }
    //批量删除
   public void delall(List<String> list) {
       List<DeleteObject> deleteObjects = new ArrayList<>();
       for (String s : list) {
           deleteObjects.add(new DeleteObject(s));
       }
         minioClient.removeObjects(RemoveObjectsArgs.builder()
               .bucket(bucketName)
               .objects(deleteObjects)
               .build());
   }
}

