package com.beyondsoft.rdc.cloud.iot.iam.common.listener;

import com.beyondsoft.rdc.cloud.iot.iam.common.filter.MySessionContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

@Slf4j
public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        MySessionContext.getInstance().addSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        log.info("---sessionDestroyed----");
        HttpSession session = httpSessionEvent.getSession();
        log.info("deletedSessionId: " + session.getId());
        //System.out.println(session.getCreationTime());
        //System.out.println(session.getLastAccessedTime());
        /*ServletContext application = session.getServletContext();
        HashSet<?> sessions = (HashSet<?>) application.getAttribute("sessions");*/

        //HttpSession session = httpSessionEvent.getSession();
        MySessionContext.getInstance().removeSession(session);
    }
}
