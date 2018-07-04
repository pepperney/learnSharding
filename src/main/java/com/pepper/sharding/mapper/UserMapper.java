package com.pepper.sharding.mapper;

import com.pepper.sharding.model.UserEntity;

import java.util.List;

public interface UserMapper {

    List<UserEntity> getAll();

    void insert(UserEntity user);

}