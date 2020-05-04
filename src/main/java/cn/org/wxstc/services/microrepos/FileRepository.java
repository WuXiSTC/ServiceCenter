package cn.org.wxstc.services.microrepos;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URI;

@Repository
@EnableConfigurationProperties({FileRepositoryProperties.class})
public class FileRepository {
    FileRepositoryProperties properties;

    private URI makeURL(String path) {
        return UriComponentsBuilder.newInstance()
                .scheme(properties.getProtocol())
                .host(properties.getHost())
                .port(properties.getPort())
                .path(properties.getRootPath())
                .path(path)
                .build().encode().toUri();
    }

    public File Get(String path) {
        return RequestTools.GetFile(makeURL(path));
    }

    private JSONObject put(URI url, File file, MultiValueMap<String, String> Hashs) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParams(Hashs);
        return RequestTools.PostFile(builder.build().encode().toUri(), file);
    }

    public JSONObject Put(String path, File file) {
        MultiValueMap<String, String> Hashs = new LinkedMultiValueMap<>();
        //TODO:文件加密
        return put(makeURL(path), file, Hashs);
    }
}
