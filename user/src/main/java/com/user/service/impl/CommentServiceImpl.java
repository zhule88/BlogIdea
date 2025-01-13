package com.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.user.mapper.CommentMapper;
import com.user.pojo.comment;
import com.user.service.CommentService;
import org.springframework.stereotype.Service;
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, comment> implements CommentService {
}
