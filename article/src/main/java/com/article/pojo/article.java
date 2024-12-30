package com.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@TableName("article")
@Data
public class article {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 文章标题
     */
    @NotEmpty
    @Pattern(regexp = "^\\S{1,20}$")
    private String title;



    /**
     * 文章封面
     */
    @NotEmpty
    private String image;


    /**
     * 文章内容
     */
    @NotEmpty
    private String content;


    @NotEmpty
    private Integer categoryId;

    @TableField(exist = false)
    private List<Integer> tags;

    /**
     * 文章状态:，1隐藏, 2发布
     */
    @NotNull
    private Integer state;


    private Integer visitCount;

    /**
    * 是否置顶
    */
    @NotNull
    private Integer top;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd   HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd   HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

}
