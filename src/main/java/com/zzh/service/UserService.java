package com.zzh.service;

import com.zzh.entity.UserEntity;

import java.util.List;

public interface UserService {

    /**
     * 添加新用户
     */
    boolean insert(UserEntity userEntity);

    /**
     * 查询用户信息
     */
    UserEntity getByUsername(String username);

    /**
     * 列表
     * @return
     */
    List<UserEntity> findUserList();

}
