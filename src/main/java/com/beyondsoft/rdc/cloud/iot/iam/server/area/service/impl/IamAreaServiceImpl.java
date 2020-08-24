package com.beyondsoft.rdc.cloud.iot.iam.server.area.service.impl;

import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.GeneralException;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.mapper.IamAreaMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.model.IamAreaBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.model.IamAreaDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.model.IamAreaVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.service.IamAreaService;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.util.AreaTreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-07-28 17:05
 */

@Service
public class IamAreaServiceImpl implements IamAreaService {

    @Autowired
    private IamAreaMapper mapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(IamAreaBo record) {
        IamAreaDo iaDo = new IamAreaDo();
        BeanUtils.copyProperties(record, iaDo);
        return this.mapper.insert(iaDo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(IamAreaBo record) {
        IamAreaDo iaDo = new IamAreaDo();
        BeanUtils.copyProperties(record, iaDo);
        return this.mapper.insertSelective(iaDo);
    }

    @Override
    public IamAreaVo selectByPrimaryKey(Integer id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(IamAreaBo record) {
        IamAreaVo iamAreaVo = this.mapper.selectByPrimaryKey(record.getId());
        //修改空间名称时需要查询除本条记录之外，同应用下同级别有没有重复的名称
        IamAreaVo iamAreaVo1 = this.mapper.selectSpaceByName(record.getAreaName().trim(),
                iamAreaVo.getParentId(),
                record.getId());
        if(iamAreaVo1 != null){
            //有重名的目录
            return -1;
        }
        IamAreaDo iamAreaDo = new IamAreaDo();
        BeanUtils.copyProperties(record,iamAreaDo);
        iamAreaDo.setUpdateTime(new Date());
        Integer result = this.mapper.updateByPrimaryKeySelective(iamAreaDo);
        return result;
    }

    @Override
    public int updateByPrimaryKey(IamAreaBo record) {
        IamAreaDo iaDo = new IamAreaDo();
        BeanUtils.copyProperties(record, iaDo);
        return this.mapper.updateByPrimaryKey(iaDo);
    }

    @Override
    public List<Map<String, Object>> getList(Integer merchantId) {
        List<IamAreaVo> list = this.mapper.getList(merchantId);
        /*for (IamAreaVo vo : list) {
            for(IamAreaVo vo1 : list){
                if(vo1.getParentId().equals(vo.getId())){
                    if(vo.getIamAreaVos() == null){
                        vo.setIamAreaVos(new ArrayList<IamAreaVo>(Arrays.asList(vo1)));
                    }else{
                        vo.getIamAreaVos().add(vo1);
                    }
                }
            }
        }
        List<IamAreaVo> collect = list.stream().filter(t -> t.getLevel() == 0).collect(Collectors.toList());*/
        List<Map<String, Object>> objects = new AreaTreeUtil().menuList(list);
        return objects;
    }

    @Override
    public List<IamAreaVo> getListArea(IamAreaBo record) {
        return null;
    }

    @Override
    public List<IamAreaVo> getByParentIdList(Integer id) {
        return this.mapper.getByParentIdList(id);
    }

    @Override
    public Integer selectChild(Integer id) {
        return this.mapper.selectChild(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteArea(Integer id) {
        Integer integer = this.mapper.selectSpaceReference(id);
        if(integer > 0){
            throw new GeneralException(InternationEnum.AREAS_ARE_BEING_USED.getLanguage(GlobalValue.getLanguage()));
        }
        int i = this.mapper.deleteArea(id);
        return i;
    }

    @Override
    public Integer addSpace(IamAreaBo iamAreaBo) {
        IamAreaDo iamAreaDo = new IamAreaDo();
        BeanUtils.copyProperties(iamAreaBo,iamAreaDo);
        //如果不传parentId，则默认为根目录
        if(iamAreaDo.getParentId() == 0){
            iamAreaDo.setLevel(0);
            iamAreaDo.setCreateTime(new Date());
            iamAreaDo.setUpdateTime(new Date());
        }else {
            iamAreaDo.setCreateTime(new Date());
            iamAreaDo.setUpdateTime(new Date());
            iamAreaDo.setLevel(1);
            //不传spaceConfigId参数时，是新建空间名称时需要查询同应用下同级别有没有重复的名称
            IamAreaVo iamAreaVo = mapper.selectSpaceByName(iamAreaDo.getAreaName().trim(),
                    iamAreaDo.getParentId(), null);
            if(iamAreaVo != null){
                //有重名的目录
                return -1;
            }
        }
        Integer result = mapper.addSpace(iamAreaDo);
        return result;
    }

    @Override
    public String getAreaByAreaName(List<Integer> idList) {
        return this.mapper.getAreaByAreaName(idList);
    }
}
