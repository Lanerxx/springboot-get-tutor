package com.example.springbootgettutor.controller;

import com.example.springbootgettutor.component.EncryptComponent;
import com.example.springbootgettutor.component.MyToken;
import com.example.springbootgettutor.entity.User;
import com.example.springbootgettutor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class LoginController {
    @Value("${my.tutor}")
    private String roleTutor;
    @Value("${my.student}")
    private String roleStudent;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EncryptComponent encrypt;

    @PostMapping("login")
    public Map login(@RequestBody User login, HttpServletResponse response) {
        User user = Optional.ofNullable(userService.getUserByNumber(login.getNumber()))
                .filter(u -> encoder.matches(login.getPassword(), u.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username and password"));

        MyToken token = new MyToken(user.getId(), user.getRole());
        String auth = encrypt.encryptToken(token);
        response.setHeader(MyToken.AUTHORIZATION, auth);
        String roleCode = user.getRole() == User.Role.TUTOR? roleTutor : roleStudent;
        return Map.of("role", roleCode);
    }
}
