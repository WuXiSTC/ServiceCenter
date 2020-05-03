package cn.org.wxstc.services.repository;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;

@Repository
@EnableConfigurationProperties({TestNetRepositoryProperties.class})
public class TestNetRepository {
    @Resource
    TestNetRepositoryProperties properties;

    String baseURL = null;

    private String makeURL(String op, UUID ID) {
        if (baseURL == null) baseURL =
                properties.getProtocol() + "://" + properties.getHost() + ":" + properties.getPort() +
                        properties.getTaskOperationPath();
        return baseURL + op + "/" + ID.toString();
    }

    public JSONObject New(UUID ID, String LocolJmxPath) {
        String url = makeURL("/new", ID);
        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.add("jmx", new FileSystemResource(new File(LocolJmxPath)));
        return RequestTools.Post(url, request);
    }

    public JSONObject Start(UUID ID) {
        String url = makeURL("/start", ID);
        return RequestTools.Get(url, new HashMap<>());
    }

    public JSONObject Stop(UUID ID) {
        String url = makeURL("/stop", ID);
        return RequestTools.Get(url, new HashMap<>());
    }

    public JSONObject Delete(UUID ID) {
        String url = makeURL("/delete", ID);
        return RequestTools.Get(url, new HashMap<>());
    }

    public JSONObject getState(UUID ID) {
        String url = makeURL("/getState", ID);
        return RequestTools.Get(url, new HashMap<>());
    }

    public File getConfig(UUID ID) {
        String url = makeURL("/getConfig", ID);
        return RequestTools.GetFile(url);
    }

    public File getLog(UUID ID) {
        String url = makeURL("/getLog", ID);
        return RequestTools.GetFile(url);
    }

    public File getResult(UUID ID) {
        String url = makeURL("/getResult", ID);
        return RequestTools.GetFile(url);
    }
}
