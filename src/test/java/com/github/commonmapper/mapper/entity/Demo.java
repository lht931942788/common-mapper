package com.github.commonmapper.mapper.entity;

import com.github.commonmapper.mapper.annotation.JoinTable;
import com.github.commonmapper.mapper.annotation.Primary;
import com.github.commonmapper.mapper.annotation.Table;
import com.github.commonmapper.mapper.mapper.TestMapper;

import java.util.List;

@Table("admin_user")
public class Demo {

    @Primary
    private String id;

    private String username;
    private String password;

    @JoinTable(mappedClass = TestMapper.class)
    private List<Test> tests;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}