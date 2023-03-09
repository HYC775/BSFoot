package com.example.bsfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bsfood.entity.*;
import com.example.bsfood.service.RoleMenuService;
import com.example.bsfood.service.RoleService;
import com.example.bsfood.service.UserRoleService;
import com.example.bsfood.utils.Result;
import com.example.bsfood.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class RoleControlle {

        @Autowired
        private RoleService roleService;
        @Autowired
        public UserRoleService userRoleService;
        @Autowired
        private RoleMenuService roleMenuService;


        @GetMapping("/listAll")
        @PreAuthorize("hasAuthority('system:role:query')")
        public Result listAll(){
            Map<String,Object> resultMap=new HashMap<>();
            List<Role> roleList = roleService.list();
            resultMap.put("roleList",roleList);
            return Result.ok(resultMap);
        }

    /**
     * 分页查询角色信息
     * @return
     */
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result userList(@RequestBody PageBean pageBean) {
        //删除头尾空格
        String query=pageBean.getQuery().trim();
        Page<Role> pageResult = roleService.page(new Page<>(pageBean.getPageNum(), pageBean.getPageSize())
                ,new QueryWrapper<Role>().like(StringUtil.isNotEmpty(query),"name",query));//如果用户名存在则进行SQL拼接，然后根据用户名查询
        //获取分页数据
        List<Role> rolesList = pageResult.getRecords();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleList", rolesList);
        resultMap.put("total", pageResult.getTotal());
        return Result.ok(resultMap);
    }

    /**
     * 添加或者修改
     * @param role
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:role:add')"+"||"+"hasAuthority('system:role:edit')")
    public Result save(@RequestBody Role role){
        if(role.getId()==null ||role.getId()==-1){
            role.setCreateTime(new Date());
            roleService.save(role);
        }else{
            role.setUpdateTime(new Date());
            roleService.updateById(role);
        }
        return Result.ok();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result findById(@PathVariable(value = "id")Integer id){
        Role role = roleService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("sysRole",role);
        return Result.ok(map);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:role:delete')")
    public Result delete(@RequestBody Long[] ids){
        roleService.removeByIds(Arrays.asList(ids));
        userRoleService.remove(new QueryWrapper<UserRole>().in("role_id",ids));
        return Result.ok();
    }

    @GetMapping("/menus/{id}")
    @PreAuthorize("hasAuthority('system:role:menu')")
    public Result menus(@PathVariable(value = "id")Integer id){
        //通过roleid获取权限菜单
       List<RoleMenu> roleMenuList = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id",id));
       //通过stream将menu的id打包
        List<Long> menuIdList = roleMenuList.stream().map(p->p.getMenuId()).collect(Collectors.toList());
        HashMap hashMap = new HashMap();
        hashMap.put("menuIdList",menuIdList);
        return Result.ok(hashMap);
    }

    /**
     * 更新角色权限信息
     * @param id
     * @param menuIds
     * @return
     */
    @Transactional
    @PostMapping("/updateMenus/{id}")
    @PreAuthorize("hasAuthority('system:role:menu')")
    public Result updateMenus(@PathVariable(value = "id") Long id, @RequestBody Long[] menuIds){
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",id));
        List<RoleMenu> roleMenuList=new ArrayList<>();
        Arrays.stream(menuIds).forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(id);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        });
        roleMenuService.saveBatch(roleMenuList);
        return Result.ok();
    }


    /**
     * 更新用户角色信息
     * @param userId
     * @param roleIds
     * @return
     */
    @Transactional
    @PostMapping("/grantRole/{userId}")
    @PreAuthorize("hasAuthority('system:user:role')")
    public Result grantRole(@PathVariable("userId") Long userId, @RequestBody Long[]
            roleIds){
        List<UserRole> userRoleList=new ArrayList<>();
        Arrays.stream(roleIds).forEach(r -> {
            UserRole sysUserRole = new UserRole();
            sysUserRole.setRoleId(r);
            sysUserRole.setUserId(userId);
            userRoleList.add(sysUserRole);
        });
        userRoleService.remove(new QueryWrapper<UserRole>
                ().eq("user_id",userId));
        userRoleService.saveBatch(userRoleList);
        return Result.ok();
    }



}
