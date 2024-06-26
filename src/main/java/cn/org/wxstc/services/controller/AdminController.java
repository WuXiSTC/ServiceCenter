package cn.org.wxstc.services.controller;

import cn.org.wxstc.services.api.AdminService;
import cn.org.wxstc.services.entity.Test;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@RestController
public class AdminController {
    @javax.annotation.Resource
    AdminService adminService;

    @RequestMapping(value = "/admin/getTask/{ID}")
    public ResponseEntity<Test> GetTask(HttpServletRequest request,
                                        @PathVariable(value = "ID") UUID ID) {
        Test test = adminService.findByID(ID);
        if (test == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/Task/start/{ID}")
    public ResponseEntity<JSONObject> Start(HttpServletRequest request,
                                            @PathVariable(value = "ID") UUID ID,
                                            @RequestParam(value = "duration") long duration) {
        try {
            JSONObject obj = adminService.StartByID(ID, duration);
            if (obj == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/admin/Task/stop/{ID}")
    public ResponseEntity<JSONObject> Stop(HttpServletRequest request, @PathVariable(value = "ID") UUID ID) {
        try {
            JSONObject obj = adminService.StopByID(ID);
            if (obj == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/admin/Task/get/{ID}/{Type}")
    public ResponseEntity<Resource> Get(HttpServletRequest request,
                                           @PathVariable(value = "Type") String Type,
                                           @PathVariable(value = "ID") UUID ID) {
        try {
            InputStream file = adminService.getFileByIDAndType(ID, Type);
            if (file == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(new InputStreamResource(file), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/admin/Task/getState/{ID}")
    public ResponseEntity<JSONObject> GetState(HttpServletRequest request,
                                               @PathVariable(value = "ID") UUID ID) {
        try {
            JSONObject obj = adminService.getStateByID(ID);
            if (obj == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/admin/getTasks")
    public ResponseEntity<JSONObject> GetTasks(HttpServletRequest request) {
        try {
            return new ResponseEntity<>(adminService.getTasks(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/admin/getGraph")
    public ResponseEntity<JSONObject> GetGraph(HttpServletRequest request) {
        try {
            return new ResponseEntity<>(adminService.getGraph(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
