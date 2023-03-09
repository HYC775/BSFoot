package com.example.bsfood.security;
import com.example.bsfood.entity.User;
import com.example.bsfood.exception.UserCountLockException;
import com.example.bsfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * 自定义Details
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user =  userService.getByUsername(username);
       if(user==null){
           throw new UsernameNotFoundException("用户名或密码错误");
       }else if("1".equals(user.getStatus())){
           throw new UserCountLockException("该账号已封禁，请联系管理员！");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),getUserAuthprity(user.getId()));
    }

    public List<GrantedAuthority> getUserAuthprity(Long userId) {// 格式
//       ROLE_admin,ROLE_common,system:user:resetPwd,system:role:delete,system:user:list,
//       system:menu:query,system:menu:list,system:menu:add,system:user:delete,system:rol
//        e:list,system:role:menu,system:user:edit,system:user:query,system:role:edit,syst
//        em:user:add,system:user:role,system:menu:delete,system:role:add,system:role:quer
//        y,system:menu:edit

         String authority=userService.getUserAuthorityInfo(userId);
         System.out.println("authority="+authority);
         return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
