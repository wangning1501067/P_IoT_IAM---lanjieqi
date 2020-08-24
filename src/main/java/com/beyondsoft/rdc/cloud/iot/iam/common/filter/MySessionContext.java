package com.beyondsoft.rdc.cloud.iot.iam.common.filter;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 全局静态map自己实现一个SessionContext
 */
public class MySessionContext {

    private static MySessionContext instance;
    private HashMap<String,HttpSession> mymap  = new HashMap();

    private MySessionContext() {
        mymap = new HashMap<String,HttpSession>();
    }

    public static MySessionContext getInstance() {
        if (instance == null) {
            instance = new MySessionContext();
        }
        return instance;
    }

    public synchronized void addSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public synchronized void removeSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    public synchronized HttpSession getSession(String session_id) {
        if (session_id == null){
            return null;
        }
        return (HttpSession) mymap.get(session_id);
    }
}
