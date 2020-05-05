package cn.org.wxstc.services;

import cn.org.wxstc.services.api.TestGetService;
import cn.org.wxstc.services.api.TestOpService;
import cn.org.wxstc.services.microrepos.FileRepository;
import cn.org.wxstc.services.microrepos.RequestTools;
import cn.org.wxstc.services.microrepos.TempProperties;
import cn.org.wxstc.services.microrepos.TestNetRepository;
import com.alibaba.fastjson.JSONObject;
import com.datastax.driver.core.utils.UUIDs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;
import java.io.*;
import java.util.UUID;

@SpringBootTest
@EnableConfigurationProperties({TempProperties.class})
public class TestNetTests {
    @Resource
    TestNetRepository testNetRepository;
    @Resource
    TestOpService testOpService;
    @Resource
    TestGetService testGetService;

    @Test
    void contextLoads() {
        System.out.println(testNetRepository.getGraph());
        System.out.println(testNetRepository.getTasks());
    }
}
