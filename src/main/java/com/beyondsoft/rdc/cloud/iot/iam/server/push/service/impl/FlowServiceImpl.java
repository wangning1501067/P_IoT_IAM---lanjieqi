package com.beyondsoft.rdc.cloud.iot.iam.server.push.service.impl;

import com.beyondsoft.rdc.cloud.iot.iam.common.util.DateUtil;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.mapper.FlowMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.FlowBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.FlowDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.FlowVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.service.FlowService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlowServiceImpl implements FlowService {

    @Autowired
    private FlowMapper flowMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.flowMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(FlowBo record) {
        FlowDo flowDo = new FlowDo();
        BeanUtils.copyProperties(record, flowDo);
        flowDo.setPushDate(new Date(record.getPushDate()));
        return this.flowMapper.insert(flowDo);
    }

    @Override
    public int insertSelective(FlowBo record) {
        FlowDo flowDo = new FlowDo();
        BeanUtils.copyProperties(record, flowDo);
        flowDo.setPushDate(new Date(record.getPushDate()));
        return this.flowMapper.insertSelective(flowDo);
    }

    @Override
    public FlowVo selectByPrimaryKey(Integer id) {
        return this.flowMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(FlowBo record) {
        FlowDo flowDo = new FlowDo();
        BeanUtils.copyProperties(record, flowDo);
        return this.flowMapper.updateByPrimaryKeySelective(flowDo);
    }

    @Override
    public int updateByPrimaryKey(FlowBo record) {
        FlowDo flowDo = new FlowDo();
        BeanUtils.copyProperties(record, flowDo);
        return this.flowMapper.updateByPrimaryKey(flowDo);
    }

    @Override
    public List<Map<String, Object>> getFlowByArea(Integer merchantId, Long startTime, Long endTime) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        return this.flowMapper.getFlowByArea(merchantId, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getDateFlow(Integer dateType, Integer merchantId, Long startTime, Long endTime) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        //查询所有符合条件记录
        List<FlowVo> flowList = this.getFlowList(merchantId, startDate, endDate);

        Map<String, Object> map = Maps.newHashMap();
        if (1 == dateType) {
            //点
            List<String> list = DateUtil.getDay24();

            for (String dateStr:list) {
                map.put(dateStr, 0);
                for (FlowVo flowVo:flowList) {
                    String hourStr = flowVo.getPushDate().getHours() + "";
                    if (dateStr.equals(hourStr)) {
                        //更新
                        Integer num = Integer.valueOf(map.get(dateStr) + "");
                        num ++;
                        map.put(hourStr, num);
                    }
                }
            }
        }else if (2 == dateType) {
            //天
            List<String> list = DateUtil.getDayList(startDate, endDate);
            for (String dateStr:list) {
                map.put(dateStr, 0);
                for (FlowVo flowVo:flowList) {
                    String dayStr = DateUtil.dateToStr(flowVo.getPushDate());
                    if (dateStr.equals(dayStr)) {
                        //更新
                        Integer num = Integer.valueOf(map.get(dayStr) + "");
                        num ++;
                        map.put(dayStr, num);
                    }
                }
            }
        }

        List<Map<String, Object>> resultList = Lists.newArrayList();
        for(String key:map.keySet()){
            Map<String, Object> resultMap = Maps.newTreeMap();
            resultMap.put("day", key);
            resultMap.put("num", map.get(key));
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public Map<String, Object> getAllFlow(Integer merchantId, Long startTime, Long endTime) {
        Map<String, Object> resultMap = Maps.newHashMap();

        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        List<FlowVo> flowList = this.flowMapper.getFlowList(merchantId, startDate, endDate);


        //获取昨天开始结束
        Date frontStartDate = DateUtil.incr(startDate, -1);
        Date frontEndDate = DateUtil.incr(endDate, -1);
        List<FlowVo> frontFlowList = this.flowMapper.getFlowList(merchantId, frontStartDate, frontEndDate);

        resultMap.put("nowDayNum", flowList.stream().mapToInt(flowVo -> flowVo.getMenNum()).sum());
        resultMap.put("frontDayNum", frontFlowList.stream().mapToInt(flowVo -> flowVo.getMenNum()).sum());
        resultMap.put("maxDayNum", flowList.stream().max(Comparator.comparing(FlowVo::getMenNum)).get().getMenNum() );
        return resultMap;
    }

    @Override
    public List<FlowVo> getFlowList(Integer merchantId, Date startTime, Date endTime) {
        return this.flowMapper.getFlowList(merchantId,startTime,endTime);
    }
}