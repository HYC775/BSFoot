package com.example.bsfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bsfood.entity.Foot;
import com.example.bsfood.entity.Pinglun;
import com.example.bsfood.service.PinglunService;
import com.example.bsfood.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pinglun")
public class PinglunController {

    @Autowired
    private PinglunService pinglunService;


    @PostMapping("/getPinglunAll")
    public Result getPignlun(@RequestBody Foot foot){

       List<Pinglun> pinglunList = pinglunService.list(new QueryWrapper<Pinglun>().eq("fid",foot.getId()));

        List<Pinglun> pinglunList1 = pinglunService.buildTreePinglun(pinglunList);

        System.out.println("pinglunList1"+pinglunList1);

        return Result.ok(pinglunList1);

    }

    @PostMapping("/save")
    public Result savePignlun(@RequestBody Pinglun pinglun){
        System.out.println(pinglun.toString());
        pinglunService.save(pinglun);
        return Result.ok(pinglun);

    }
}
