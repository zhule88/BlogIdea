package com.article.controller;


import com.article.pojo.file;
import com.article.service.FileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.common.pojo.Result;
import com.common.service.MinioService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/file")
@Tag(name = "文件管理")

public class FileController {
    @Autowired
    FileService fileService;

    MinioService minioService;

    @Autowired
    public FileController(MinioService minioService) {
        this.minioService = minioService;
        this.minioService.setBucketName("article");
    }


    @Operation(summary = "获取文章所有文件名")
    @GetMapping("/list")
    public Result list(int id) {
        return Result.success(fileService.list(new LambdaQueryWrapper<file>().eq(file::getArticleId, id)));
    }

    @Operation(summary = "上传文件")
    @PostMapping("/add")
    public Result add(@RequestParam("file") MultipartFile ffile,
                      @RequestParam(required = false,value = "id") Integer articleId) throws Exception {
      String url = minioService.addFile(ffile);
      String filename = url.substring(url.lastIndexOf("/")+1);
      if(articleId != null) {
           file f= new file();
           f.setArticleId(articleId);
           f.setFilename(filename);
          fileService.save(f);
      }
        return Result.success(url);
    }
    @Operation(summary = "删除单个文件")
    @DeleteMapping("/del")
    public Result del(String filename) throws Exception {
        minioService.del(filename);
        fileService.remove(new LambdaQueryWrapper<file>().eq(file::getFilename,filename));
        return Result.success();
    }


}
