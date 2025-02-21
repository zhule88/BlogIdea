package com.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.user.mapper.CoverMapper;
import com.user.pojo.cover;
import com.user.service.CoverService;
import org.springframework.stereotype.Service;


@Service
public class CoverServiceImpl extends ServiceImpl<CoverMapper, cover> implements CoverService {

}