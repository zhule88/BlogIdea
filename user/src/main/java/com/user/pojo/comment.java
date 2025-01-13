package com.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("comment")
@Data
public class comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private  Integer userId;

    private Integer articleId;

    private Integer parentId;

    private Integer replyId;

    private String content;

    private String bowser;

    private  String location;

    @JsonFormat(pattern="yyyy-MM-dd   HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;


}
