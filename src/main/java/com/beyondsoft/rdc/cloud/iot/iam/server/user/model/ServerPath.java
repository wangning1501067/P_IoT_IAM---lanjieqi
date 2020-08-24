package com.beyondsoft.rdc.cloud.iot.iam.server.user.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ServerPath {

    //服务端websocket地址
    @Value("${websocket.server.path}")
    private String path;
}
