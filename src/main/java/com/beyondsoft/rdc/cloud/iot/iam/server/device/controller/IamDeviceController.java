package com.beyondsoft.rdc.cloud.iot.iam.server.device.controller;

import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.GeneralException;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.service.IamDeviceService;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author shkstart
 * @create 2020-07-28 14:35
 */

@RestController
@RequestMapping("/iam/server/device")
@Slf4j
public class IamDeviceController {

    @Autowired
    private IamDeviceService service;

    /**
     * 添加
     */
    @PostMapping("/save")
    public ObjectRestResponse save(@RequestBody @Valid IamDeviceBo iamDeviceBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.service.insertSelective(iamDeviceBo);
            restResponse.setData(num);
        } catch (GeneralException e) {
            log.error("===设备添加失败===", e.getMessageInfo());
            restResponse.setMessage(e.getMessageInfo());
            restResponse.setStatus(400);
        }
        return restResponse;
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("deviceId") Integer deviceId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.service.deleteByPrimaryKey(deviceId);
            restResponse.setData(num);
        } catch (GeneralException e) {
            log.error("===设备删除失败===", e);
            restResponse.setMessage(e.getMessageInfo());
            restResponse.setStatus(e.getCode());
        } catch (Exception e) {
            log.error("===设备删除失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ObjectRestResponse update(@RequestBody IamDeviceBo iamDeviceBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int result = this.service.updateByPrimaryKeySelective(iamDeviceBo);
            restResponse.setData(result);
        } catch (Exception e) {
            log.error("===设备修改失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }


    /**
     * 查询单个
     */
    @GetMapping("/getOne")
    public ObjectRestResponse getOne(@RequestParam("deviceId") Integer deviceId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            IamDeviceVo iamDeviceVo = this.service.selectByPrimaryKey(deviceId);
            restResponse.setData(iamDeviceVo);
        } catch (Exception e) {
            log.error("===设备查询单个失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }


    /**
     * 集合
     */
    @GetMapping("/getList")
    public ObjectRestResponse getListStorey(@RequestParam(value = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                            @RequestParam(value = "deviceLocation",required = false) String deviceLocation,
                                            @RequestParam(value = "status",required = false) Integer status,
                                            @RequestParam(value = "deviceName",required = false) String deviceName
                                            ) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        //if(pageIndex != null && pageSize != null){
            PageHelper.startPage(pageIndex, pageSize);
            List<IamDeviceVo> list = this.service.getList(deviceLocation,status,deviceName);
            PageInfo pageInfo = new PageInfo(list);
            restResponse.setData(pageInfo);
        /*}else {
            List<IamDeviceVo> labelVoList = this.service.getList(deviceLocation,status,deviceName);
            restResponse.setData(labelVoList);
        }*/
        return restResponse;
    }


    /**
     * 不分页查询所有
     * @param deviceLocation
     * @param status
     * @param deviceName
     * @return
     */
    @GetMapping("/getByList")
    public ObjectRestResponse getByList(@RequestParam(value = "deviceLocation",required = false) String deviceLocation,
                                        @RequestParam(value = "status",required = false) Integer status,
                                        @RequestParam(value = "deviceName",required = false) String deviceName
    ) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        //if(pageIndex != null && pageSize != null){
        List<IamDeviceVo> list = this.service.getList(deviceLocation,status,deviceName);
        //PageInfo pageInfo = new PageInfo(list);
        restResponse.setData(list);
        /*}else {
            List<IamDeviceVo> labelVoList = this.service.getList(deviceLocation,status,deviceName);
            restResponse.setData(labelVoList);
        }*/
        return restResponse;
    }

    /**
     * 查询所有区域
     * @return
     */
    @GetMapping("/getListArea")
    public ObjectRestResponse getListArea() {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<String> listArea = this.service.getListArea();
        restResponse.setData(listArea);
        return restResponse;
    }

    /**
     * 根据区域查询设备信息
     * @param areaName
     * @return
     */
    /*@GetMapping("/getByAreaList")
    public ObjectRestResponse getByAreaList(@RequestParam("areaName") String areaName) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<IamDeviceVo> byAreaList = this.service.getByAreaList(areaName);
        restResponse.setData(byAreaList);
        return restResponse;
    }*/

    /**
     * 根据状态查询设备信息
     * @param status
     * @return
     */
    /*@GetMapping("/getByStatusList")
    public ObjectRestResponse getByStatusList(@RequestParam("status") Integer status) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<IamDeviceVo> byStatusList = this.service.getByStatusList(status);
        restResponse.setData(byStatusList);
        return restResponse;
    }*/

    /**
     * 根据设备名称模糊查找设备信息
     * @param name
     * @return
     */
    /*@GetMapping("/getByDeviceNameList")
    public ObjectRestResponse getByDeviceNameList(@RequestParam("name") String name) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<IamDeviceVo> byDeviceNameList = this.service.getByDeviceNameList(name);
        restResponse.setData(byDeviceNameList);
        return restResponse;
    }*/


    /**
     * 根据设备名称和商户id获取设备信息
     * @param name
     * @param id
     * @return
     */
    @GetMapping("/getByNameAndId")
    public ObjectRestResponse getByNameAndId(@RequestParam("name") String name,@RequestParam("id") Integer id) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        IamDeviceVo byNameAndId = this.service.getByNameAndId(id, name);
        restResponse.setData(byNameAndId);
        return restResponse;
    }

    /**
     * 根据设备编号和商户id获取设备信息
     * @param number
     * @param id
     * @return
     */
    @GetMapping("/getByNumberAndId")
    public ObjectRestResponse getByNumberAndId(@RequestParam("number") String number,@RequestParam("id") Integer id) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        IamDeviceVo byNumberAndId = this.service.getByNumberAndId(id, number);
        restResponse.setData(byNumberAndId);
        return restResponse;
    }

    /**
     * 根据商户id查询设备信息
     * @param id
     * @return
     */
    @GetMapping("/getByBusinessId")
    public ObjectRestResponse getByBusinessId(@RequestParam("id") Integer id) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<IamDeviceVo> byBusinessId = this.service.getByBusinessId(id);
        restResponse.setData(byBusinessId);
        return restResponse;
    }





}
