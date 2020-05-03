package cn.org.wxstc.services.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@EnableConfigurationProperties({UserAuthProperties.class})
public class UserAuth {
    @Resource
    private UserAuthProperties properties;
}
