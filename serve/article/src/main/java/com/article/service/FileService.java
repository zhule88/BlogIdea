package com.article.service;


import com.article.pojo.file;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface FileService  extends IService<file> {

    String getFileName(MultipartFile file);
    String addFile( MultipartFile file,String filename) throws Exception;
    void addSql(String filename,int articleId);
    void delall(int articleId);
}
