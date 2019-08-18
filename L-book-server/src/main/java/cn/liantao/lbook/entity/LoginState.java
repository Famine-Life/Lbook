package cn.liantao.lbook.entity;

import lombok.Data;

@Data
public class LoginState {
    private Boolean isLogin;
    private Integer code;
    private String account;
    private String name;
    private String message;
}
