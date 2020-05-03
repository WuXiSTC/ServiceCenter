package cn.org.wxstc.services.api;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.entity.User;
import cn.org.wxstc.services.repository.TestRepository;
import cn.org.wxstc.services.repository.UserRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@EnableConfigurationProperties({TestNetProperties.class})
public class TestNet {
    @Resource
    private TestNetProperties properties;
    @Resource
    private UserRepository userRepository;
    @Resource
    private TestRepository testRepository;

    public JSON FindAllByUser(String USER) {
        Optional<User> user = userRepository.findByID(USER);
        JSONArray tasks = new JSONArray();
        if (user.isPresent())
            for (Map.Entry<UUID, String> e : user.get().getTests().entrySet()) {
                JSONArray task = new JSONArray();
                task.add(e.getKey());
                task.add(e.getValue());
                tasks.add(task);
            }
        return tasks;
    }

    public Test FindByIDAndUser(UUID ID, String USER) {
        Optional<Test> test = testRepository.findByIDAndUSER(ID, USER);
        return test.orElse(null);
    }
}
