package cn.liantao.lbook.service;

import cn.liantao.lbook.entity.LoginState;
import cn.liantao.lbook.entity.User;
import cn.liantao.lbook.entity.UserState;
import cn.liantao.lbook.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

//连接数据库User表的的具体实现类
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    //新增一个用户
    @Override
    public void create(String account, String password, String name, String mail) {
        userMapper.createUser(account, password, name, true, false, mail);
    }
    @Override
    public void create(String account, String password, String name, Boolean allowed, Boolean isManager, String mail) {
        userMapper.createUser(account, password, name, allowed, isManager, mail);
    }

    //获取单个用户数据
    @Override
    public User getUser(String account) {
        User user = userMapper.getUser(account);
        return user;
    }

    //判断用户状态
    @Override
    public LoginState getLoginState(String account, String password) {
        LoginState state = new LoginState();
        User user;

        user = userMapper.getLoginState(account, password);

        if (user == null){
            state.setIsLogin(false);
            state.setCode(0);
            state.setMessage("用户名密码错误");
        } else {
            if (user.getAllowed()) {
                if (user.getIsManager()) {
                    state.setIsLogin(true);
                    state.setCode(1);
                    state.setName(user.getName());
                    state.setAccount(user.getAccount());
                    state.setMessage("用户存在，为管理员");
                } else {
                    state.setIsLogin(true);
                    state.setCode(0);
                    state.setName(user.getName());
                    state.setAccount(user.getAccount());
                    state.setMessage("用户存在，为普通用户");
                }
            } else {
                state.setIsLogin(false);
                state.setCode(1);
                state.setMessage("用户被禁用");
            }
        }

        return state;
    }

    //禁用用户
    @Override
    public int banUser(String account) {
        int rows = userMapper.changeUser(false, account);
        return rows;
    }

    //解禁用户
    @Override
    public int allowUser(String account) {
        int rows = userMapper.changeUser(true, account);
        return rows;
    }


    @Override   //判断用户是否存在
    public Boolean ifExist(String account, String mail) {
        User user = userMapper.getUser(account);
        if(user != null) {
            return true;
        } else {
            user = userMapper.getUserWithMail(mail);
            if (user != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    //获得所有用户状态
    public List<UserState> getUserStates() {
        List<UserState> userStates;
        userStates = userMapper.getUserState();

        return userStates;
    }


}