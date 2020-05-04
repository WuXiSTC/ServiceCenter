package cn.org.wxstc.services.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;

public class ResponseTools {
    static public ResponseEntity<JSONObject> JSON(boolean ok, String message, HttpStatus status) {
        JSONObject o = new JSONObject();
        o.put("ok", ok);
        o.put("message", message);
        return new ResponseEntity<>(o, status);
    }

    static public ResponseEntity<Resource> File(File file) {
        if (file == null || !file.exists() || !file.isFile()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
