package cn.org.wxstc.services.api;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.microrepos.FileRepository;
import cn.org.wxstc.services.microrepos.TestNetRepository;
import cn.org.wxstc.services.repository.TestRepository;
import com.alibaba.fastjson.JSONObject;
import com.datastax.driver.core.utils.UUIDs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;

@Service
public class TestOpService {
    @Resource
    private TestRepository testRepository;
    @Resource
    private TestNetRepository testNetRepository;
    @Resource
    private FileRepository fileRepository;

    public JSONObject NewByUserAndName(String USER, String Name, File jmx) {
        UUID ID = UUIDs.timeBased();
        String jmxPath = ServiceTools.getPathByIDAndUserAndType(ID, USER, "jmx");
        JSONObject result = fileRepository.Put(jmxPath, jmx);//先上传文件
        if (ServiceTools.IsOk(result)) return result;//失败则返回
        testRepository.save(new Test(Name, USER, jmxPath));//然后记录数据库
        result = new JSONObject();
        result.put("ok", true);
        result.put("message", "新建成功");
        return result;
    }

    public JSONObject StartByTest(Test test) {
        if (testNetRepository.getState(test.getID()) != null) {
            JSONObject result = new JSONObject();
            result.put("ok", true);
            result.put("message", "已启动");
            return result;
        }
        File file = fileRepository.Get(test.getJMXPath());//从文件存储库取文件
        if (file == null) {
            JSONObject result = new JSONObject();
            result.put("ok", false);
            result.put("message", "测试计划文件不存在");
            return result;//失败则返回
        }
        if (ServiceTools.AlreadyRun(test)) {//如果已经运行过
            test = new Test(test.getName(), test.getUSER(), test.getJMXPath());//就复制一个
            testRepository.save(test);//并保存
        }
        JSONObject result = testNetRepository.New(test.getID(), file);//放到测试网络
        if (ServiceTools.IsOk(result)) return result;//失败则返回
        result = testNetRepository.Start(test.getID());//成功则启动之
        return result;//返回
    }

    public JSONObject StopByTest(Test test) {
        if (ServiceTools.AlreadyRun(test)) {
            JSONObject result = new JSONObject();
            result.put("ok", true);
            result.put("message", "Already Stopped");
            return result;//已运行则直接返回
        }
        testNetRepository.Stop(test.getID());
        File file = testNetRepository.getResult(test.getID());//从测试网络取文件
        if (file != null) {
            String path = ServiceTools.getPathByIDAndUserAndType(test.getID(), test.getUSER(), "jtl");
            fileRepository.Put(path, file);//上传到文件存储库
            test.setJTLPath(path);
        }
        file = testNetRepository.getLog(test.getID());//从测试网络取文件
        if (file != null) {
            String path = ServiceTools.getPathByIDAndUserAndType(test.getID(), test.getUSER(), "log");
            fileRepository.Put(path, file);//上传到文件存储库
            test.setLOGPath(path);
        }
        testRepository.save(test);//记录
        return testNetRepository.Delete(test.getID());//从测试网络中删除
    }
}
