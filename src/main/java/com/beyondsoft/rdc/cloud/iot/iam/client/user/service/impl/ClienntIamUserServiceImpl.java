package com.beyondsoft.rdc.cloud.iot.iam.client.user.service.impl;

import com.beyondsoft.rdc.cloud.iot.iam.client.user.service.ClientIamUserService;
import com.beyondsoft.rdc.cloud.iot.iam.client.websocket.server.WebSocketServer;
import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.GeneralException;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.service.IamBusinessCenterService;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.service.IamDeviceService;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.mapper.IamUserMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.LoginModel;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ClienntIamUserServiceImpl implements ClientIamUserService {

    @Autowired
    private IamUserMapper userMapper;

    @Autowired
    private IamDeviceService deviceService;

    @Autowired
    private IamBusinessCenterService businessCenterService;

    @Override
    public IamUserVo getOneUser(LoginModel loginModel) {

        IamUserVo user = this.userMapper.getOneUser(loginModel.getUsername());
        if(user==null){
            throw new GeneralException(InternationEnum.KEY_USERNAME_ERROR.getLanguage(GlobalValue.getLanguage()));
        }else{
            /*String passMd5 = MD5Util.MD5(loginModel.getPassword());
            if(!user.getPassword().equals(passMd5)){
                throw new GeneralException(InternationEnum.KEY_PASSWORD_ERROR.getLanguage(GlobalValue.getLanguage()));
            }*/
            if (!BCrypt.checkpw(loginModel.getPassword(), user.getPassword())) {
                throw new GeneralException(InternationEnum.KEY_PASSWORD_ERROR.getLanguage(GlobalValue.getLanguage()));
            }
        }

        //user添加商户ID
        List<IamBusinessCenterVo> list = this.businessCenterService.getList(user.getId());
        user.setMerchantId(list.get(0).getId());

        //IamUserVo user = new IamUserVo();
        //user.setWebsocketPath(serverPath.getPath()+"?userId="+user.getId()+"&deviceCode="+loginModel.getDeviceCode());

        //验证设备code
        IamDeviceVo deviceVo = this.deviceService.getDeviceByUserId(user.getId(), loginModel.getDeviceCode());
        if (ObjectUtils.isEmpty(deviceVo)) {
            throw new GeneralException(InternationEnum.DEVICE_NOT_EXISTS.getLanguage(GlobalValue.getLanguage()));
        }

        //判断设备是否在线=您输入的设备编号已在别处登录
        boolean flag = new WebSocketServer().ifDeviceExist(loginModel);
        if (!flag) {
            throw new GeneralException(InternationEnum.DEVICE_ALREADY_EXISTS.getLanguage(GlobalValue.getLanguage()));
        }
        return user;
    }
}