package com.pad.service.impl;

import com.pad.entity.Admin;
import com.pad.entity.LoginUser;
import com.pad.exceptionhandler.PadException;
import com.pad.response.R;
import com.pad.service.LoginService;
import com.pad.utils.JwtUtils;
import com.pad.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private RedisUtil redisUtil;

    //登录
    @Override
    public R login(Admin admin) {
        //进行用户认证 使用用户名与密码认证方式
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(admin.getName(),admin.getPassword());
        Authentication authenticate = manager.authenticate(authenticationToken);
        //认证失败 抛出异常
        if (ObjectUtils.isEmpty(authenticate)){
            throw new PadException(HttpStatus.UNAUTHORIZED.value(),"登录失败");
        }
        //认证成功 使用userId生成jwt 存入R
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getAdmin().getId();
        String jwt = JwtUtils.acquireJWT(id);
        Map<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        //将用户信息存入redis
        redisUtil.setCacheObject("login:"+id,loginUser);
        return R.ok().code(200).message("登录成功").data(map);
    }

    //退出
    @Override
    public R logout() {
        //获取SecurityContextHolder
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        String userId = loginUser.getAdmin().getId();
        //删除redis中的值
        redisUtil.deleteObject("login:"+userId);
        return R.ok().code(200).message("退出成功");
    }
}
