package cn.org.rookie.mapper.entity;

import cn.org.rookie.mapper.annotation.Primary;
import cn.org.rookie.mapper.annotation.Table;

import java.util.Date;

@Table("demo")
public class Demo {

    @Primary
    private String id;

    private String name;

    private String birthday;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
