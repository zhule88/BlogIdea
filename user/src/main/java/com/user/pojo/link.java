package com.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.time.LocalDateTime;



@TableName("link")
@Data
public class link {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private  String nickname;

    private String avatar;

    private  String description;

    @JsonFormat(pattern="yyyy-MM-dd   HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;



}
