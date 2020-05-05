package cn.org.wxstc.services.api;

import cn.org.wxstc.services.entity.Test;
import com.alibaba.fastjson.JSONObject;

import java.util.UUID;

public class ServiceTools {
    static public String getPathByIDAndUserAndType(UUID ID, String USER, String Type) {
        return USER + "/" + Type + "/" + ID.toString() + "." + Type;
    }

    static public boolean IsOk(JSONObject obj) {
        return obj == null || !obj.get("ok").equals("true");
    }

    static public boolean AlreadyRun(Test test) {
        return test.getJTLPath() != null || test.getLOGPath() != null ||
                (!test.getJTLPath().equals("")) || (!test.getLOGPath().equals(""));
    }
}
