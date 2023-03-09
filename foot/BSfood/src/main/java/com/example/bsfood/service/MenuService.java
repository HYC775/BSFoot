package com.example.bsfood.service;

import com.example.bsfood.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author HYC
* @description 针对表【menu】的数据库操作Service
* @createDate 2022-10-13 15:48:56
*/
public interface MenuService extends IService<Menu> {

    List<Menu> buildTreeMenu(List<Menu> menuList);
}
