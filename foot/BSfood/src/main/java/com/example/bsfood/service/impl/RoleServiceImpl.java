package com.example.bsfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bsfood.entity.Role;
import com.example.bsfood.service.RoleService;
import com.example.bsfood.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author HYC
* @description 针对表【role】的数据库操作Service实现
* @createDate 2022-10-13 15:48:56
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




