package com.example.springbootgettutor.component;

import com.example.springbootgettutor.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyToken {
    //Avoid hard coding
    public static final String AUTHORIZATION = "authorization";
    public static final String UID = "uid";
    public static final String ROLE = "role";

    private int uid;
    private User.Role role;
}
