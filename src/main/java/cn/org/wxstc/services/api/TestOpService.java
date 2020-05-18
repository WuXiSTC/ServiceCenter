package cn.org.wxstc.services.api;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.microrepos.FileRepository;
import cn.org.wxstc.services.microrepos.TestNetRepository;
import cn.org.wxstc.services.repository.TestRepository;
import com.alibaba.fastjson.JSONObject;
import com.datastax.driver.core.utils.UUIDs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class TestOpService {
    @Resource
    private TestRepository testRepository;
    @Resource
    private TestNetRepository testNetRepository;
    @Resource
    private FileRepository fileRepository;

    public JSONObject NewByUserAndName(String USER, String Name, InputStream jmx) throws IOException {
        UUID ID = UUIDs.timeBased();
        String jmxPath = ServiceTools.getPathByIDAndUserAndType(ID, USER, "jmx");
        ResponseEntity<String> result = fileRepository.Put(jmxPath, jmx);//先上传文件
        if (result.getStatusCode() != HttpStatus.OK) return null;//失败则返回
        testRepository.save(new Test(Name, USER, jmxPath));//然后记录数据库
        JSONObject j = new JSONObject();
        j.put("ok", true);
        j.put("message", "新建成功");
        return j;
    }

    public JSONObject StartByTest(Test test, long duration) throws IOException {
        if (testNetRepository.getState(test.getID()) != null) {
            JSONObject result = new JSONObject();
            result.put("ok", true);
            result.put("message", "已启动");
            return result;
        }
        if (ServiceTools.AlreadyRun(test)) {//如果已经运行过
            JSONObject result = new JSONObject();
            result.put("ok", false);
            result.put("message", "不能重复运行");
            return result;
        }
        InputStream file = fileRepository.Get(test.getJMXPath());//从文件存储库取文件
        if (file == null) throw new IOException("无法读取文件流" + test.getJMXPath());
        JSONObject result = testNetRepository.New(test.getID(), file);//放到测试网络
        if (!ServiceTools.IsOk(result)) throw new IOException(result.toString());//失败则返回
        result = testNetRepository.Start(test.getID(), duration);//成功则启动之
        return result;//返回
    }

    public JSONObject StopByTest(Test test) throws IOException {
        if (ServiceTools.AlreadyRun(test)) {
            JSONObject result = new JSONObject();
            result.put("ok", true);
            result.put("message", "Already Stopped");
            return result;//已运行则直接返回
        }
        testNetRepository.Stop(test.getID());
        InputStream file = testNetRepository.getResult(test.getID());//从测试网络取文件
        if (file != null) {
            String path = ServiceTools.getPathByIDAndUserAndType(test.getID(), test.getUSER(), "jtl");
            try {
                fileRepository.Put(path, file);//上传到文件存储库
            } catch (IOException e) {
                e.printStackTrace();
            }
            test.setJTLPath(path);
        }
        file = testNetRepository.getLog(test.getID());//从测试网络取文件
        if (file != null) {
            String path = ServiceTools.getPathByIDAndUserAndType(test.getID(), test.getUSER(), "log");
            try {
                fileRepository.Put(path, file);//上传到文件存储库
            } catch (IOException e) {
                e.printStackTrace();
            }
            test.setLOGPath(path);
        }
        testRepository.save(test);//记录
        return testNetRepository.Delete(test.getID());//从测试网络中删除
    }
}
