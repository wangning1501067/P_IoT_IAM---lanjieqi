package com.beyondsoft.rdc.cloud.iot.iam.server.area.util;

import com.beyondsoft.rdc.cloud.iot.iam.server.area.model.IamAreaVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 递归构造树型结构
 * @author wangningning@beyondsoft.com
 * @version 1.0
 * @date 19-7-16 下午2:07
 */
@Slf4j
public class AreaTreeUtil {

    public List<IamAreaVo> menuCommon;
    public List<Map<String, Object>> list = Lists.newArrayList();

    public List<Map<String, Object>> menuList(List<IamAreaVo> menuList2){
        List<IamAreaVo> menuList = menuList2.stream().sorted(
                Comparator.comparing(IamAreaVo::getLevel))
                .collect(Collectors.toList());

        menuCommon = menuList;
        for (Object obj : menuList) {
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
            if (obj instanceof IamAreaVo) {
                IamAreaVo x = (IamAreaVo)obj;
                if(0 == x.getParentId()){
                    mapArr.put("id", x.getId());
                    mapArr.put("name", x.getAreaName());
                    mapArr.put("pid", x.getParentId());
                    mapArr.put("children", menuChild(x.getId()));
                    list.add(mapArr);
                }
            }
        }
        return this.cleanChild(list);
    }

    private List<Map<String, Object>> menuChild(Integer id){
        List<Map<String, Object>> lists = Lists.newArrayList();
        for(Object obj:menuCommon){
            IamAreaVo a = (IamAreaVo)obj;
            Map<String,Object> childArray = new LinkedHashMap<String, Object>();

            if(Objects.equals(a.getParentId(), id)){
                childArray.put("id", a.getId());
                childArray.put("name", a.getAreaName());
                childArray.put("pid", a.getParentId());
                childArray.put("children", menuChild(a.getId()));
                lists.add(childArray);
            }
        }
        return lists;
    }

    private List<Map<String, Object>> cleanChild(List<Map<String, Object>> mapList) {
        for (Map<String, Object> map : mapList) {
            List list = (List) map.get("children");
            if(list == null || list.size() <= 0) {
                map.remove("children");
            }else{
                cleanChild(list);
            }
        }
        return mapList;
    }
}