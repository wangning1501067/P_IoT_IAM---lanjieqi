package com.beyondsoft.rdc.cloud.iot.iam.demo.controller;

import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.demo.model.User;
import com.beyondsoft.rdc.cloud.iot.iam.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserService userService;

    /**
     * 用户
     */
    @GetMapping("/get/{id}")
    public ObjectRestResponse getOneuser(@PathVariable Integer id) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        User user = this.userService.selectByPrimaryKey(id);
        restResponse.setData(user);
        return restResponse;
    }
}
