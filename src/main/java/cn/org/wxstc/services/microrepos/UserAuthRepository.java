package cn.org.wxstc.services.microrepos;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;

@Service
@EnableConfigurationProperties({UserAuthRepositoryProperties.class})
public class UserAuthRepository {
    @Resource
    private UserAuthRepositoryProperties properties;

    private URI makeURL(String op) {
        return UriComponentsBuilder.newInstance()
                .scheme(properties.getProtocol())
                .host(properties.getHost())
                .port(properties.getPort())
                .path(op)
                .build().encode().toUri();
    }

    private JSONObject Op(String ID, String PASS, String path) {
        URI URL = makeURL(path);
        MultiValueMap<String, Object> postData = new LinkedMultiValueMap<>();
        postData.add("ID", ID);
        postData.add("PASS", PASS);
        return RequestTools.Post(URL, postData);

    }

    public JSONObject Verify(String ID, String PASS) {
        return Op(ID, PASS, properties.getVerifyPath());
    }

    public JSONObject Register(String ID, String PASS) {
        return Op(ID, PASS, properties.getRegisterPath());
    }

    public JSONObject Update(String ID, String PASS, String newPASS) {
        URI URL = makeURL(properties.getUpdatePath());
        MultiValueMap<String, Object> postData = new LinkedMultiValueMap<>();
        postData.add("ID", ID);
        postData.add("PASS", PASS);
        postData.add("newPASS", newPASS);
        return RequestTools.Post(URL, postData);
    }
}
