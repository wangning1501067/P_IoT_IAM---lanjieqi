package com.beyondsoft.rdc.cloud.iot.iam.client.websocket.server;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beyondsoft.rdc.cloud.iot.iam.client.user.model.LabelImagesMobelBo;
import com.beyondsoft.rdc.cloud.iot.iam.client.user.model.LabelImagesMobelVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.model.IamDeviceDo;
import com.beyondsoft.rdc.cloud.iot.iam.server.device.service.IamDeviceService;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.model.ImagesVo;
import com.beyondsoft.rdc.cloud.iot.iam.server.images.service.ImagesService;
import com.beyondsoft.rdc.cloud.iot.iam.server.user.model.LoginModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket服务端
 */
@ServerEndpoint("/iam/socket")
@RestController
@Slf4j
public class WebSocketServer {
    //private static final AtomicInteger userId = new AtomicInteger(1);

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<String, WebSocketServer>();
/*
    Map map = Collections.synchronizedMap(new HashMap());

    Collections*/
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 这里使用静态，让 service 属于类
    private static ImagesService imagesService;


    @Autowired
    public void setChatService(ImagesService imagesService) {
        WebSocketServer.imagesService = imagesService;
    }

    private static IamDeviceService iamDeviceService;

    @Autowired
    public void setChatService1(IamDeviceService iamDeviceService) {
        WebSocketServer.iamDeviceService = iamDeviceService;
    }



    /**
     * 建立连接成功调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        log.debug("webSocket连接成功");
        this.session = session;
        webSocketSet.put(session.getQueryString(), this);//加入map中     //加入set中

        // 获取请求url
        /*String url = session.getRequestURI().toString();
        log.debug("链接地址" + url);*/

        // 获取参数
        /*if (session.getRequestParameterMap().get("userId") != null) {
            log.debug("userId=" +session.getRequestParameterMap().get("userId").get(0) +"==>deviceCode="+session.getRequestParameterMap().get("deviceCode").get(0));
        }*/
        String merchantId = session.getRequestParameterMap().get("merchantId").get(0);
        String deviceCode = session.getRequestParameterMap().get("deviceCode").get(0);
        List<ImagesVo> deviceByImages = imagesService.getDeviceByImages(Integer.valueOf(merchantId), deviceCode, null, null);

        JSONObject json = new JSONObject();
        json.put("type", 1);
        JSONArray subMsgs = JSONArray.parseArray(JSON.toJSONString(deviceByImages));
        json.put("data", subMsgs);
        try {
            sendMessage(session, json+"");
        } catch (Exception e) {
            log.error("websocket IO连接异常", e);
        }


        session.setMaxIdleTimeout(shutdown());

        //修改设备状态
        iamDeviceService.updateByDeviceId(Integer.valueOf(merchantId),deviceCode,1);

    }

    public Long shutdown(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int seconds = 300000;
                while (seconds > 0) {
                    try {
                        Thread.sleep(1000);
                        seconds--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(seconds == 0){
                    onClose();
                }
            }
        }).start();
        return 300000L;
    }

    /*private Object getResult() {
        Map<String, Object> result = new HashMap<>();
        result.put("seg_id", segId.getAndIncrement());
        return result;
    }*/

    /**
     * 关闭连接时调用
     */
    @OnClose
    public void onClose() {
        try {
            String merchantId = session.getRequestParameterMap().get("merchantId").get(0);
            String deviceCode = session.getRequestParameterMap().get("deviceCode").get(0);
            webSocketSet.remove(session.getQueryString());  //从set中删除
            //subOnlineCount();           //在线数减1
            session.close();
            //log.info("有一连接关闭！当前在线人数为" + getOnlineCount());

            //修改设备状态
            iamDeviceService.updateByDeviceId(Integer.valueOf(merchantId),deviceCode,0);
        } catch (IOException e) {
            log.error("websocket IO关闭异常", e);
        }
        log.debug("webSocket连接关闭");
    }

    /**
     * 收到客户端信息
     * @param message
     * @param session
     */
    /*@OnMessage
    public void onMessage(String message, Session session) {
        log.debug("I'm String onMessage.");
        sendMessage(session, JSON.toJSONString(getResult()));
    }*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.debug("收到的信息:"+message);
        sendMessage(session, message);

        /*log.info("【WebSocket消息】接收心跳:{}"+ message);
        if("心跳链接测试".equals(message)) {

        } else {
            // 可以添加
            // OsWebSocketGoods vo = JSONObject.parseObject(message, OsWebSocketGoods.class);
        }*/
    }

    /*@OnMessage
    public void onMessage(byte[] message, Session session) {
        log.debug("I'm byte onMessage.");
        sendMessage(session, JSON.toJSONString(getResult()));
    }*/

    /**
     * 错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.debug("webSocket发生错误" + error.getMessage());
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(Session session, String message) {
        /*if (session == null){
            return;
        }*/
        /*if (session.isOpen() && message.length() != 0) {*/

        synchronized(this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.debug("websocket发送消息异常 " + e.getMessage());
                e.printStackTrace();
            }
        }
        /*}*/
    }

    /**
     * 图片变更推送消息
     */
    public static void sendImagesUpdate(Integer merchantId, List<IamDeviceDo> deviceDoList) {
        for (IamDeviceDo deviceDo : deviceDoList) {
            String deviceNumber = deviceDo.getDeviceNumber();
            List<ImagesVo> deviceByImages = imagesService.getDeviceByImages(merchantId, deviceNumber, null, null);
            StringBuffer sb = new StringBuffer();
            sb.append("merchantId="+merchantId+"&deviceCode="+deviceNumber);
            if (webSocketSet.get(sb+"") != null) {
                JSONObject json = new JSONObject();
                json.put("type", 1);
                JSONArray subMsgs = JSONArray.parseArray(JSON.toJSONString(deviceByImages));
                json.put("data", subMsgs);
                log.debug("===图片变更推送消息===>{}", json);
                webSocketSet.get(sb+"").sendMessage(null, json+"");
            } else {
                log.debug(sb+"==>当前用户不在线");
            }
        }
    }

    /**
     * 指定用户发消息
     * @param labelImagesMobelVo
     * @param labelImagesMobel
     */
    public static void sendToUser(LabelImagesMobelVo labelImagesMobelVo, LabelImagesMobelBo labelImagesMobel) {
        StringBuffer sb = new StringBuffer();
        sb.append("merchantId="+labelImagesMobel.getMerchantId()+"&deviceCode="+labelImagesMobel.getDeviceCode());
        if (webSocketSet.get(sb+"") != null) {
            JSONObject json = new JSONObject();
            json.put("type", 2);
            //JSONArray jArray = new JSONArray();
            /*mapList.stream().forEach(map -> {
                jArray.add(JSONObject.parseObject(JSONUtils.toJSONString(labelImagesMobelVo)));
            });*/
            //json.put("data", jArray);
            json.put("data", JSONArray.toJSON(labelImagesMobelVo));
            log.debug("===标签比对图片推送消息===>{}", json);
            webSocketSet.get(sb+"").sendMessage(null, json+"");
        } else {
            log.debug(sb+"==>当前用户不在线");
        }
    }

    /**
     * 您输入的设备编号已在别处登录
     */
    public boolean ifDeviceExist(LoginModel loginModel) {
        boolean flag = false;

        List<String> strList = Lists.newArrayList();
        for(String key : webSocketSet.keySet()){
            String[] strArray = key.split("&");
            for (String str:strArray) {
                String[] param = str.split("=");
                strList.add(param[1]);
            }
        }

        if(!strList.contains(loginModel.getDeviceCode())){
            flag = true;
        }
        return flag;
    }

    /**
     * 设备在线总数
     */
    public static int getDeviceAll(Integer merchantId){
        int num = 0;
        StringBuffer sb = new StringBuffer();
        sb.append("merchantId="+merchantId);
        for(String key : webSocketSet.keySet()){
            if(key.contains(sb+"")){
                num ++;
            }
        }
        return num;
    }

    /**
     * 设备在线总数
     */
    public static boolean ifDeviceOnline(Integer merchantId, String deviceNumber){
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        sb.append("merchantId="+merchantId+"&deviceCode="+deviceNumber);
        for(String key : webSocketSet.keySet()){
            if(key.equals(sb+"")){
                flag = true;
            }
        }
        return flag;
    }
}