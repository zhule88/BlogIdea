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
@RequestMapping("/cover")
@Tag(name = "图片处理")
public class CoverController {


    @Operation(summary = "上传图片")
    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile image) {
        try {
            // 获取原始文件名称
            String originalFilename = image.getOriginalFilename();
            // 生成新文件名
            String suffix = StrUtil.subAfter(originalFilename, ".", true);
            String name = UUID.randomUUID().toString();
            String fileName = name + "."+suffix;
            // 保存文件
            if (originalFilename != null) {
                image.transferTo(new File("C:\\Users\\zhule\\Desktop\\blog\\public\\image", fileName));
            }else{
                return Result.error("文件名空");
            }
            return Result.success(fileName);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }
    @Operation(summary = "删除图片")
    @DeleteMapping("/delete")
    public Result delete(String name) {
        File delFile = new File(name);
        if (delFile.delete()) {
            return Result.success();
        }
        return Result.error("文件删除失败");
    }
}