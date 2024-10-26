package com.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@TableName("category")
@Data
public class category {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotEmpty
    private String name;
}
