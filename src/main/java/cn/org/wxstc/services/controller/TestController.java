package cn.org.wxstc.services.controller;

import cn.org.wxstc.services.api.TestNet;
import cn.org.wxstc.services.entity.Test;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class TestController {
    @Resource
    TestNet testNet;

    @RequestMapping(value = "/getTests")
    public JSON getTests(HttpServletRequest request) {
        String USER = SessionTools.GetUSER(request);
        if (USER != null) return testNet.FindAllByUser(USER);
        return null;
    }

    @RequestMapping(value = "/getTest")
    public Test getTest(HttpServletRequest request, @RequestParam(value = "ID") UUID ID) {
        String USER = SessionTools.GetUSER(request);
        if (USER != null) return testNet.FindByIDAndUser(ID, USER);
        return null;
    }
}
