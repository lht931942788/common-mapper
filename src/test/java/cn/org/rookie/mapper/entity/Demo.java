package cn.org.rookie.mapper.entity;

import cn.org.rookie.mapper.annotation.Association;
import cn.org.rookie.mapper.annotation.Column;
import cn.org.rookie.mapper.annotation.JoinColumn;
import cn.org.rookie.mapper.annotation.Table;

@Table("admin_user")
public class Demo {

    @Column(primary = true)
    private String id;
    @JoinColumn(tableName = "demo", column = "user_id", relations = {@Association(target = "id", association = "user_id")})
    private String username;
    private String password;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}