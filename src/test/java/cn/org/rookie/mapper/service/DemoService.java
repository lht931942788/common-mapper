package cn.org.rookie.mapper.service;

import cn.org.rookie.mapper.entity.Demo;
import cn.org.rookie.mapper.mapper.DemoMapper;
import cn.org.rookie.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DemoService extends BaseServiceImpl<DemoMapper, Demo, String> {
}
