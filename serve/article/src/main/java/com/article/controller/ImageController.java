package com.article.controller;

import cn.hutool.core.util.StrUtil;
import com.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@Tag(name = "图片处理")
public class ImageController {

    final String prefix = "C:\\Users\\zhule\\Desktop\\blog\\public\\image\\";

    final String cover = prefix + "cover";

    final String article = prefix + "article\\stage";

    public Result upload(MultipartFile image, String url) {
        try {
            // 获取原始文件名称
            String originalFilename = image.getOriginalFilename();
            // 生成新文件名
            String suffix = StrUtil.subAfter(originalFilename, ".", true);
            String name = UUID.randomUUID().toString();
            String fileName = name + "." + suffix;
            // 保存文件
            image.transferTo(new File(url, fileName));
            return Result.success(fileName);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }


    @Operation(summary = "上传封面图")
    @PostMapping("/cover/upload")
    public Result coverUpload(@RequestPart("file") MultipartFile image) {
        return upload(image, cover);
    }

    @Operation(summary = "上传文章图片")
    @PostMapping("/article/upload")
    public Result articleUpload(@RequestPart("file") MultipartFile image) {
        return upload(image, article);
    }

    @Operation(summary = "删除图片")
    @DeleteMapping("/cover/delete")
    public Result coverDelete(String filename) {
        File delFile = new File(cover, filename);
        if (delFile.delete()) {
            return Result.success();
        }
        return Result.error("文件删除失败");
    }


    @Operation(summary = "删除并生成临时文件夹")
    @DeleteMapping("/article/init")
    public Result init() {
        File file = new File(article);
        if (file.exists()) {
            File[] list = file.listFiles();
            if (list != null) {
                for (File f : list) {
                    if (f.delete()) {
                        Result.error("文件删除失败");
                    }
                }
            }
        } else {
            if (file.mkdir()) {
                return Result.success("文件夹创建失败");
            }
            ;
        }
        return Result.success();
    }

    @Operation(summary = "临时文件夹改正式")
    @PostMapping("/article/mkdir")
    public Result mkdir(int id) {
        File oldFile = new File(article);
        File newFile = new File(id + "");
        if (oldFile.renameTo(newFile)) {
            return Result.success();
        }
        return Result.error("文件夹切换失败");
    }
}