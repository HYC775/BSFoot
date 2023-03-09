package com.example.bsfood.controller;

import com.example.bsfood.entity.User;
import com.example.bsfood.entity.UserRole;
import com.example.bsfood.service.UserRoleService;
import com.example.bsfood.service.UserService;
import com.example.bsfood.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/save")
    public Result save(@RequestBody User user){
        if(user.getId()==null ||user.getId()==-1){
            user.setCreateTime(new Date());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.save(user);
        }
        UserRole userRole = new UserRole();
        User user1 = userService.getByUsername(user.getUsername());
        userRole.setUserId((Long)user1.getId());
        userRole.setRoleId(2L);
        userRoleService.save(userRole);
        return Result.ok();
    }


    /**
     * 验证用户名
     * @param user
     * @return
     */
    @PostMapping("/checkUserName")
    public Result checkUserName(@RequestBody User user){
        if(userService.getByUsername(user.getUsername())==null){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}
