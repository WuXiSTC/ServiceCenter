package cn.org.wxstc.services;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ServicesApplicationTests {

    @Test
    void contextLoads() {
        String aaa = ResponseEntity.ok().body(null).toString();
        System.out.println(aaa);
    }

}
