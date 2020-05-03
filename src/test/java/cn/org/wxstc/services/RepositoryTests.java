package cn.org.wxstc.services;

import cn.org.wxstc.services.repository.TestRepository;
import cn.org.wxstc.services.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class RepositoryTests {

    @Autowired
    TestRepository testRepository;

    @Test
    void TestTests() {

    }

    @Autowired
    UserRepository userRepository;

    @Test
    void UserTests() {

    }
}
