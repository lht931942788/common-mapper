package com.github.commonmapper.mapper.mapper;

import com.github.commonmapper.mapper.BaseMapper;
import com.github.commonmapper.mapper.entity.Demo;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoMapper extends BaseMapper<Demo, String> {
}
