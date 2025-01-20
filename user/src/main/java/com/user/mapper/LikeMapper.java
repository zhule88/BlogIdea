package com.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.user.pojo.comment;
import com.user.pojo.like;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface LikeMapper extends BaseMapper<like>{
}
