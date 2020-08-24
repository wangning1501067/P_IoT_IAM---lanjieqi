package com.beyondsoft.rdc.cloud.iot.iam.server.user.controller;

import com.beyondsoft.rdc.cloud.iot.iam.common.constant.SessionConstant;
import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.LoginModel;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.service.IamUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/iam/server/no-login")
@Slf4j
public class LoginController {

    @Autowired
    private IamUserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ObjectRestResponse adminLogin(@RequestBody LoginModel loginModel, HttpSession session) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        IamUserVo user = this.userService.getOneUser(loginModel.getUsername(), loginModel.getPassword());
        if (user != null) {
            session.setAttribute(SessionConstant.SERVER_SESSION_USER, user);
            int sessionTimeout = session.getServletContext().getSessionTimeout();
            log.debug("===LoginController===session过去时间:{}", sessionTimeout);
        }
        restResponse.setData(user);
        return restResponse;
    }

    /**
     * 用户退出
     */
    @GetMapping("/loginout")
    public ObjectRestResponse adminLoginout(HttpSession session) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        if(session!=null){
            //IamUserVo user = (IamUserVo)session.getAttribute(SessionConstant.NAME);//从当前session中获取用户信息
            session.removeAttribute(SessionConstant.SERVER_SESSION_USER);
            session.invalidate();//关闭session
        }
        return restResponse;
    }
}
