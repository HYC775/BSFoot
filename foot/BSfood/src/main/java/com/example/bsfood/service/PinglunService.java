package com.example.bsfood.service;

import com.example.bsfood.entity.Pinglun;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author HYC
* @description 针对表【pinglun】的数据库操作Service
* @createDate 2023-02-23 22:13:11
*/
public interface PinglunService extends IService<Pinglun> {

    List<Pinglun> buildTreePinglun(List<Pinglun> pinglunList);
}
