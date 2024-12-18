package com.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.user.mapper.LinkMapper;
import com.user.pojo.link;
import com.user.service.LinkService;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, link> implements LinkService {
}
