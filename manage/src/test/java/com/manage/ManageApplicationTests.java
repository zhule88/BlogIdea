package com.manage;

import com.article.pojo.article;
import com.article.service.ArticleService;
import com.user.service.UserService;
import io.minio.*;
import io.minio.messages.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.List;


@SpringBootTest

class ManageApplicationTests {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;


    String host = "192.168.88.130";

    @Test
    void urlUpdate (){
        String url = "http://" + host+ ":9000/article";
        List<article> l1 = articleService.lambdaQuery().list();
        l1.forEach(a -> a.setImage(a.getImage().replace("article","article/")));
        articleService.updateBatchById(l1);
       /* List<user> l2 = userService.lambdaQuery().list();
        l2.forEach(a -> a.setAvatar(url+a.getAvatar()));
        userService.updateBatchById(l2);*/
    }
    @Test
   void clonee() throws Exception{
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
        Iterable<io.minio.Result<Item>> objects = sourceClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
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
        List<article> l = articleService.list();
        l.forEach(item->{
            item.setContent(item.getContent().replace("localhost","192.168.88.130"));
            articleService.updateById(item);
        });
    }


}
