package cn.org.wxstc.services.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
@EnableConfigurationProperties({UserAuthProperties.class})
public class UserAuth {
    @Resource
    private UserAuthProperties properties;
    private RestTemplate restTemplate = new RestTemplate();

    public boolean Verify(String ID, String PASS) {
        String URL = "http://" + properties.getHost() + ":" + properties.getPort() + properties.getVerifyPath();
        MultiValueMap<String, String> postData = new LinkedMultiValueMap<>();
        postData.add("ID", ID);
        postData.add("PASS", PASS);
        JSONObject json = RequestTools.Post(URL, postData);
        assert json != null;
        return (boolean) json.get("ok");
    }
}
