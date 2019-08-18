package cn.liantao.lbook.service;

import cn.liantao.lbook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{

    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;

    @Override
    public String sendMail(String phoneNumber) {
        User user = userService.getUser(phoneNumber);
        if (user == null)
            return "用户不存在";
        String password = user.getPassword();
        String mail = user.getMail();
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("756670011@qq.com");
        message.setTo(mail);
        message.setSubject("主题：EBook密码找回");
        message.setText("您的密码为 "+password);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return "发送失败";
        }

        return mail;
    }
}
