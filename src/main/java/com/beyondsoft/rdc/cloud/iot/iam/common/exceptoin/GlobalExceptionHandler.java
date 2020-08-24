package com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin;

import com.beyondsoft.rdc.cloud.iot.iam.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public BaseResponse baseExceptionHandler(HttpServletResponse response, BaseException ex, HttpServletRequest request) {
        //exceptionHandler(response, ex, request);
        //logger.error(ex.getMessage(), ex);
        //response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(GeneralException.class)
    public BaseResponse otherGeneralExceptionHandler(HttpServletResponse response, GeneralException ex2, HttpServletRequest request) {
        //log.error(ex2.getMessageInfo(), ex2);
        ex2.printStackTrace();
        BaseResponse baseResponse = new BaseResponse(ex2.getCode(), ex2.getMessageInfo());
        return baseResponse;
    }

    @ExceptionHandler(LoginException.class)
    public BaseResponse otherLoginExceptionHandler(HttpServletResponse response, LoginException ex2, HttpServletRequest request) {
        //log.error(ex2.getMessageInfo(), ex2);
        ex2.printStackTrace();
        BaseResponse baseResponse = new BaseResponse(ex2.getCode(), ex2.getMessageInfo());
        return baseResponse;
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse otherExceptionHandler(HttpServletResponse response, Exception ex, HttpServletRequest request){
        //exceptionHandler(response, ex, request);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error(ex.getMessage(),ex);
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    /*public void exceptionHandler(HttpServletResponse response, Exception ex, HttpServletRequest request){
        Map<Object, Object> map = new HashMap<>();
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            map.put(paraName,request.getParameter(paraName));
        }
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多IP的情况（只取第一个IP）
        if (ip != null && ip.contains(",")) {
            String[] ipArray = ip.split(",");
            ip = ipArray[0];
        }
        //环境判断
        if (env.equals("2") && env != null){
            //邮件集合
            String arr[]={"iot-dev@beyondsoft.com"};
            for (int i = 0 ; i < arr.length ; i++){
                String mail = SendEmail.sendEmails(ip, request.getMethod(), request.getRequestURL(), name, ex.getMessage(),map,null);
                SendEmailUtil.send(arr[i], mail, null, ResponseStatusConstant.INTERNAL_SERVER_ERROR);
            }
        }
    }*/
}
