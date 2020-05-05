package cn.org.wxstc.services.microrepos;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.util.*;

public class RequestTools {
    static public JSONObject Post(URI URL, MultiValueMap<String, Object> form_data) {
        RestTemplate restTemplate = new RestTemplate();
        FormHttpMessageConverter fc = new FormHttpMessageConverter();
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        List<HttpMessageConverter<?>> partConverters = new ArrayList<>();
        partConverters.add(stringConverter);
        partConverters.add(new ResourceHttpMessageConverter());
        fc.setPartConverters(partConverters);
        restTemplate.getMessageConverters().addAll(Arrays.asList(fc, new MappingJackson2HttpMessageConverter()));
        return restTemplate.postForEntity(URL, form_data, JSONObject.class).getBody();
    }

    static public File GetFile(URI URL) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.execute(URL, HttpMethod.GET, null, clientHttpResponse -> {
            HttpStatus status = clientHttpResponse.getStatusCode();
            if (status != HttpStatus.OK && status != HttpStatus.PARTIAL_CONTENT) {
                if (status == HttpStatus.NOT_FOUND) return null;
                throw new ServerException(clientHttpResponse.toString());
            }
            File ret = File.createTempFile("download", "tmp");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
    }

    static public JSONObject PostFile(URI URL, File file) {
        //TODO:发送文件
        return null;
    }
}
