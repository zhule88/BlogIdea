package com.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("user")
@Data
public class user {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;
    private String password;

    private String avatar;
    @Email
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd   HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;


}
