package com.zzh.controller;

import com.zzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/user/list")
    public ResponseEntity getUserList(){
        return ResponseEntity.ok(userService.findUserList());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/user/one")
    public ResponseEntity getUserOne(@RequestParam("username")String username){
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @GetMapping("/user/noauthor")
    public ResponseEntity getNoAuthorUser(){
        return ResponseEntity.ok(userService.findUserList());
    }
}
