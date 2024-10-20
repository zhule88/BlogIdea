package com.article.mapper;

import com.article.pojo.article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface ArticleMapper extends BaseMapper<article> {
}
