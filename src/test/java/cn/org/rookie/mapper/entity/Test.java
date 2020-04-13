package cn.org.rookie.mapper.entity;

import cn.org.rookie.mapper.annotation.Primary;
import cn.org.rookie.mapper.annotation.Table;

@Table("admin_user")
public class Test {

    @Primary
    private String id;

    private String username;

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

}
