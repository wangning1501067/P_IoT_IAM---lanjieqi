package com.beyondsoft.rdc.cloud.iot.iam.server.user.service.impl;

import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.GeneralException;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.service.IamBusinessCenterService;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.mapper.IamUserMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.IamUserVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.service.IamUserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IamUserServiceImpl implements IamUserService {

    @Autowired
    private IamUserMapper userMapper;

    @Autowired
    private IamBusinessCenterService businessCenterService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(IamUserBo record) {
        IamUserDo user = new IamUserDo();
        BeanUtils.copyProperties(record, user);
        return this.userMapper.insert(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(IamUserBo record) {
        IamUserDo user = new IamUserDo();
        BeanUtils.copyProperties(record, user);
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        //默认启用
        user.setStatus(1);
        int num = this.userMapper.insertSelective(user);

        IamBusinessCenterBo businessCenterBo = new IamBusinessCenterBo();
        businessCenterBo.setMerchantName("");
        businessCenterBo.setUserId(user.getId());
        businessCenterBo.setStatus(1);
        this.businessCenterService.insertSelective(businessCenterBo);
        return num;
    }

    @Override
    public IamUserVo selectByPrimaryKey(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(IamUserBo record) {
        IamUserDo user = new IamUserDo();
        BeanUtils.copyProperties(record, user);
        return this.userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updateByPrimaryKey(IamUserBo record) {
        IamUserDo user = new IamUserDo();
        BeanUtils.copyProperties(record, user);
        return this.userMapper.updateByPrimaryKey(user);
    }

    @Override
    public IamUserVo getOneUser(String username, String password) {
        IamUserVo user = this.userMapper.getOneUser(username);
        if(user==null){
            throw new GeneralException(InternationEnum.KEY_USERNAME_ERROR.getLanguage(GlobalValue.getLanguage()));
        }else{
            /*String passMd5 = MD5Util.MD5(password);
            if(!user.getPassword().equals(passMd5)){
                throw new GeneralException(InternationEnum.KEY_PASSWORD_ERROR.getLanguage(GlobalValue.getLanguage()));
            }*/
            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new GeneralException(InternationEnum.KEY_PASSWORD_ERROR.getLanguage(GlobalValue.getLanguage()));
            }
        }
        IamBusinessCenterVo merchant = this.businessCenterService.getByBusinessId(user.getId());
        user.setMerchantId(merchant.getId());
        return user;
    }
}