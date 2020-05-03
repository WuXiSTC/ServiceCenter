package cn.org.wxstc.services.repository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@EnableConfigurationProperties({FileRepositoryProperties.class})
public class FileRepository {
    FileRepositoryProperties properties;

    private void Put(String url, File file, Map<String, String> Hashs) {
        StringBuilder params = new StringBuilder("?");
        for (Map.Entry<String, String> hash : Hashs.entrySet())
            params.append(hash.getKey()).append("=").append(hash.getValue()).append("&");
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url + params.toString()).build().encode();
        RequestTools.PostFile(uriComponents.toUri(), file);
    }


    private File Get(URI url) {
        return RequestTools.GetFile(url);
    }

    private URI makeURL(UUID ID, String Type) {
        return UriComponentsBuilder.newInstance()
                .scheme(properties.getProtocol())
                .host(properties.getHost())
                .port(properties.getPort())
                .path("{apiPath}/{Dir}/{ID}.{Type}").build()
                .expand(properties.getApiPath(), Type, ID.toString(), Type)
                .encode().toUri();
    }

    public void PutByIDAndType(UUID ID, String Type, File file) {
        Map<String, String> Hashs = new HashMap<>();
        //TODO:文件加密
        Put(makeURL(ID, Type).toString(), file, Hashs);
    }

    public File GetByIDAndType(UUID ID, String Type) {
        return Get(makeURL(ID, Type));
    }
}
