package xyz.lazyrabbit.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.lazyrabbit.entity.User;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {


    @GetMapping("/")
    public String index() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        if (user != null) {
            return "index current login user :" + user.getUserName();
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(User user, HttpServletRequest request) {
        try {
            // 根据用户名和密码创建 Token
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
            // 获取 subject 认证主体
            Subject subject = SecurityUtils.getSubject();
            // 开始认证，这一步会跳到我们自定义的 Realm 中
            subject.login(token);
            return "login sucess";
        } catch (Exception e) {
            return "fail：" + e.getMessage();
        }
    }

    @GetMapping("/logout")
    public String logout() {
        // 获取 subject 认证主体
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "logout success";
    }

    @GetMapping("/auth")
    public String user() {
        return "user admin";
    }

    @GetMapping("/role")
    public String role() {
        return "role admin";
    }

    @GetMapping("/permission")
    public String permission() {
        return "permission edit";
    }
}
