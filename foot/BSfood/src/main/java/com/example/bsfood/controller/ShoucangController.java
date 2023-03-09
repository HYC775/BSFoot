package com.example.bsfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bsfood.entity.*;
import com.example.bsfood.service.FootService;
import com.example.bsfood.service.ShoucangService;
import com.example.bsfood.utils.Result;
import com.example.bsfood.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shoucang")
public class ShoucangController {

    @Autowired
    private ShoucangService shoucangService;

    @Autowired
    private FootService footService;
    /**
     * 分页查询收藏列表
     * @return
     */
    @PostMapping("/list/{id}")
    @PreAuthorize("hasAuthority('system:shoucang:query')"+"||"+"hasAuthority('system:foot:query')")
    public Result userList(@PathVariable(value = "id")Integer id,@RequestBody PageBean pageBean) {
        //删除头尾空格
        String query=pageBean.getQuery().trim();
        Page<Foot> pageResult = footService.page(new Page<>(pageBean.getPageNum(), pageBean.getPageSize())
                ,new QueryWrapper<Foot>().like(StringUtil.isNotEmpty(query),"footname",query).or()
                        .like(StringUtil.isNotEmpty(query),"userName",query)
                        .inSql("id", "select fid from shoucang where uid=" +id));
        //获取分页数据
        List<Foot> footList = pageResult.getRecords();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("footList", footList);
        resultMap.put("total", pageResult.getTotal());
        return Result.ok(resultMap);

    }


    /**
     * 删除
     * @param ids
     * @return
     */
    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:shoucang:delete')")
    public Result delete(@RequestBody Long[] ids){
        footService.removeByIds(Arrays.asList(ids));
        shoucangService.remove(new QueryWrapper<Shoucang>().in("fid",ids));
        return Result.ok();
    }
}
