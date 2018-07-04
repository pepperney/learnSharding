package com.pepper.sharding.service;

import com.pepper.sharding.mapper.UserMapper;
import com.pepper.sharding.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<UserEntity> getUsers() {
        List<UserEntity> users= userMapper.getAll();
        return users;
    }

    @Transactional(value="transactitonManager",rollbackFor = Exception.class,timeout=36000)  //说明针对Exception异常也进行回滚，如果不标注，则Spring 默认只有抛出 RuntimeException才会回滚事务
    public void save(UserEntity user) {
        try{
            userMapper.insert(user);
            log.error(String.valueOf(user));
        }catch(Exception e){
            log.error("find exception!");
            throw e;   // 事物方法中，如果使用trycatch捕获异常后，需要将异常抛出，否则事物不回滚。
        }

    }
}
