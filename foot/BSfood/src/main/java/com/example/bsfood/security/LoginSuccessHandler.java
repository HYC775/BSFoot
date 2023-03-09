package com.example.bsfood.security;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bsfood.entity.Menu;
import com.example.bsfood.entity.Role;
import com.example.bsfood.entity.User;
import com.example.bsfood.service.MenuService;
import com.example.bsfood.service.RoleService;
import com.example.bsfood.service.UserService;
import com.example.bsfood.utils.JwtUtils;
import com.example.bsfood.utils.Result;
import com.example.bsfood.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 登录失败处理器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        HashMap<String, Object> resultMap = new HashMap<>();

        String username=authentication.getName();
        String token = JwtUtils.genJwtToken(username);
        User currentUser = userService.getByUsername(username);

        // 根据用户id获取所有的角色
        List<Role> roleList = roleService.list(new QueryWrapper<Role>
                ().inSql("id", "SELECT role_id FROM user_role WHERE user_id=" + currentUser.getId()));

        // 遍历角色，获取所有菜单权限
        Set<Menu> menuSet=new HashSet<Menu>();
        for(Role role:roleList){
            List<Menu> sysMenuList = menuService.list(new
                    QueryWrapper<Menu>().inSql("id",
                    "SELECT menu_id FROM role_menu WHERE role_id=" + role.getId()));
            for(Menu sysMenu:sysMenuList){
                    menuSet.add(sysMenu);
            }
        }

        currentUser.setRoles(roleList.stream().map(Role::getName).collect(Collectors.joining(",")));
        currentUser.setRoleListbyUserId(roleList);

        List<Menu> menuList = new ArrayList<>(menuSet);
        System.out.println(menuList);
        //排序
        menuList.sort(Comparator.comparing(Menu::getOrderNum));
        List<Menu> menuTreeList = menuService.buildTreeMenu(menuList);

        System.out.println(menuTreeList);
        resultMap.put("token",token);
        resultMap.put("currentUser",currentUser);
        resultMap.put("menuTreeList",menuTreeList);

        outputStream.write(JSONUtil.toJsonStr(Result.ok(resultMap)).getBytes());
        outputStream.flush();
        outputStream.close();

    }
}
