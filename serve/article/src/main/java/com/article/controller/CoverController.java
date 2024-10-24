package com.article.controller;

import com.pojo.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/cover")
public class CoverController {

    @PostMapping("/upload")
    public Result uploadImage(@RequestParam("file") MultipartFile image) {
        try {
            // 获取原始文件名称
            String originalFilename = image.getOriginalFilename();
            // 生成新文件名

            // 保存文件
            if (originalFilename != null) {
                image.transferTo(new File("C:\\Users\\zhule\\Desktop\\image", originalFilename));
            }else{
                return Result.error("文件名空");
            }
            // 返回结果
            return Result.success(originalFilename);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }


}