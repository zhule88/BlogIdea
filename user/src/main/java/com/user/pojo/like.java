package com.user.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("likes")
public class like {
    int id;
    int commentId;
    int userId;
}
