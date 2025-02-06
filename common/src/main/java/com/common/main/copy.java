package com.common.main;

import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import java.io.InputStream;
import java.util.List;

public class copy {
    public static void main(String[] args) throws Exception {
        String originHost = "192.168.88.130";
        String targetHost = "120.27.202.144";
        //配置原客户端
        MinioClient sourceClient = MinioClient.builder()
                .endpoint("http://"+originHost+":9000")
                .credentials("admin", "admin123")
                .build();
        // 配置目标客户端
        MinioClient targetClient = MinioClient.builder()
                .endpoint("http://"+targetHost+":9000")
                .credentials("zhule", "")
                .build();
        //桶名
        List<Bucket> buckets = sourceClient.listBuckets();
        // 遍历所有桶
        for (Bucket bucket : buckets) {
            String bucketName = bucket.name();
            Iterable<Result<Item>> objects = sourceClient
                    .listObjects(ListObjectsArgs.builder()
                            .bucket(bucketName).build());
            //遍历桶内对象，上传到目标客户端
            if(!targetClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                String policyJson = "{ \"Version\": \"2012-10-17\", \"Statement\": [ { \"Effect\": \"Allow\", \"Principal\": { \"AWS\": [ \"*\" ] }, \"Action\": [ \"s3:GetObject\" ], \"Resource\": [ \"arn:aws:s3:::" + bucketName + "/*\" ] } ] }";
                targetClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(policyJson).build());
            }
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
}
