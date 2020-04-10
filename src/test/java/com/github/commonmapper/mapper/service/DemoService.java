package com.github.commonmapper.mapper.service;

import com.github.commonmapper.mapper.entity.Demo;
import com.github.commonmapper.mapper.mapper.DemoMapper;
import com.github.commonmapper.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DemoService extends BaseServiceImpl<DemoMapper, Demo, String> {
}
