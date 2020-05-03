package cn.org.wxstc.services.microrepos;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

@Repository
@EnableConfigurationProperties({TestNetRepositoryProperties.class})
public class TestNetRepository {
    @Resource
    TestNetRepositoryProperties properties;

    private URI makeURL(String op, UUID ID) {
        return UriComponentsBuilder.newInstance()
                .scheme(properties.getProtocol())
                .host(properties.getHost())
                .port(properties.getPort())
                .path(properties.getTaskOperationPath())
                .path(op).path(ID.toString())
                .build().encode().toUri();
    }

    public JSONObject New(UUID ID, File jmx) {
        URI url = makeURL("/new", ID);
        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.add("jmx", new FileSystemResource(jmx));
        return RequestTools.Post(url, request);
    }

    public JSONObject Start(UUID ID) {
        URI url = makeURL("/start", ID);
        return RequestTools.Get(url, new HashMap<>());
    }

    public JSONObject Stop(UUID ID) {
        URI url = makeURL("/stop", ID);
        return RequestTools.Get(url, new HashMap<>());
    }

    public JSONObject Delete(UUID ID) {
        URI url = makeURL("/delete", ID);
        return RequestTools.Get(url, new HashMap<>());
    }

    public JSONObject getState(UUID ID) {
        URI url = makeURL("/getState", ID);
        return RequestTools.Get(url, new HashMap<>());
    }

    public File getConfig(UUID ID) {
        URI url = makeURL("/getConfig", ID);
        return RequestTools.GetFile(url);
    }

    public File getLog(UUID ID) {
        URI url = makeURL("/getLog", ID);
        return RequestTools.GetFile(url);
    }

    public File getResult(UUID ID) {
        URI url = makeURL("/getResult", ID);
        return RequestTools.GetFile(url);
    }
}
