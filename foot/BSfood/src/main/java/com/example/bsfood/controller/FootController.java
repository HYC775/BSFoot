package com.example.bsfood.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bsfood.entity.*;
import com.example.bsfood.service.DaleiService;
import com.example.bsfood.service.FXiaoleiService;
import com.example.bsfood.service.FootService;
import com.example.bsfood.service.XiaoleiService;
import com.example.bsfood.utils.DateUtil;
import com.example.bsfood.utils.Result;
import com.example.bsfood.utils.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/foot")
public class FootController {

    @Autowired
    private FootService footService;

    @Autowired
    private DaleiService daleiService;

    @Autowired
    private FXiaoleiService fXiaoleiService;

    @Autowired
    private XiaoleiService xiaoleiService;

    @Value("${footImagesFilePath}")
    private String footImagesFilePath;

    /**
     * 查询美食信息
     * @return
     */
    @PostMapping("/listAll")
    //@PreAuthorize("hasAuthority('system:foot:query')")
    public Result listAll(){
        Map<String,Object> resultMap=new HashMap<>();
        System.out.println("11233");
        List<Foot> footList = footService.list();
        System.out.println(footList);
        resultMap.put("footList",footList);
        return Result.ok(resultMap);
    }


    /**
     * 上传美食图片
     * @param file
     * @return
     * @throws Exception
     */

    @RequestMapping("/uploadImage")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Map<String,Object> uploadImage(MultipartFile file)throws Exception{

        Map<String,Object> resultMap=new HashMap<>();
        if(!file.isEmpty()){
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            String suffixName=originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(footImagesFilePath+newFileName));
            resultMap.put("code",0);
            resultMap.put("msg","上传成功");
            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("title",newFileName);
            dataMap.put("src","image/footimgs/"+newFileName);
            resultMap.put("data",dataMap);
        }
        return resultMap;
    }

    /**
     * 修改美食图片
     * @param
     * @return
     */
    @RequestMapping("/updateAvatar")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result updateAvatar(@RequestBody Foot foot){
        Foot currentFoot = footService.getById(foot.getId());
        currentFoot.setImgs(foot.getImgs());
        footService.updateById(currentFoot);
        return Result.ok();
    }




    /**
     * 根据分类查询
     * @return
     */
    @PostMapping("dalei/{classid}")
    @PreAuthorize("hasAuthority('system:foot:query')")
    public Result findBydaleiId(@PathVariable(value = "classid")Integer id){
      List<Foot> footList = footService.list(new QueryWrapper<Foot>().in("dalei_id",id));

        for(int i=0;i<footList.size();i++){
            Foot foot = footList.get(i);//获取每一个Example对象
            foot.setFxiaoleiList(fXiaoleiService.list(new QueryWrapper<FXiaolei>().in("fid",id)));
            String xiaoleistr = "";
            for(int j=0;i<foot.getFxiaoleiList().size();j++){
                FXiaolei fXiaolei = foot.getFxiaoleiList().get(j);
                Xiaolei xiaolei = xiaoleiService.getById(fXiaolei.getXiaoleiId());
                xiaoleistr+= xiaolei.getName()+";";
            }
            foot.setXiaoleString(xiaoleistr);
            foot.setDalei(daleiService.getById(foot.getDaleiId()));
        }
        Map<String,Object> map=new HashMap<>();
        map.put("footList",footList);
        return Result.ok(map);
    }


    /**
     * 分页查询美食信息
     * @return
     */

    @PostMapping("/list")
    @PreAuthorize("hasAuthority('system:foot:query')")
    public Result userList(@RequestBody PageBean pageBean) {
        //删除头尾空格
        String query=pageBean.getQuery().trim();
        Page<Foot> pageResult = footService.page(new Page<>(pageBean.getPageNum(), pageBean.getPageSize())
                ,new QueryWrapper<Foot>().like(StringUtil.isNotEmpty(query),"footname",query).or()
                        .like(StringUtil.isNotEmpty(query),"userName",query));//如果用户名存在则进行SQL拼接，然后根据用户名查询
        //获取分页数据
        List<Foot> footList = pageResult.getRecords();

         for(Foot foot:footList){
            //子查询将用户权限信息查出并存入list集合
            List<FXiaolei> fXiaoleiList = fXiaoleiService.list(new QueryWrapper<FXiaolei>().eq("fid",foot.getId()));

            foot.setFxiaoleiList0(fXiaoleiList);
        }
        System.out.println(footList.toString());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("footList", footList);
        resultMap.put("total", pageResult.getTotal());
        return Result.ok(resultMap);
    }


    @PostMapping("/list/{id}")
    @PreAuthorize("hasAuthority('system:foot:query')")
    public Result user_footList(@PathVariable(value = "id")Integer id,@RequestBody PageBean pageBean) {
        //删除头尾空格
        String query=pageBean.getQuery().trim();
        Page<Foot> pageResult = footService.page(new Page<>(pageBean.getPageNum(), pageBean.getPageSize())
                ,new QueryWrapper<Foot>().eq("userid",id)
                        .like(StringUtil.isNotEmpty(query),"footname",query).or()
                        .like(StringUtil.isNotEmpty(query),"userName",query)
                       );//如果用户名存在则进行SQL拼接，然后根据用户名查询
        //获取分页数据
        List<Foot> footList = pageResult.getRecords();

        for(Foot foot:footList){
            //子查询将用户权限信息查出并存入list集合
            List<FXiaolei> fXiaoleiList = fXiaoleiService.list(new QueryWrapper<FXiaolei>().eq("fid",foot.getId()));

            foot.setFxiaoleiList0(fXiaoleiList);
        }
        System.out.println(footList.toString());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("footList", footList);
        resultMap.put("total", pageResult.getTotal());
        return Result.ok(resultMap);
    }



    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:foot:query')")
    public Result findById(@PathVariable(value = "id")Integer id){
        Foot foot = footService.getById(id);
        foot.setDalei(daleiService.getById(foot.getDaleiId()));
        foot.setFxiaoleiList(fXiaoleiService.list(new QueryWrapper<FXiaolei>().in("fid",id)));
        Map<String,Object> map=new HashMap<>();
        map.put("sysFoot",foot);
        return Result.ok(map);
    }

    @Transactional
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:foot:add')"+"||"+"hasAuthority('system:foot:edit')"+
            "||"+"hasAuthority('system:fxiaolei:add')"+"||"+"hasAuthority('system:fxiaolei:edit')"+
            "||"+"hasAuthority('system:fxiaolei:delete')")
    public Result save(@RequestBody Foot foot){

        int[] xiaoleiIds =foot.getXiaoleiids();
          System.out.println("11");
        if(foot.getId()==null ||foot.getId()==-1){
            footService.save(foot);
            System.out.println("22"+xiaoleiIds);
            Foot foot1 = footService.getOne(new QueryWrapper<Foot>().eq("guid",foot.getGuid()));
            System.out.println(foot1.toString());
            int fid = foot1.getId();
            List<FXiaolei> fXiaoleis = new ArrayList<>();
            Arrays.stream(xiaoleiIds).forEach(xiaoleiId -> {
                FXiaolei fXiaolei = new FXiaolei();
                fXiaolei.setFid(fid);
                fXiaolei.setXiaoleiId(xiaoleiId);
                fXiaoleis.add(fXiaolei);});
            System.out.println(fXiaoleis.toString());
            fXiaoleiService.saveBatch(fXiaoleis);
        }else{
            footService.updateById(foot);
            List<FXiaolei> fXiaoleis = new ArrayList<>();
            Arrays.stream(xiaoleiIds).forEach(xiaoleiId -> {
                FXiaolei fXiaolei = new FXiaolei();
                fXiaolei.setFid(foot.getId());
                fXiaolei.setXiaoleiId(xiaoleiId);
                fXiaoleis.add(fXiaolei);});
             fXiaoleiService.remove(new QueryWrapper<FXiaolei>().eq("fid",foot.getId().toString()));
             fXiaoleiService.saveBatch(fXiaoleis);
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
    @PreAuthorize("hasAuthority('system:foot:delete')")
    public Result delete(@RequestBody Long[] ids){
        footService.removeByIds(Arrays.asList(ids));
        fXiaoleiService.remove(new QueryWrapper<FXiaolei>().in("fid",ids));
        return Result.ok();
    }
}
