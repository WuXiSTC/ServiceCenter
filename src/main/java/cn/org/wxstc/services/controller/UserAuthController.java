package cn.org.wxstc.services.controller;

import cn.org.wxstc.services.microrepos.UserAuthRepository;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserAuthController {
    @Resource
    UserAuthRepository userAuthRepository;

    private ResponseEntity<Boolean> UserAuthJSONVerify(HttpServletRequest request, JSONObject obj, String ID) {
        if (obj == null)//为空则服务器错误
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        Object ok = obj.get("ok");
        if (ok != null && !ok.equals(true) && !ok.equals("true"))//ok不为true即false
            return new ResponseEntity<>(false, HttpStatus.OK);
        SessionTools.SetUSER(request, ID);//写Session
        return new ResponseEntity<>(true, HttpStatus.OK);//返回true
    }

    @RequestMapping(value = "/user/login")
    public ResponseEntity<Boolean> login(HttpServletRequest request,
                                         @RequestParam(value = "ID") String ID,
                                         @RequestParam(value = "PASS") String PASS) {
        JSONObject obj = userAuthRepository.Verify(ID, PASS);
        return UserAuthJSONVerify(request, obj, ID);
    }


    @RequestMapping(value = "/user/register")
    public ResponseEntity<Boolean> register(HttpServletRequest request,
                                            @RequestParam(value = "ID") String ID,
                                            @RequestParam(value = "PASS") String PASS) {
        JSONObject obj = userAuthRepository.Register(ID, PASS);
        return UserAuthJSONVerify(request, obj, ID);
    }

    @RequestMapping(value = "/user/update")
    public ResponseEntity<Boolean> update(HttpServletRequest request,
                                          @RequestParam(value = "ID") String ID,
                                          @RequestParam(value = "PASS") String PASS,
                                          @RequestParam(value = "newPASS") String newPASS) {
        JSONObject obj = userAuthRepository.Update(ID, PASS, newPASS);
        return UserAuthJSONVerify(request, obj, ID);
    }
}
