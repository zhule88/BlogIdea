package com.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tag")
@Data
public class tag {
    @TableId(value = "id", type = IdType.AUTO)
    int id;
    String name;
}
