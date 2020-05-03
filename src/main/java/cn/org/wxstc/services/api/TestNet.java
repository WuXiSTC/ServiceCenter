package cn.org.wxstc.services.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@EnableConfigurationProperties({TestNetProperties.class})
public class TestNet {
    @Resource
    private TestNetProperties properties;
}
