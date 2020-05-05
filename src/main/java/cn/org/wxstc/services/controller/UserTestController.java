package cn.org.wxstc.services.controller;

import cn.org.wxstc.services.api.UserService;
import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.microrepos.TempProperties;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@EnableConfigurationProperties({TempProperties.class})
public class UserTestController {
    @javax.annotation.Resource
    UserService userService;

    @RequestMapping(value = "/user/getTask/{ID}")
    public ResponseEntity<Test> GetTask(HttpServletRequest request,
                                        @PathVariable(value = "ID") UUID ID) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Test test = userService.findByIDAndUser(ID, USER);
        if (test == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/getTasks")
    public ResponseEntity<JSONArray> GetTasks(HttpServletRequest request) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(userService.findAllByUser(USER), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/Task/new/{Name}")
    public ResponseEntity<JSONObject> New(HttpServletRequest request,
                                          @PathVariable(value = "Name") String Name,
                                          @RequestParam("jmx") MultipartFile file) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (file == null || file.isEmpty()) return ResponseTools.JSON(false, "请上传文件", HttpStatus.BAD_REQUEST);
        try {
            JSONObject obj = userService.NewByUserAndName(USER, Name, file.getInputStream());
            if (obj == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/Task/start/{ID}")
    public ResponseEntity<JSONObject> Start(HttpServletRequest request, @PathVariable(value = "ID") UUID ID) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            JSONObject obj = userService.StartByIDAndUser(ID, USER);
            if (obj == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/Task/stop/{ID}")
    public ResponseEntity<JSONObject> Stop(HttpServletRequest request, @PathVariable(value = "ID") UUID ID) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(new JSONObject(), HttpStatus.UNAUTHORIZED);
        try {
            JSONObject obj = userService.StopByIDAndUser(ID, USER);
            if (obj == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/Task/get/{ID}/{Type}")
    public ResponseEntity<?> Get(HttpServletRequest request,
                                 @PathVariable(value = "Type") String Type,
                                 @PathVariable(value = "ID") UUID ID) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            InputStream file = userService.getFileByIDAndUserAndType(ID, USER, Type);
            if (file == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(file, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/Task/getState/{ID}")
    public ResponseEntity<JSONObject> GetState(HttpServletRequest request,
                                               @PathVariable(value = "ID") UUID ID) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            JSONObject obj = userService.getStateByIDAndUser(ID, USER);
            if (obj == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
