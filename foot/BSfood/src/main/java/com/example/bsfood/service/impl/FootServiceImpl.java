package com.example.bsfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bsfood.entity.Foot;
import com.example.bsfood.service.FootService;
import com.example.bsfood.mapper.FootMapper;
import org.springframework.stereotype.Service;

/**
* @author HYC
* @description 针对表【foot】的数据库操作Service实现
* @createDate 2023-02-12 00:51:31
*/
@Service
public class FootServiceImpl extends ServiceImpl<FootMapper, Foot>
    implements FootService{

}




