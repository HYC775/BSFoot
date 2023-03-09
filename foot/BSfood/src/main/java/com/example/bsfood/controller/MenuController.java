package com.example.bsfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bsfood.entity.Menu;
import com.example.bsfood.service.MenuService;
import com.example.bsfood.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
   private MenuService menuService;

    @RequestMapping("/treeList")
    @PreAuthorize("hasAuthority('system:menu:query')")
    public Result treeList(){

        List<Menu> menuList = menuService.list(new QueryWrapper<Menu>().orderByAsc("order_num"));

        HashMap hashMap = new HashMap();
        hashMap.put("treeMenu",menuService.buildTreeMenu(menuList));
        return Result.ok(hashMap);

    }

    /**
     * 添加或者修改
     * @param sysMenu
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:menu:add')"+"||"+"hasAuthority('system:menu: edit')")
            public Result save(@RequestBody Menu sysMenu){
            if(sysMenu.getId()==null || sysMenu.getId()==-1){
            sysMenu.setCreateTime(new Date());
            menuService.save(sysMenu);
           }else{
            sysMenu.setUpdateTime(new Date());
            menuService.updateById(sysMenu);
           }
            return Result.ok();
            }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:menu:query')")
    public Result findById(@PathVariable(value = "id")Long id){
       Menu sysMenu = menuService.getById(id);
       Map<String,Object> map=new HashMap<>();
       map.put("sysMenu",sysMenu);
       return Result.ok(map);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('system:menu:delete')")
    public Result delete(@PathVariable(value = "id")Long id){
    int count = menuService.count(new QueryWrapper<Menu>().eq("parent_id",id));
     if(count>0){
       return Result.error("请先删除子菜单！");
    }
        menuService.removeById(id);
        return Result.ok();
    }
}
