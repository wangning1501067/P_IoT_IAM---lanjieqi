package com.beyondsoft.rdc.cloud.iot.iam.server.area.service;

import com.beyondsoft.rdc.cloud.iot.iam.server.area.model.IamAreaBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.model.IamAreaVo;

import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-07-28 17:04
 */
public interface IamAreaService {

    int deleteByPrimaryKey(Integer id);

    int insert(IamAreaBo record);

    int insertSelective(IamAreaBo record);

    IamAreaVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IamAreaBo record);

    int updateByPrimaryKey(IamAreaBo record);

    List<Map<String, Object>> getList(Integer merchantId);

    List<IamAreaVo> getListArea(IamAreaBo record);

    List<IamAreaVo> getByParentIdList(Integer id);

    Integer selectChild(Integer id);

    int deleteArea(Integer id);

    Integer addSpace(IamAreaBo iamAreaBo);

    String getAreaByAreaName(List<Integer> idList);

}
