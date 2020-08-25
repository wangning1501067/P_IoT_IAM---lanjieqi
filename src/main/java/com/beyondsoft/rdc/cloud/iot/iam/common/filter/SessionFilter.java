package com.beyondsoft.rdc.cloud.iot.iam.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beyondsoft.rdc.cloud.iot.iam.common.constant.ResponseStatusConstant;
import com.beyondsoft.rdc.cloud.iot.iam.common.constant.SessionConstant;
import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.ExceptionCode;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class SessionFilter implements Filter {

    //标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
    String SERVER_NO_LOGIN = "您还未登录商户后台";

    String CLIENT_NO_LOGIN = "您还未登录设备前台";

    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{
            /*"/login",*/
            "/no-login/",
            "/server/user/",
            "/socket",
            "/iam/client/label/labelImages"
    };


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /**
         * request.getSession(true)：若存在会话则返回该会话，否则新建一个会话。
         * request.getSession(false)：若存在会话则返回该会话，否则返回NULL
         */
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        log.debug("filter url:"+uri);
        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);

        if (!needFilter) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            // session中包含user对象,则是登录状态
            if (uri.contains("/server/")) {
                ifEnd(filterChain, request, response, session, SessionConstant.SERVER_SESSION_USER, this.SERVER_NO_LOGIN);
            }else if (uri.contains("/client/")){
                //ifEnd(filterChain, request, response, session, SessionConstant.CLIENT_SESSION_USER, this.CLIENT_NO_LOGIN);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    private void ifEnd(FilterChain filterChain, HttpServletRequest request, HttpServletResponse response, HttpSession session, String sessionUser, String content) throws IOException, ServletException {
        if (session == null || session.getAttribute(sessionUser) == null) {
            response.setContentType("application/json; charset=utf-8");
            JSONObject res = new JSONObject();
            res.put("message", content);
            res.put("status", ExceptionCode.LOFIN_FAIL);
            log.debug("===检测地址未登录===filter==res:{}>sessUser:{}",res, session == null ? null : JSON.toJSONString(session.getAttribute(sessionUser)) + "====");

            PrintWriter writer = response.getWriter();
            writer.write(res + "");
            writer.flush();
            writer.close();
            //throw new LoadException(content);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * @Author: xxxxx
     * @Description: 是否需要过滤
     * @Date: 2018-03-12 13:20:54
     * @param uri
     */
    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if(uri.contains(includeUrl)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
