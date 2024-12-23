package com.main;

import io.minio.*;
import io.minio.messages.Item;

import java.io.*;


//迁移minio文件
public class clone {
    public static void main(String[] args) throws Exception {
        //配置原客户端
        MinioClient sourceClient = MinioClient.builder()
                .endpoint("http://localhost:9000/")
                .credentials("minioadmin", "minioadmin")
                .build();
        // 配置目标客户端
        MinioClient targetClient = MinioClient.builder()
                .endpoint("http://192.168.88.130:9000/")
                .credentials("admin", "admin123")
                .build();
        //桶名
        String bucketName = "article";
            Iterable<Result<Item>> objects = sourceClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
            //遍历桶内对象，上传到目标客户端
            for (Result<Item> objectResult : objects) {
                Item object = objectResult.get();
                InputStream in = sourceClient.getObject(
                        GetObjectArgs.builder().bucket(bucketName).object(object.objectName()).build());
                    targetClient.putObject(
                                PutObjectArgs.builder()
                                        .bucket(bucketName)
                                        .object(object.objectName())
                                        .stream(in, object.size(), -1)
                                        .build());
                      }
                }
       }
