package cn.org.wxstc.services;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.entity.User;
import cn.org.wxstc.services.repository.TestRepository;
import cn.org.wxstc.services.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
