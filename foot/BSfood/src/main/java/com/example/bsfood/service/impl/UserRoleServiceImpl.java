package com.example.bsfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bsfood.entity.UserRole;
import com.example.bsfood.service.UserRoleService;
import com.example.bsfood.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author HYC
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2022-10-13 15:48:56
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




