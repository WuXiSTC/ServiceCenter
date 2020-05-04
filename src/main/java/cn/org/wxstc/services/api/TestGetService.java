package cn.org.wxstc.services.api;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.microrepos.FileRepository;
import cn.org.wxstc.services.microrepos.TestNetRepository;
import cn.org.wxstc.services.repository.TestRepository;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestGetService {
    @Resource
    private TestRepository testRepository;
    @Resource
    private TestNetRepository testNetRepository;
    @Resource
    private FileRepository fileRepository;
    @Resource
    private TestOpService testOpService;

    private JSONObject getStateFromTestNetByTest(Test test) {
        JSONObject result = testNetRepository.getState(test.getID());//存在则获取
        if (result != null) {
            if (result.get("stateCode").equals(2) || result.get("stateCode").equals("2"))
                testOpService.StopByTest(test);//已停止则获取文件
            return result;//有则返回
        }
        result = new JSONObject();
        result.put("stateCode", 2);
        result.put("message", "Stopped");
        return result;//无则返回已停止
    }

    public JSONObject getStateByID(UUID ID) {
        Optional<Test> test = testRepository.findByID(ID);
        if (!test.isPresent()) return null;//不存在则返回不存在
        return getStateFromTestNetByTest(test.get());
    }

    public JSONObject getStateByIDAndUser(UUID ID, String USER) {
        Optional<Test> test = testRepository.findByIDAndUSER(ID, USER);
        if (!test.isPresent()) return null;//不存在则返回不存在
        return getStateFromTestNetByTest(test.get());
    }

    public File getFileByIDAndUserAndType(UUID ID, String USER, String Type) {
        String path = ServiceTools.getPathByIDAndUserAndType(ID, USER, Type);
        return fileRepository.Get(path);
    }
}
