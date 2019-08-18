﻿# LBook
一个简单在线图书购买平台Demo，采用Spring Boot+SpringMVC+Vue开发


## 技术栈
### 后端技术栈
1.Maven

2.SpringBoot

3.SpringActuator

4.SpringSecurity

5.MyBatis

6.MySQL

7.Jackson

8.Lombok

9.Fastjson

### 前端技术栈
1.Vue

2.ElementUI

3.Bootstrap

4.axios

5.vue-router

6.vuex

7.vue-lazyload

8.vue-cli

## 功能
- 用户功能

- 首页

- 登入登出、登录态一定时间保留

- 浏览所有书籍并查看书籍详情

- 购物车

- 书籍搜索

- 下单

- 个人订单查看

- 个人购买统计

## 管理员功能(待完善)
- 书籍信息管理

- 用户权限管理

- 所有订单管理

- 搜索特定订单

- 统计数据管理


## 文档 
- [功能需求文档](docs/功能需求文档)

## 快速开始
#### L-book-client
1.克隆L-book-client到本地

2.安装依赖npm install

3.启动项目npm run serve

#### L-book-server
1.clone L-book-server到idea

2.修改数据库配置

3.运行

## 当前状态
1.关系数据库(Mysql)+Nosql数据库(MongoDB) 双数据库源管理数据

2.利用短信验证功能实现注册与登录

3.应用安全性保证(Spring Security)

4.实现邮件发送，并依此开发找回密码功能

