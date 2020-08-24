package com.beyondsoft.rdc.cloud.iot.iam.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beyondsoft.rdc.cloud.iot.iam.client.user.model.LabelImagesMobelBo;
import com.beyondsoft.rdc.cloud.iot.iam.common.constant.SessionConstant;
import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.ExceptionCode;
import com.beyondsoft.rdc.cloud.iot.iam.common.exceptoin.LoginException;
import com.beyondsoft.rdc.cloud.iot.iam.common.filter.MySessionContext;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 拦截器，登录检查
 */
@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {
    //标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
    String SERVER_NO_LOGIN = "您还未登录商户后台";

    String CLIENT_NO_LOGIN = "您还未登录设备前台";

    // 目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*Object user = request.getSession().getAttribute(SessionConstant.CLIENT_SESSION_USER);
        // 如果获取的request的session中的loginUser参数为空（未登录），就返回登录页，否则放行访问
        if (user == null) {
            // 未登录，给出错误信息，
            request.setAttribute("msg","无权限请先登录");
            // 获取request返回页面到登录页
            request.getRequestDispatcher("/index.html").forward(request, response);
            return false;
        } else {
            // 已登录，放行
            return true;
        }*/

        HttpSession session = request.getSession();

        String uri = request.getRequestURI();
        log.debug("===LoginHandlerInterceptor===url:"+uri);
        // session中包含user对象,则是登录状态
        if (uri.contains("/server/")) {
            boolean b = ifEnd(request, response, session, SessionConstant.SERVER_SESSION_USER, this.SERVER_NO_LOGIN);
            if(!b){
                throw new LoginException(this.SERVER_NO_LOGIN);
            }
        }else if (uri.contains("/client/")){

            //获取请求参数
            RequestWrapper requestWrapper = new RequestWrapper(request);
            log.debug("RequestBody: {}",requestWrapper.getBodyString());//这里getBodyString()方法无参数
            String bodyString = requestWrapper.getBodyString();
            LabelImagesMobelBo stu=JSONObject.toJavaObject(JSON.parseObject(bodyString), LabelImagesMobelBo.class);

            HttpSession sess = MySessionContext.getInstance().getSession(stu.getSid());

            boolean b = ifEnd(request, response, sess, SessionConstant.CLIENT_SESSION_USER, this.CLIENT_NO_LOGIN);
            if(!b){
                throw new LoginException(this.CLIENT_NO_LOGIN);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private List<String> url = Lists.newArrayList();

    /**
     * 定义排除拦截URL
     * @return
     */
    public List<String> getUrl(){
        /*"/no-login/",
                "/server/user/",
                "/socket",*/
        url.add("/iam/client/no-login/login");
        url.add("/iam/server/user/save");
        url.add("/iam/socket");
        return url;
    }

    private boolean ifEnd(HttpServletRequest request, HttpServletResponse response, HttpSession session, String sessionUser, String content) throws IOException, ServletException {
        if (session == null) {
            return false;
        }else if(session.isNew()){
            return false;
        }else if(session.getAttribute(sessionUser) == null){
            return false;
        }
        return true;
    }
}
