package com.beyondsoft.rdc.cloud.iot.iam.server.user.controller;

import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.service.IamUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iam/server/user")
@Slf4j
public class UserController {

    @Autowired
    private IamUserService userService;

    /**
     * 添加
     */
    @PostMapping("/save")
    public ObjectRestResponse save(@RequestBody IamUserBo userBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.userService.insertSelective(userBo);
            restResponse.setData(num);
        } catch (Exception e) {
            log.error("===标签添加失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }
}
