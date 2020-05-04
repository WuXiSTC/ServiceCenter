package cn.org.wxstc.services.controller;

import cn.org.wxstc.services.api.AdminService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

public class AdminController {
    @javax.annotation.Resource
    AdminService adminService;

    @RequestMapping(value = "/admin/Task/start/{ID}")
    public ResponseEntity<JSONObject> Start(HttpServletRequest request, @PathVariable(value = "ID") UUID ID) {
        try {
            return new ResponseEntity<>(adminService.StartByID(ID), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/admin/Task/stop/{ID}")
    public ResponseEntity<JSONObject> Stop(HttpServletRequest request, @PathVariable(value = "ID") UUID ID) {
        try {
            return new ResponseEntity<>(adminService.StopByID(ID), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseTools.JSON(false, e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/admin/Task/get/{ID}/{Type}")
    public ResponseEntity<Resource> Get(HttpServletRequest request,
                                        @PathVariable(value = "Type") String Type,
                                        @PathVariable(value = "ID") UUID ID) {
        try {
            File file = adminService.getFileByIDAndType(ID, Type);
            return ResponseTools.File(file);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/admin/Task/getState/{ID}")
    public ResponseEntity<JSONObject> GetState(HttpServletRequest request,
                                               @PathVariable(value = "ID") UUID ID) {
        try {
            return new ResponseEntity<>(adminService.getStateByID(ID), HttpStatus.OK);
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
