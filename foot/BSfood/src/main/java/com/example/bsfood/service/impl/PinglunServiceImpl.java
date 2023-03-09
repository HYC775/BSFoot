package com.example.bsfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bsfood.entity.Menu;
import com.example.bsfood.entity.Pinglun;
import com.example.bsfood.service.PinglunService;
import com.example.bsfood.mapper.PinglunMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author HYC
* @description 针对表【pinglun】的数据库操作Service实现
* @createDate 2023-02-23 22:13:11
*/
@Service
public class PinglunServiceImpl extends ServiceImpl<PinglunMapper, Pinglun>
    implements PinglunService{


    public List<Pinglun> buildTreePinglun(List<Pinglun> pinglunList) {
        List<Pinglun> resultPinglunList = new ArrayList<>();
        System.out.println("pinglunList****"+pinglunList);
        //寻找添加子节点
        for (Pinglun pinglun:pinglunList){
            System.out.println(pinglun);
            for (Pinglun childrenPinglun:pinglunList){
                if(childrenPinglun.getParentid()==pinglun.getId()){
                    System.out.println(childrenPinglun.getParentid());
                    pinglun.getChildren().add(childrenPinglun);
                    System.out.println(2);
                }
            }
            System.out.println(11111);
            //顶级节点
            if(pinglun.getParentid()==0){
                System.out.println(pinglun.getParentid());
                resultPinglunList.add(pinglun);
            }
        }
        //System.out.println("****"+resultPinglunList);
        return resultPinglunList;
    }

}




