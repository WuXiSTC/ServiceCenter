package cn.org.wxstc.services;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServicesApplicationTests {

    @Test
    void contextLoads() {
        JSONObject obj = new JSONObject();
        obj.put("ok",true);
        Object ok = obj.get("okk");
        System.out.println(ok.equals(true));
    }

}
