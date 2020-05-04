package cn.org.wxstc.services.api;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.microrepos.TestNetRepository;
import cn.org.wxstc.services.repository.TestRepository;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    @Resource
    private TestRepository testRepository;
    @Resource
    private TestOpService testOpService;
    @Resource
    private TestGetService testGetService;
    @Resource
    private TestNetRepository testNetRepository;

    public Test findByID(UUID ID) {
        Optional<Test> test = testRepository.findByID(ID);
        return test.orElse(null);
    }

    public JSONObject StartByID(UUID ID) {
        Optional<Test> test = testRepository.findByID(ID);
        if (!test.isPresent()) return null;//不存在则返回不存在
        return testOpService.StartByTest(test.get());
    }

    public JSONObject StopByID(UUID ID) {
        Optional<Test> test = testRepository.findByID(ID);
        if (!test.isPresent()) return null;//不存在则返回不存在
        return testOpService.StopByTest(test.get());
    }

    public JSONObject getStateByID(UUID ID) {
        return testGetService.getStateByID(ID);
    }

    public File getFileByIDAndType(UUID ID, String Type) {
        Optional<Test> test = testRepository.findByID(ID);
        if (!test.isPresent()) return null;//不存在则返回不存在
        return testGetService.getFileByIDAndUserAndType(ID, test.get().getUSER(), Type);
    }

    public JSONObject getGraph() {
        return testNetRepository.getGraph();
    }

    public JSONObject getTasks() {
        return testNetRepository.getTasks();
    }
}
