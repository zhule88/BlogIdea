package com.article.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("article_tag")
@Data
public class articletag {
   int articleId;
   int tagId;
}
