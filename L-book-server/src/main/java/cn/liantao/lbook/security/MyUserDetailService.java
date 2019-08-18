package cn.liantao.lbook.security;

import cn.liantao.lbook.entity.SecurityUser;
import cn.liantao.lbook.entity.User;
import cn.liantao.lbook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUser(username);
        SecurityUser securityUser = new SecurityUser();
        if (user == null) {
            securityUser.setEnabled(true);
            securityUser.setUsername("wrong"+username);
            securityUser.setPassword("wrong");
        } else {
            logger.info("登录用户名:" + username + "     数据库密码:" + user.getPassword());
            String password = passwordEncoder.encode(user.getPassword());

            securityUser.setEnabled(true);
            securityUser.setUsername(username);
            securityUser.setPassword(password);
            String role = user.getIsManager()? "ROLE_ADMIN" : "ROLE_USER";
            securityUser.setRoles(AuthorityUtils.commaSeparatedStringToAuthorityList(role));
        }

        return securityUser;
    }
}
