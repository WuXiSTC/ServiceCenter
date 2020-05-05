package cn.org.wxstc.services.api;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.microrepos.FileRepository;
import cn.org.wxstc.services.microrepos.TestNetRepository;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@Service
public class TestGetService {
    @Resource
    private TestNetRepository testNetRepository;
    @Resource
    private FileRepository fileRepository;
    @Resource
    private TestOpService testOpService;

    public JSONObject getStateByTest(Test test) throws IOException {
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

    public InputStream getFileByTestAndType(Test test, String Type) {
        String path;
        switch (Type) {
            case "jmx":
                path = test.getJMXPath();
                break;
            case "jtl":
                path = test.getJTLPath();
                break;
            case "log":
                path = test.getLOGPath();
                break;
            default:
                path = ServiceTools.getPathByIDAndUserAndType(test.getID(), test.getUSER(), Type);
        }
        if (path == null) return null;
        return fileRepository.Get(path);
    }
}
