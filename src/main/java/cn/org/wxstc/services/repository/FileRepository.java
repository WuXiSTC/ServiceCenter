package cn.org.wxstc.services.repository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    private void Put(URI url, File file, MultiValueMap<String, String> Hashs) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParams(Hashs);
        RequestTools.PostFile(builder.build().encode().toUri(), file);
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
        MultiValueMap<String, String> Hashs = new LinkedMultiValueMap<>();
        //TODO:文件加密
        Put(makeURL(ID, Type), file, Hashs);
    }

    public File GetByIDAndType(UUID ID, String Type) {
        return Get(makeURL(ID, Type));
    }
}
