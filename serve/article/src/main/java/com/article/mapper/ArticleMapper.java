package com.article.mapper;

import com.article.pojo.article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface ArticleMapper extends BaseMapper<article> {

    @Select("select MAX(id) from article;")
    int newIdget();
}
