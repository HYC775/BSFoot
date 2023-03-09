package com.example.bsfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bsfood.entity.Dalei;
import com.example.bsfood.entity.Xiaolei;
import com.example.bsfood.service.XiaoleiService;
import com.example.bsfood.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/xilei")
public class XiaoleiController {

    @Autowired
    private XiaoleiService xiaoleiService;


    @GetMapping("/listAll")
    @PreAuthorize("hasAuthority('system:xiaolei:query')")
    public Result listAll(){
        Map<String,Object> resultMap=new HashMap<>();
        List<Xiaolei> xiaoleiiList = xiaoleiService.list();
        resultMap.put("xileiList",xiaoleiiList);
        return Result.ok(resultMap);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:xiaolei:query')")
    public Result findById(@PathVariable(value = "id")Integer id){
        Xiaolei xiaolei = xiaoleiService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("xilei",xiaolei);
        return Result.ok(map);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("fByxiaolei/{id}")
    @PreAuthorize("hasAuthority('system:xiaolei:query')")
    public Result footidByxiaolei(@PathVariable(value = "id")Integer id){
        List<Xiaolei> xiaolei = xiaoleiService.list(new QueryWrapper<Xiaolei>().eq("fid",id));
        Map<String,Object> map=new HashMap<>();
        map.put("xiaolei",xiaolei);
        return Result.ok(map);
    }

    /**
     * 添加或者修改
     * @param xiaolei
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:xiaolei:add')"+"||"+"hasAuthority('system:xiaolei:edit')")
    public Result save(@RequestBody Xiaolei xiaolei){
        if(xiaolei.getId()==null ||xiaolei.getId()==-1){
            xiaoleiService.save(xiaolei);
        }else{
            xiaoleiService.updateById(xiaolei);
        }
        return Result.ok();
    }


    /**
     * 删除
     * @param ids
     * @return
     */
    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:xiaolei:delete')")
    public Result delete(@RequestBody Long[] ids){
        xiaoleiService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }
}
