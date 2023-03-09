package com.example.bsfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bsfood.entity.Menu;
import com.example.bsfood.service.MenuService;
import com.example.bsfood.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author HYC
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2022-10-13 15:48:56
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Override
    public List<Menu> buildTreeMenu(List<Menu> menuList) {
        List<Menu> resultMenuList = new ArrayList<>();
        System.out.println(menuList);
        //寻找添加子节点
        for (Menu menu:menuList){
            for (Menu childrenMenu:menuList){
                if(childrenMenu.getParentId()==menu.getId()){
                    menu.getChildren().add(childrenMenu);
                }
            }
            //顶级节点
            if(menu.getParentId()==0L){
                resultMenuList.add(menu);
            }
        }
        System.out.println("****"+resultMenuList);
        return resultMenuList;
    }
}




