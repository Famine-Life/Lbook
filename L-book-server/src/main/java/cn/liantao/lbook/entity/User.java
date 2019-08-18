package cn.liantao.lbook.entity;

import lombok.Data;

//User实体定义
@Data
public class User {
    private String account;
    private String password;
    private String name;
    private String code;
    private Boolean allowed;
    private Boolean isManager;
    private String mail;
}