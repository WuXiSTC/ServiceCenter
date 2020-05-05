package cn.org.wxstc.services.api;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.entity.User;
import cn.org.wxstc.services.repository.TestRepository;
import cn.org.wxstc.services.repository.UserRepository;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Resource
    private TestRepository testRepository;
    @Resource
    private TestOpService testOpService;
    @Resource
    private TestGetService testGetService;
    @Resource
    private UserRepository userRepository;

    public JSONArray findAllByUser(String USER) {
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

    public Test findByIDAndUser(UUID ID, String USER) {
        Optional<Test> test = testRepository.findByIDAndUSER(ID, USER);
        return test.orElse(null);
    }

    public JSONObject NewByUserAndName(String USER, String Name, InputStream jmx) throws IOException {
        return testOpService.NewByUserAndName(USER, Name, jmx);
    }

    public JSONObject StartByIDAndUser(UUID ID, String USER, long duration) throws IOException {
        Optional<Test> test = testRepository.findByIDAndUSER(ID, USER);
        if (!test.isPresent()) return null;//不存在则返回不存在
        return testOpService.StartByTest(test.get(), duration);
    }

    public JSONObject StopByIDAndUser(UUID ID, String USER) throws IOException {
        Optional<Test> test = testRepository.findByIDAndUSER(ID, USER);
        if (!test.isPresent()) return null;//不存在则返回不存在
        return testOpService.StopByTest(test.get());
    }

    public JSONObject getStateByIDAndUser(UUID ID, String USER) throws IOException {
        Optional<Test> test = testRepository.findByIDAndUSER(ID, USER);
        if (!test.isPresent()) return null;//不存在则返回不存在
        return testGetService.getStateByTest(test.get());
    }

    public InputStream getFileByIDAndUserAndType(UUID ID, String USER, String Type) {
        Optional<Test> test = testRepository.findByIDAndUSER(ID, USER);
        if (!test.isPresent()) return null;//不存在则返回不存在
        return testGetService.getFileByTestAndType(test.get(), Type);
    }
}
