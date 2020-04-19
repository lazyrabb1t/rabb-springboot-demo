package xyz.lazyrabbit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.lazyrabbit.constants.RabbConstants;
import xyz.lazyrabbit.model.User;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/basic")
public class BasicLoginController {


    @GetMapping("/login")
    public String login() {
        return "basic/login";
    }

    @GetMapping("/")
    public String index() {
        return "basic/index";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Object> login(HttpServletRequest request, String account, String password) {
        if ("admin@rabb.xyz".equals(account) && "123456".equals(password)) {
            request.getSession().setAttribute(RabbConstants.LOGIN_USER_SESSION_KEY, new User());
            return ResponseEntity.ok("login success");
        }
        return ResponseEntity.badRequest().body("error account or password");
    }
}
