package com.beyondsoft.rdc.cloud.iot.iam.client.user.service;

import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.LoginModel;

public interface ClientIamUserService {
    IamUserVo getOneUser(LoginModel loginModel);
}