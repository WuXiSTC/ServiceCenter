package cn.org.wxstc.services.microrepos;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Repository
@EnableConfigurationProperties({TestNetRepositoryProperties.class})
public class TestNetRepository {
    @Resource
    TestNetRepositoryProperties properties;
    @Resource
    private TempProperties tempProperties;

    private UriComponentsBuilder makeURIBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme(properties.getProtocol())
                .host(properties.getHost())
                .port(properties.getPort());
    }

    private UriComponentsBuilder makeTaskOpURLBuilder(String op, UUID ID) {
        return makeURIBuilder()
                .path(properties.getTaskOperationPath())
                .path(op).path("/" + ID.toString());
    }

    private URI makeTaskOpURL(String op, UUID ID) {
        return makeTaskOpURLBuilder(op, ID)
                .build().encode().toUri();
    }

    public JSONObject New(UUID ID, InputStream jmx) throws IOException {
        File file = tempProperties.TempFile();
        StreamUtils.copy(jmx, new FileOutputStream(file));
        URI url = makeTaskOpURL("/new", ID);
        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.add("jmx", new FileSystemResource(file));
        return RequestTools.Post(url, request);
    }

    public JSONObject Start(UUID ID, long duration) {
        URI url = makeTaskOpURLBuilder("/start", ID).queryParam("duration", duration)
                .build().encode().toUri();
        return Get(url);
    }

    public JSONObject Stop(UUID ID) {
        URI url = makeTaskOpURL("/stop", ID);
        return Get(url);
    }

    public JSONObject Delete(UUID ID) {
        URI url = makeTaskOpURL("/delete", ID);
        return Get(url);
    }

    public JSONObject getState(UUID ID) {
        URI url = makeTaskOpURL("/getState", ID);
        return Get(url);
    }

    public InputStream getConfig(UUID ID) {
        URI url = makeTaskOpURL("/getConfig", ID);
        return RequestTools.GetFile(url);
    }

    public InputStream getLog(UUID ID) {
        URI url = makeTaskOpURL("/getLog", ID);
        return RequestTools.GetFile(url);
    }

    public InputStream getResult(UUID ID) {
        URI url = makeTaskOpURL("/getResult", ID);
        return RequestTools.GetFile(url);
    }

    private URI makeBaseURL(String op) {
        return makeURIBuilder().path(op)
                .build().encode().toUri();
    }

    public JSONObject getTasks() {
        URI url = makeBaseURL(properties.getTasksQueryPath());
        return Get(url);

    }

    public JSONObject getGraph() {
        URI url = makeBaseURL(properties.getGraphQueryPath());
        return Get(url);
    }

    private JSONObject Get(URI URL) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.execute(URL.toString(), HttpMethod.GET, null,
                    clientHttpResponse -> new Gson().fromJson(
                            new InputStreamReader(clientHttpResponse.getBody(), StandardCharsets.UTF_8),
                            JSONObject.class));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) return null;
            throw e;
        }
    }
}
