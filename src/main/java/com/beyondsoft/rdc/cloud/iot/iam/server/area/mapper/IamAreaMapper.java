package com.beyondsoft.rdc.cloud.iot.iam.server.area.mapper;

import com.beyondsoft.rdc.cloud.iot.iam.server.area.model.IamAreaDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.model.IamAreaVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-28 16:55
 */

@Mapper
public interface IamAreaMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(IamAreaDo record);

    int insertSelective(IamAreaDo record);

    IamAreaVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IamAreaDo record);

    int updateByPrimaryKey(IamAreaDo record);

    List<IamAreaVo> getList(@Param("merchantId") Integer merchantId);

    List<IamAreaVo> getByParentIdList(Integer parentId);

    Integer selectChild(Integer parentId);

    int deleteArea(Integer id);

    Integer selectSpaceReference(@Param("id") Integer id);

    Integer selectVresaSpaceReference(@Param("id") Integer id);

    IamAreaVo selectSpaceByName(@Param("areaName") String areaName,@Param("parentId") Integer parentId,
                                        @Param("id") Integer id);

    Integer addSpace(IamAreaDo iamAreaDo);

    String getAreaByAreaName(@Param("idList") List<Integer> idList);
}
