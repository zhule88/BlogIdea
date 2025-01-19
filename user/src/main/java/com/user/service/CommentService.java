package com.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.user.pojo.comment;
import com.user.pojo.commentVo;

import java.util.List;


public interface CommentService extends IService<comment> {
    List<commentVo> page(int id,int current,int size);
    List<commentVo> setUser(List<comment> list);
}
