package cn.org.wxstc.services.controller;

import cn.org.wxstc.services.api.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

public class UserTestController {
    @javax.annotation.Resource
    UserService userService;

    private ResponseEntity<JSONObject> JSON(boolean ok, String message, HttpStatus status) {
        JSONObject o = new JSONObject();
        o.put("ok", ok);
        o.put("message", message);
        return new ResponseEntity<>(o, status);
    }

    @RequestMapping(value = "/user/Task/new/{Name}")
    public ResponseEntity<JSONObject> New(HttpServletRequest request,
                                          @PathVariable(value = "Name") String Name,
                                          @RequestParam("jmx") MultipartFile file) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(new JSONObject(), HttpStatus.UNAUTHORIZED);
        if (file == null || file.isEmpty()) return JSON(false, "请上传文件", HttpStatus.BAD_REQUEST);
        try {
            File jmx = File.createTempFile("cn.org.wxstc.services", "tmp");
            file.transferTo(jmx);
            ResponseEntity<JSONObject> result = new ResponseEntity<>(
                    userService.NewByUserAndName(USER, Name, jmx), HttpStatus.OK);
            jmx.delete();
            return result;
        } catch (Exception e) {
            return JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/Task/start/{ID}")
    public ResponseEntity<JSONObject> Start(HttpServletRequest request, @PathVariable(value = "ID") UUID ID) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(new JSONObject(), HttpStatus.UNAUTHORIZED);
        try {
            return new ResponseEntity<>(userService.StartByIDAndUser(ID, USER), HttpStatus.OK);
        } catch (Exception e) {
            return JSON(false, e.toString(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/Task/stop/{ID}")
    public ResponseEntity<JSONObject> Stop(HttpServletRequest request, @PathVariable(value = "ID") UUID ID) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(new JSONObject(), HttpStatus.UNAUTHORIZED);
        try {
            return new ResponseEntity<>(userService.StopByIDAndUser(ID, USER), HttpStatus.OK);
        } catch (Exception e) {
            return JSON(false, e.toString(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/Task/get/{ID}/{Type}")
    public ResponseEntity<Resource> Get(HttpServletRequest request,
                                        @PathVariable(value = "Type") String Type,
                                        @PathVariable(value = "ID") UUID ID) {
        String USER = SessionTools.GetUSER(request);
        if (USER == null) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        File file = userService.getFileByIDAndUserAndType(ID, USER, Type);
        if (file == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
