package com.pepper.sharding.controller;

import com.dangdang.ddframe.rdb.sharding.keygen.DefaultKeyGenerator;
import com.pepper.sharding.model.UserEntity;
import com.pepper.sharding.model.UserSexEnum;
import com.pepper.sharding.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DefaultKeyGenerator defaultKeyGenerator;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * test select
     * @return
     */
    @RequestMapping("/getUsers")
    public List<UserEntity> getUsers() {
        List<UserEntity> users = userService.getUsers();
        return users;
    }

    /**
     * test insert
     * @return
     */
    @RequestMapping(value = "/save")
    public String updateTransactional() {
        for (int i = 0; i < 10; i++) {
            UserEntity user = new UserEntity();
            user.setId(defaultKeyGenerator.generateKey().longValue());
            user.setUser_id(defaultKeyGenerator.generateKey().longValue());
            user.setOrder_id(defaultKeyGenerator.generateKey().longValue());
            user.setNickName("name" + i);
            user.setPassWord("name" + i);
            user.setUserName("name" + i);
            user.setUserSex(UserSexEnum.WOMAN);
            userService.save(user);
        }
        return "success";
    }
}

