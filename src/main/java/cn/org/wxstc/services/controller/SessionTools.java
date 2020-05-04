package cn.org.wxstc.services.controller;

import javax.servlet.http.HttpServletRequest;

public class SessionTools {
    static public void SetUSER(HttpServletRequest request, String USER) {
        request.getSession().setAttribute("USER", USER);
    }

    static public String GetUSER(HttpServletRequest request) {
        Object USERObj = request.getSession().getAttribute("USER");
        if (USERObj != null) return USERObj.toString();
        return null;
    }
}
