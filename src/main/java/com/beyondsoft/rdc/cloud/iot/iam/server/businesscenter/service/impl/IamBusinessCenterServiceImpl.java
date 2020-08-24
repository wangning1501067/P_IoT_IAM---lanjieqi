package com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.service.impl;

import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.model.IamBusinessCenterVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.mapper.IamBusinessCenterMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.businesscenter.service.IamBusinessCenterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-28 12:36
 */
@Service
public class IamBusinessCenterServiceImpl implements IamBusinessCenterService {

    @Autowired
    private IamBusinessCenterMapper mapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(IamBusinessCenterBo record) {
        IamBusinessCenterDo ibcDo = new IamBusinessCenterDo();
        BeanUtils.copyProperties(record, ibcDo);
        return this.mapper.insert(ibcDo);
    }

    @Override
    public int insertSelective(IamBusinessCenterBo record) {
        IamBusinessCenterDo ibcDo = new IamBusinessCenterDo();
        record.setStatus(1);
        BeanUtils.copyProperties(record, ibcDo);
        return this.mapper.insertSelective(ibcDo);
    }

    @Override
    public IamBusinessCenterVo selectByPrimaryKey(Integer id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(IamBusinessCenterBo record) {
        IamBusinessCenterDo ibcDo = new IamBusinessCenterDo();
        BeanUtils.copyProperties(record, ibcDo);
        ibcDo.setUpdateTime(new Date());
        return this.mapper.updateByPrimaryKeySelective(ibcDo);
    }

    @Override
    public int updateByPrimaryKey(IamBusinessCenterBo record) {
        IamBusinessCenterDo ibcDo = new IamBusinessCenterDo();
        BeanUtils.copyProperties(record, ibcDo);
        return this.mapper.updateByPrimaryKey(ibcDo);
    }

    @Override
    public List<IamBusinessCenterVo> getList(Integer userId) {
        return this.mapper.getList(userId);
    }

    @Override
    public IamBusinessCenterVo getByBusinessId(Integer userId) {
        return this.mapper.getByBusinessId(userId);
    }
}
