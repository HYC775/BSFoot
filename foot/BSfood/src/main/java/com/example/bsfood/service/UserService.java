package com.example.bsfood.service;

import com.example.bsfood.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author HYC
* @description 针对表【user】的数据库操作Service
* @createDate 2022-10-10 15:47:03
*/
public interface UserService extends IService<User> {


    User getByUsername(String username);
    String getUserAuthorityInfo(Long userId);
}
