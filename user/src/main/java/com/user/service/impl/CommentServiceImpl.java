package com.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.pojo.page;
import com.user.mapper.CommentMapper;
import com.user.pojo.comment;
import com.user.pojo.commentVo;
import com.user.pojo.like;
import com.user.pojo.user;
import com.user.service.CommentService;
import com.user.service.LikeService;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, comment> implements CommentService {

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;

    @Override
    public List<commentVo> page(int id, int current,int size) {
        Page<comment> pa = Page.of(current, size);
        pa.addOrder(new OrderItem("create_time", false));
        lambdaQuery().eq(comment::getArticleId, id).isNull(comment::getParentId).page(pa);
        page<comment> p = new page<>(pa.getTotal(), pa.getRecords());
        return setUser(p.getRecords());
    }

    public List<commentVo> setUser(List<comment> l){
        List<commentVo> ll =  new ArrayList<>();
        for(comment c:l) {
            user u = userService.getById(c.getUserId());
            commentVo cv = new commentVo();
            BeanUtil.copyProperties(c, cv);
            cv.setLike(Math.toIntExact(likeService.lambdaQuery().eq(like::getCommentId, c.getId()).count()));
            cv.setAvatar(u.getAvatar());
            cv.setUsername(u.getUsername());
            ll.add(cv);
        }
        return ll;
    }
}
