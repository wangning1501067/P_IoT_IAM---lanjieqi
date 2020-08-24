package com.beyondsoft.rdc.cloud.iot.iam.client.user.controller;

import com.beyondsoft.rdc.cloud.iot.iam.client.user.model.LabelImagesMobelBo;
import com.beyondsoft.rdc.cloud.iot.iam.client.user.model.LabelImagesMobelVo;
import com.beyondsoft.rdc.cloud.iot.iam.common.response.ObjectRestResponse;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iam/client/label")
public class ClientLabelController {

    @Autowired
    private ImagesService imagesService;

    /**
     * 标签比对图片
     */
    @PostMapping("/labelImages")
    public ObjectRestResponse labelImages(@RequestBody LabelImagesMobelBo labelImagesMobel) {
        ObjectRestResponse<Object> restResponse = new ObjectRestResponse<>();
        LabelImagesMobelVo labelImagesMobelVo = this.imagesService.getLabelByImages(labelImagesMobel);
        restResponse.setData(labelImagesMobelVo);
        return restResponse;
    }
}
