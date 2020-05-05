package cn.org.wxstc.services;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.entity.User;
import cn.org.wxstc.services.microrepos.FileRepository;
import cn.org.wxstc.services.microrepos.TempProperties;
import cn.org.wxstc.services.repository.TestRepository;
import cn.org.wxstc.services.repository.UserRepository;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

@SpringBootTest
class RepositoryTests {

    @Autowired
    TestRepository testRepository;

    @org.junit.jupiter.api.Test
    void TestTests() {
        Test test = new Test("test" + UUID.randomUUID(), "test2", "/1/1");
        testRepository.save(test);
        System.out.println(testRepository.findByIDAndUSER(test.getID(), test.getUSER()));
    }

    @Autowired
    UserRepository userRepository;

    @org.junit.jupiter.api.Test
    void UserTests() {
        System.out.println(userRepository.findByID("test2"));
        userRepository.save(new User("test100", new TreeMap<>()));
        System.out.println(userRepository.findByID("test100"));
    }

    @Autowired
    FileRepository fileRepository;
    @Resource
    TempProperties tempProperties;

    @org.junit.jupiter.api.Test
    void FileTests() throws IOException {
        File f = tempProperties.TempFile();
        FileWriter fw = new FileWriter(f);
        fw.write("0123456789qwerty uint[]\\afghans;'zabrina,./");
        fw.close();
        String path = "/test/" + UUID.randomUUID();
        System.out.println(fileRepository.Put(path, new FileInputStream(f)));
        try {
            InputStream is = fileRepository.Get(path);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            System.out.println("文件内容:\n" + new String(bytes));
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
