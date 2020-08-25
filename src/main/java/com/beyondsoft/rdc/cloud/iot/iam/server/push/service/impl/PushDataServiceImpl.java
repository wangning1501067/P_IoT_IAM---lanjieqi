package com.beyondsoft.rdc.cloud.iot.iam.server.push.service.impl;

import com.beyondsoft.rdc.cloud.iot.iam.client.websocket.server.WebSocketServer;
import com.beyondsoft.rdc.cloud.iot.iam.common.util.DateUtil;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.service.IamDeviceService;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.mapper.PushDataMapper;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.PushDataBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.PushDataDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.model.PushDataVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.push.service.PushDataService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PushDataServiceImpl implements PushDataService {

    @Autowired
    private PushDataMapper pushDataMapper;

    @Autowired
    private IamDeviceService deviceService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.pushDataMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PushDataBo record) {
        PushDataDo pushDataDo = new PushDataDo();
        BeanUtils.copyProperties(record, pushDataDo);
        return this.pushDataMapper.insert(pushDataDo);
    }

    @Override
    public int batchInsert(List<PushDataBo> pushDataBoList) {
        return this.pushDataMapper.batchInsert(pushDataBoList);
    }

    @Override
    public int insertSelective(PushDataBo record) {
        PushDataDo pushDataDo = new PushDataDo();
        BeanUtils.copyProperties(record, pushDataDo);
        return this.pushDataMapper.insertSelective(pushDataDo);
    }

    @Override
    public PushDataVo selectByPrimaryKey(Integer id) {
        return this.pushDataMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PushDataBo record) {
        PushDataDo pushDataDo = new PushDataDo();
        BeanUtils.copyProperties(record, pushDataDo);
        return this.pushDataMapper.updateByPrimaryKeySelective(pushDataDo);
    }

    @Override
    public int updateByPrimaryKey(PushDataBo record) {
        PushDataDo pushDataDo = new PushDataDo();
        BeanUtils.copyProperties(record, pushDataDo);
        return this.pushDataMapper.updateByPrimaryKey(pushDataDo);
    }

    @Override
    public Map<String, Object> getPushDataByData(Long pushTime, Integer marchentId) {
        return this.pushDataMapper.getPushDataByData(new Date(pushTime), marchentId);
    }

    @Override
    public Map<String, Object> getDeviceAll(Integer marchentId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        List<IamDeviceVo> deviceVoList = this.deviceService.getByBusinessId(marchentId);
        int deviceAll = deviceVoList.size();
        resultMap.put("deviceAll", deviceAll);
        int deviceOnline = WebSocketServer.getDeviceAll(marchentId);
        resultMap.put("deviceOnline", deviceOnline);

        DecimalFormat df = new DecimalFormat("0.00");
        float format = (float) deviceOnline / deviceAll;
        resultMap.put("deviceOnlineRate", df.format(Float.valueOf(format) * 100));
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> getPushChaert(Integer deviceId, Integer merchantId, Long startTime, Long endTime) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        //获取时间相差天数集合
        List<String> dayList = DateUtil.getDayList(startDate, endDate);

        List<PushDataVo> pushDateList = this.pushDataMapper.getPushChaert(deviceId, merchantId, startDate, endDate);

        Map<String, Object> dayMap = Maps.newTreeMap();
        for (String day : dayList) {
            dayMap.put(day, 0);
            for (PushDataVo pushDataVo : pushDateList) {
                String dateStr= DateUtil.dateToStr(pushDataVo.getPushDate());
                if(day.equals(dateStr)){
                    //更新
                    Integer num = Integer.valueOf(dayMap.get(dateStr) + "");
                    num ++;
                    dayMap.put(day, num);
                }
            }
        }

        List<Map<String, Object>> resultList = Lists.newArrayList();
        for(String key:dayMap.keySet()){
            Map<String, Object> resultMap = Maps.newTreeMap();
            resultMap.put("day", key);
            resultMap.put("num", dayMap.get(key));
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getAdvertList(Integer merchantId, Long startTime, Long endTime) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        return this.pushDataMapper.getAdvertList(merchantId, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getDeviceList(Integer merchantId, Long startTime, Long endTime) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        return this.pushDataMapper.getDeviceList(merchantId, startDate, endDate);
    }

    @Override
    public int batchDeleteByDeviceId(List<Integer> deviceIdList) {
        return this.pushDataMapper.batchDeleteByDeviceId(deviceIdList);
    }

    @Override
    public List<Map<String, Object>> getSexData(Integer labelType, Integer merchantId, Long startTime, Long endTime) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        return this.pushDataMapper.getSexData(labelType, merchantId, startDate, endDate);
    }
}