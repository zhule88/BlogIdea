package com.article.controller;

import cn.hutool.core.util.StrUtil;
import com.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@Tag(name = "图片处理")
public class ImageController {

    final String prefix = "C:\\Users\\zhule\\Desktop\\blog\\public\\image\\";

    final String cover = prefix + "article\\";

    final String article = prefix + "article\\stage";

    public boolean delete(File file) {
        if (file.exists()) {
            File[] list = file.listFiles();
            if (list != null) {
                for (File f : list) {
                    f.delete();
                }
            }
            return false;
        }
        return true;
    }

    @Operation(summary = "获取所有图片")
    @GetMapping("/list")
    public Result list(int id){
        File file = new File(cover +id);
        List<String> ans = new ArrayList<>();
        File[] list = file.listFiles();
        if (list != null) {
            for (File f : list) {
                ans.add(f.getName());
            }
        }
        return Result.success(ans);
    }


    @Operation(summary = "上传文章封面")
    @PostMapping("/cover/upload")
    public Result coverUpload(@RequestPart("file")MultipartFile image,@RequestParam(required = false) Integer id) throws IOException {
        //获取文件后缀
        String originalFilename = image.getOriginalFilename();
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        //根据数量生成id
        String fileName = "0." + suffix;
        // 保存文件
        if (id == null) {
            image.transferTo(new File( article, fileName));
        }else{
            image.transferTo(new File( cover+id, fileName));
        }
        return Result.success(fileName);
    }

    @Operation(summary = "上传文章图片")
    @PostMapping("/article/upload")
    public Result indexUpload(@RequestPart("file")MultipartFile image,@RequestParam(required = false) Integer id) throws IOException {
           //获取文件后缀
            String originalFilename = image.getOriginalFilename();
            String suffix = StrUtil.subAfter(originalFilename, ".", true);
            //根据数量生成id
            File file;
            if(id == null){
                file  = new File(article);
            }else{
                file  = new File(cover+id);
            }
            File[] list = file.listFiles();
            String name = null;
            if (list != null) {
                name = list.length+"";
            }
            String fileName = name + "." + suffix;
            // 保存文件
            if (id == null) {
                image.transferTo(new File( article, fileName));
            }else{
                image.transferTo(new File( cover+id, fileName));
            }
            return Result.success(fileName);
        }

    @Operation(summary = "删除单个图片")
    @DeleteMapping("/delete")
    public Result coverDelete(String filename,int id) {
        File delFile = new File(cover+id, filename);
        if (delFile.delete()) {
            return Result.success();
        }
        return Result.error("文件删除失败");
    }

    @Operation(summary = "删除文章图片文件夹")
    @GetMapping("/article/mkdirdel")
    public Result mkdirDelete(int id) {
        File delFile = new File(cover+id);
        delete(delFile);
       delFile.delete();
        return Result.success();
    }

    @Operation(summary = "生成临时文件夹")
    @PostMapping("/article/init")
    public Result init() {
        File file = new File(article);
        if (delete(file)) {
            file.mkdir();
        }
        return Result.success();
    }

    @Operation(summary = "临时文件夹改正式")
    @GetMapping("/article/mkdir")
    public Result mkdir(int id) {
        File oldFile = new File(article);
        File newFile = new File(cover+id);
        if (oldFile.renameTo(newFile)) {
            return Result.success();
        }
        return Result.error("文件夹切换失败");
    }
}