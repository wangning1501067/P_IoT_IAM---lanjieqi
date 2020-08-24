package com.beyondsoft.rdc.cloud.iot.iam.server.images.util;

import com.beyondsoft.rdc.cloud.iot.iam.common.constant.IAMConstant;
import com.beyondsoft.rdc.cloud.iot.iam.common.util.DateUtil;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesVo;
import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ImagesUtil {

    /**
     * 图片是否符合推送规则
     * @return
     */
    public static List<Map<String, Object>> getImagesListMap(List<Map<String, Object>> mapList) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> resultList = Lists.newArrayList();

        //规则：是否在有效期内
        for (Map<String, Object> map : mapList) {
            if(IAMConstant.KEY_STATUS_YES == Integer.valueOf(map.get("imagestimestart")+"")){
                resultList.add(map);
                continue;
            }else{
                Boolean flag = DateUtil.isEffectiveDate(DateUtil.getNowDate(), sdf.parse(map.get("imagesstarttime")+""), sdf.parse(map.get("imagesendtime")+""));
                if(flag){
                    resultList.add(map);
                    continue;
                }
            }
        }

        return resultList;
    }

    /**
     * 图片是否符合推送规则
     * @return
     */
    public static List<ImagesVo> getImagesList(List<ImagesVo> imagesVoList){
        List<ImagesVo> resultList = Lists.newArrayList();

        Date date = new Date();
        //规则：是否在有效期内
        for (ImagesVo imagesVo : imagesVoList) {
            if(IAMConstant.KEY_STATUS_YES == imagesVo.getImagesTimeStart()){
                resultList.add(imagesVo);
                continue;
            }else{
                Boolean flag = DateUtil.isEffectiveDate(DateUtil.getNowDate(), imagesVo.getImagesStartTime(), imagesVo.getImagesEndTime());
                if(flag){
                    resultList.add(imagesVo);
                    continue;
                }
            }
        }

        return resultList;
    }
}
