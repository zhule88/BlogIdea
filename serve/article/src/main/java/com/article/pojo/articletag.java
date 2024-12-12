package com.article.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("article_tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class articletag {
   int articleId;
   int tagId;
}
