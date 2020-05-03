package cn.org.wxstc.services.controller;

import cn.org.wxstc.services.api.UserAuth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Resource
    UserAuth userAuth;

    @RequestMapping(value = "/login")
    public boolean login(HttpServletRequest request,
                         @RequestParam(value = "ID") String ID,
                         @RequestParam(value = "PASS") String PASS) {
        boolean ok = userAuth.Verify(ID, PASS);
        if (ok) SessionTools.SetUSER(request, ID);
        return ok;
    }
}
