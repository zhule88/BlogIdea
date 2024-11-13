package com.article.mapper;

import com.article.pojo.article;
import com.article.pojo.articletag;
import com.article.pojo.tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleTagMapper  extends BaseMapper<articletag> {
}
