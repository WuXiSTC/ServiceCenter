package cn.org.wxstc.services.microrepos;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.*;
import java.net.URI;

@Repository
@EnableConfigurationProperties({FileRepositoryProperties.class, TempProperties.class})
public class FileRepository {
    @javax.annotation.Resource
    FileRepositoryProperties properties;
    @Resource
    TempProperties tempProperties;

    private URI makeURL(String path) {
        return UriComponentsBuilder.newInstance()
                .scheme(properties.getProtocol())
                .host(properties.getHost())
                .port(properties.getPort())
                .path(properties.getRootPath())
                .path(path)
                .build().encode().toUri();
    }

    public InputStream Get(String path) {
        return RequestTools.GetFile(makeURL(path));
    }

    public ResponseEntity<String> Put(String path, InputStream stream) throws IOException {
        MultiValueMap<String, String> Hashs = new LinkedMultiValueMap<>();
        File file = tempProperties.TempFile();
        OutputStream out = new FileOutputStream(file);
        StreamUtils.copy(stream, out);
        //TODO:文件加密
        return put(makeURL(path), file, Hashs);
    }

    private ResponseEntity<String> put(URI url, File file, MultiValueMap<String, String> Hashs) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParams(Hashs);
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        restTemplate.setRequestFactory(requestFactory);
        HttpEntity<FileSystemResource> requestEntity = new HttpEntity<>(new FileSystemResource(file));
        return restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity, String.class);
    }
}
