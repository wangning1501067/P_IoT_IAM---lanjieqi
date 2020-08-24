package com.beyondsoft.rdc.cloud.iot.iam.server.area.controller;

import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.GeneralException;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.GlobalValue;
import com.beyondsoft.rdc.cloud.iot.iam.common.internation.InternationEnum;
import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.model.IamAreaBo;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.model.IamAreaVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.area.service.IamAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-07-28 17:03
 */

@RestController
@RequestMapping("/iam/server/area")
@Slf4j
public class IamAreaController {

    @Autowired
    private IamAreaService service;

    /**
     * 添加
     */
    /*@PostMapping("/save")
    public ObjectRestResponse save(@RequestBody IamAreaBo iamAreaBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.service.insertSelective(iamAreaBo);
            restResponse.setData(num);
        } catch (Exception e) {
            log.error("===区域添加失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }*/

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("areaId") Integer areaId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            int num = this.service.deleteArea(areaId);
            restResponse.setData(num);
        } catch (GeneralException e) {
            log.error("===区域删除失败===", e);
            restResponse.setMessage(e.getMessageInfo());
            restResponse.setStatus(e.getCode());
        } catch (Exception e) {
            log.error("===区域删除失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ObjectRestResponse update(@RequestBody IamAreaBo iamAreaBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            Integer result = this.service.updateByPrimaryKeySelective(iamAreaBo);
            if(result == -1){
                //有重复名称
                restResponse.setStatus(501);
                restResponse.setMessage(InternationEnum.THE_REGION_NAME_ALREADY_EXISTS.getLanguage(GlobalValue.getLanguage()));
            }else{
                restResponse.setData(result);
            }
        }catch (Exception e){
            log.error("-----------update space fail------{}",e.getMessage());
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
        }
        return restResponse;
    }

    /**
     * 查询单个
     */
    @GetMapping("/getOne")
    public ObjectRestResponse getOne(@RequestParam("areaId") Integer areaId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        try {
            IamAreaVo iamAreaVo = this.service.selectByPrimaryKey(areaId);
            restResponse.setData(iamAreaVo);
        } catch (Exception e) {
            log.error("===区域查询单个失败===", e);
            restResponse.setMessage(InternationEnum.SYSTEM_ERROR.getLanguage(GlobalValue.getLanguage()));
            restResponse.setStatus(400);
        }
        return restResponse;
    }

    /**
     * 集合，以树形式展现
     */
    @GetMapping("/getList")
    public ObjectRestResponse getListStorey(@RequestParam("merchantId") Integer merchantId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<Map<String, Object>> list = this.service.getList(merchantId);
        restResponse.setData(list);
        return restResponse;
    }



    @PostMapping("/getListArea")
    public ObjectRestResponse getListArea(@RequestBody IamAreaBo iamAreaBo) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<IamAreaVo> list = this.service.getListArea(iamAreaBo);
        restResponse.setData(list);
        return restResponse;
    }

    /**
     * 根据父级id查询数据
     * @param parentId
     * @return
     */
    @GetMapping("/getByParentIdList")
    public ObjectRestResponse getByParentIdList(@RequestParam("parentId") Integer parentId) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        List<IamAreaVo> byParentIdList = this.service.getByParentIdList(parentId);
        restResponse.setData(byParentIdList);
        return restResponse;
    }

    /**
     * 查询是否有子级目录
     * @param id
     * @return
     */
    @GetMapping("/selectChild")
    public ObjectRestResponse selectChild(@RequestParam("id") Integer id) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        Integer integer = this.service.selectChild(id);
        restResponse.setData(integer);
        return restResponse;
    }

    /**
     *添加区域
     * @param iamAreaBo
     * @return
     */
    @PostMapping("/addSpace")
    public ObjectRestResponse<Integer> addSpace(@RequestBody IamAreaBo iamAreaBo){
        ObjectRestResponse<Integer> restResponse = new ObjectRestResponse<>();
        try {
            Integer result = this.service.addSpace(iamAreaBo);
            if(result == -1){
                //空间名已经存在
                restResponse.setStatus(501);
                restResponse.setMessage(InternationEnum.THE_REGION_NAME_ALREADY_EXISTS.getLanguage(GlobalValue.getLanguage()));
            }else{
                restResponse.setData(result);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return restResponse;
    }

}
