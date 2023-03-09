package com.example.bsfood.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bsfood.entity.PageBean;
import com.example.bsfood.entity.Role;
import com.example.bsfood.entity.User;
import com.example.bsfood.entity.UserRole;
import com.example.bsfood.service.RoleService;
import com.example.bsfood.service.UserRoleService;
import com.example.bsfood.service.UserService;
import com.example.bsfood.utils.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;


@RestController
@RequestMapping("/user")
public class UserControlle {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    @Value("${avatarImagesFilePath}")
    private String avatarImagesFilePath;



    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:user:add')"+"||"+"hasAuthority('system:user:edit')")
    public Result save(@RequestBody User user){
        if(user.getId()==null ||user.getId()==-1){
            user.setCreateTime(new Date());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
             userService.save(user);
        }else{
            user.setUpdateTime(new Date());
            userService.updateById(user);
        }
        return Result.ok();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result findById(@PathVariable(value = "id")Integer id){
        User user = userService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("sysUser",user);
        return Result.ok(map);
    }


    /**
     * 验证用户名
     * @param user
     * @return
     */
    @PostMapping("/checkUserName")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result checkUserName(@RequestBody User user){
        if(userService.getByUsername(user.getUsername())==null){
            return Result.ok();
        }else{
            return Result.error();
        }
    }



    /**
     * 修改密码
     * @param user
     * @return
     */
    @PostMapping("/updateUserPwd")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result updateUserPwd(@RequestBody User user){
        User currentUser = userService.getById(user.getId());

        //检测前端输入的旧密码是否与数据库一致
        if(bCryptPasswordEncoder.matches(user.getOldPassword(),currentUser.getPassword())){
            //加密
            currentUser.setPassword(bCryptPasswordEncoder.encode(user.getNewPassword()));
            currentUser.setUpdateTime(new Date());
            userService.updateById(currentUser);
        }else{
            return Result.error("输入旧密码错误！");
        }
        return Result.ok();

    }

    /**
     * 上传用户头像图片
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
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(avatarImagesFilePath+newFileName));
            resultMap.put("code",0);
            resultMap.put("msg","上传成功");
            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("title",newFileName);
            dataMap.put("src","image/userAvatar/"+newFileName);
            resultMap.put("data",dataMap);
        }
        return resultMap;
    }

    /**
     * 修改用户头像
     * @param
     * @return
     */
    @RequestMapping("/updateAvatar")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result updateAvatar(@RequestBody User user){
        User currentUser = userService.getById(user.getId());
        currentUser.setUpdateTime(new Date());
        currentUser.setAvatar(user.getAvatar());
        userService.updateById(currentUser);
        return Result.ok();
    }


    @PostMapping("/list")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result userList(@RequestBody PageBean pageBean) {
        //删除头尾空格
        String query=pageBean.getQuery().trim();
        Page<User> pageResult = userService.page(new Page<>(pageBean.getPageNum(), pageBean.getPageSize())
        ,new QueryWrapper<User>().like(StringUtil.isNotEmpty(query),"username",query));//如果用户名存在则进行SQL拼接，然后根据用户名查询
        //获取分页数据
        List<User> userList = pageResult.getRecords();

        for(User user:userList){
            //子查询将用户权限信息查出并存入list集合
           List<Role> roleList = roleService.list(new QueryWrapper<Role>()
                   .inSql("id", "select role_id from user_role where user_id=" +
                    user.getId()));
           user.setRoleList(roleList);
       }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userList", userList);
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
    @PreAuthorize("hasAuthority('system:user:delete')")
    public Result delete(@RequestBody Long[] ids){
        userService.removeByIds(Arrays.asList(ids));
        userRoleService.remove(new QueryWrapper<UserRole>().in("user_id",ids));
         return Result.ok();
    }


    /**
     * 重置密码
     * @param id
     * @return
     */
    @GetMapping("/resetPassword/{id}")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result resetPassword(@PathVariable(value = "id")Integer id){
          User user = userService.getById(id);
         user.setPassword(bCryptPasswordEncoder.encode(Constant.DEFAULT_PASSWORD));
        user.setUpdateTime(new Date());
        userService.updateById(user);
        return Result.ok();
    }

    /**
     * 更新status状态
     * @param id
     * @param status
     * @return
     */
    @GetMapping("/updateStatus/{id}/status/{status}")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result updateStatus(@PathVariable(value = "id")Integer id,
                          @PathVariable(value = "status")String status){
          User sysUser = userService.getById(id);
         sysUser.setStatus(status);
        userService.saveOrUpdate(sysUser);
        return Result.ok();
    }

}
