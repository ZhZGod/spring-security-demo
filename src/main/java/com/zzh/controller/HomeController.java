package com.zzh.controller;

import com.zzh.entity.UserEntity;
import com.zzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String doRegister(@RequestBody UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        if (userService.insert(userEntity)){
            return "success";
        }
        return "error";
    }

    /**
     * 自定义login 方法  security 自己有实现login的接口 也可以自定义实现 但是 要把 它自定义的事情做了
     * @param username
     * @param password
     * @param request
     * @return
     */
    @GetMapping("/mylogin")
    public String myLogin(@RequestParam("username")String username, @RequestParam("password")String password, HttpServletRequest request){
        System.out.println("username--->>>"+username);
        System.out.println("password--->>>"+password);
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession();
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
        return "yes";
    }

}
