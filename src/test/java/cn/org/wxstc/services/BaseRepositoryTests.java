package cn.org.wxstc.services;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.entity.User;
import cn.org.wxstc.services.repository.DatabaseRepository;
import cn.org.wxstc.services.repository.RedisTestRepository;
import cn.org.wxstc.services.repository.RedisUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class BaseRepositoryTests {

    @Autowired
    DatabaseRepository databaseRepository;

    @org.junit.jupiter.api.Test
    void databaseTests() {
        Test test = new Test("test_for_test", "test1", "/a/b/c");
        databaseRepository.insert(test);
        UUID uuid = test.getID();
        Optional<Test> otest = databaseRepository.findByID(uuid);
        System.out.println(otest.toString());
        for (Test ot : databaseRepository.findAll())
            System.out.println(ot.toString());
        for (Test ot : databaseRepository.findAllByUSER("test1"))
            System.out.println(ot.toString());
    }

    @Autowired
    RedisUserRepository redisUserRepository;

    @org.junit.jupiter.api.Test
    void redisUserTests() {
        Map<String, SortedMap<UUID, String>> uuids = new HashMap<>();
        for (Test ot : databaseRepository.findAll()) {
            SortedMap<UUID, String> uids = uuids.get(ot.getUSER());
            if (uids == null) uids = new TreeMap<>();
            uids.put(ot.getID(), ot.getName());
            uuids.put(ot.getUSER(), uids);
        }
        for (Map.Entry<String, SortedMap<UUID, String>> entry : uuids.entrySet()) {
            User user = new User(entry.getKey(), entry.getValue());
            redisUserRepository.save(user);
        }
        for (User u : redisUserRepository.findAll())
            System.out.println(u.toString());
    }

    @Autowired
    RedisTestRepository redisTestRepository;

    @org.junit.jupiter.api.Test
    void redisTestTests() {
        Test test =
                new Test("test-" + UUID.randomUUID(), "test1", "/a/b/c");
        redisTestRepository.save(test);
        UUID uuid = test.getID();
        Optional<Test> otest = redisTestRepository.findByID(uuid);
        System.out.println(otest.toString());
        for (Test ot : redisTestRepository.findAll())
            System.out.println(ot.toString());
    }
}
