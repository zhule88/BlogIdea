package com.article.service.impl;

import cn.hutool.core.util.StrUtil;
import com.article.mapper.FileMapper;

import com.article.pojo.file;

import com.article.service.FileService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, file> implements FileService {
    final String bucketName = "article" ;

    @Autowired
    MinioClient minioClient;
    @Autowired
    FileMapper fileMapper;

    @Override
    public String getFileName(MultipartFile file) {
        //文件命名
        String originalFilename = file.getOriginalFilename();
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        String name = UUID.randomUUID().toString();
        return name +"."+ suffix;
    }

    @Override
    public String addFile(MultipartFile file,String filename) throws Exception {
        //文件存储
        long filesize = file.getSize();
        minioClient.putObject( PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .stream(file.getInputStream(), filesize, -1)
                .build());
        //返回访问地址
        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .method(Method.GET)
                .build());
        String[] s = url.split("\\?");
        return s[0];
    }

    @Override
    public void addSql(String filename, int articleId) {
        file file = new file();
        file.setFilename(filename);
        file.setArticleId(articleId);
        fileMapper.insert(file);
    }

    @Override
    public void delall(int articleId) throws Exception {
        List<file> list =  lambdaQuery().eq(file::getArticleId,articleId)
                .select(file::getFilename)
                .list();
        List<DeleteObject> deleteObjects = new ArrayList<>();
        for (file file : list) {
            deleteObjects.add(new DeleteObject(file.getFilename()));
        }
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder()
                .bucket(bucketName)
                .objects(deleteObjects)
                .build());
        for (Result<DeleteError> result : results) {
            DeleteError error = result.get();
            log.error("Error in deleting object " + error.objectName() + "; " + error.message());
        }
        lambdaUpdate().eq(file::getArticleId,articleId)
                .remove();
    }

}
