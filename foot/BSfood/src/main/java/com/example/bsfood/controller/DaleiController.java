package com.example.bsfood.controller;
import com.example.bsfood.entity.Dalei;
import com.example.bsfood.entity.Role;
import com.example.bsfood.service.DaleiService;
import com.example.bsfood.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/dalei")
public class DaleiController {

    @Autowired
    private DaleiService daleiService;


    @GetMapping("/listAll")
    @PreAuthorize("hasAuthority('system:dalei:query')")
    public Result listAll(){
        Map<String,Object> resultMap=new HashMap<>();
        List<Dalei> daleiList = daleiService.list();
        resultMap.put("daleiList",daleiList);
        return Result.ok(resultMap);
    }


    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:dalei:query')")
    public Result findById(@PathVariable(value = "id")Integer id){
        Dalei dalei = daleiService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("dalei",dalei);
        return Result.ok(map);
    }

    /**
     * 添加或者修改
     * @param dalei
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:dalei:add')"+"||"+"hasAuthority('system:dalei:edit')")
    public Result save(@RequestBody Dalei dalei){
        if(dalei.getId()==null ||dalei.getId()==-1){
            daleiService.save(dalei);
        }else{
            daleiService.updateById(dalei);
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
    @PreAuthorize("hasAuthority('system:dalie:delete')")
    public Result delete(@RequestBody Long[] ids){
        daleiService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }
}
