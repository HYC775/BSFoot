package com.example.bsfood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bsfood.entity.Menu;
import com.example.bsfood.entity.Role;
import com.example.bsfood.entity.User;
import com.example.bsfood.mapper.MenuMapper;
import com.example.bsfood.mapper.RoleMapper;
import com.example.bsfood.service.UserService;
import com.example.bsfood.mapper.UserMapper;
import com.example.bsfood.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author HYC
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-10-10 15:47:03
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public User getByUsername(String username) {
        return getOne(new QueryWrapper<User>().eq("username",username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        StringBuffer authority=new StringBuffer();
        // 根据用户id获取所有的角色
        List<Role> roleList = roleMapper.selectList(new QueryWrapper<Role>
                ().inSql("id", "SELECT role_id FROM user_role WHERE user_id=" + userId));
        if(roleList.size()>0){
            String roleCodeStrs = roleList.stream().map(r -> "ROLE_" +
                    r.getCode()).collect(Collectors.joining(","));
            authority.append(roleCodeStrs);
        }

        // 遍历角色，获取所有菜单权限
        Set<String> menuCodeSet=new HashSet<String>();
        for(Role role:roleList){
            List<Menu> sysMenuList = menuMapper.selectList(new
                    QueryWrapper<Menu>().inSql("id",
                    "SELECT menu_id FROM role_menu WHERE role_id=" + role.getId()));
            for(Menu sysMenu:sysMenuList){
                String perms=sysMenu.getPerms();
                if(StringUtil.isNotEmpty(perms)){
                    menuCodeSet.add(perms);
                }
            }
        }
        if(menuCodeSet.size()>0){
            authority.append(",");
            String menuCodeStrs = menuCodeSet.stream().collect(Collectors.joining(","));
            authority.append(menuCodeStrs);
        }
        System.out.println("authority:"+authority.toString());
        return authority.toString();

    }
}




