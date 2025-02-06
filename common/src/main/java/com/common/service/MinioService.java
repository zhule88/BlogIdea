package com.common.service;

import cn.hutool.core.util.StrUtil;
import io.minio.*;


import io.minio.http.Method;
import io.minio.messages.DeleteObject;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MinioService {


    @Resource
    String bucketName;

    MinioClient minioClient;
    @Autowired
    public MinioService( @Value("${host}") String host) {
        this.minioClient = MinioClient.builder()
                .endpoint("http://" + host + ":9000")
                .credentials("zhule", "")
                .build();
    }
    //上传文件
    public String addFile(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        String name = UUID.randomUUID().toString();
        String filename = name + "." + suffix;
        //文件存储
        long filesize = file.getSize();
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .stream(file.getInputStream(), filesize, -1)
                    .build());
        } catch (Exception e) {
            throw new Exception("文件上传失败");
        }
        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .method(Method.GET)
                .build());
        String[] s = url.split("\\?");
        return s[0];
    }
    //删除文件
    public void del(String filename) throws Exception {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
        } catch (Exception ignored) {
            throw new Exception("文件删除失败");
        }
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

