package com.example.springbootgettutor.intercepter;

import com.example.springbootgettutor.component.ResponseComponent;
import com.example.springbootgettutor.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TutorInterceptor implements HandlerInterceptor {
    @Autowired
    private ResponseComponent responseComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(responseComponent.getRole().equals(User.Role.TUTOR))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Without permission");
        }
        return true;
    }
}
