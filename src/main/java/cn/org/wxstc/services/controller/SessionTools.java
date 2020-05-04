package cn.org.wxstc.services.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    static public ResponseEntity<JSONObject> JSON(boolean ok, String message, HttpStatus status) {
        JSONObject o = new JSONObject();
        o.put("ok", ok);
        o.put("message", message);
        return new ResponseEntity<>(o, status);
    }
}
