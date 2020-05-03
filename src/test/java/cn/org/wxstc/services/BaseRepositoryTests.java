package cn.org.wxstc.services;

import cn.org.wxstc.services.repository.DatabaseRepository;
import cn.org.wxstc.services.repository.RedisTestRepository;
import cn.org.wxstc.services.repository.RedisUserRepository;
import cn.org.wxstc.services.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class BaseRepositoryTests {

    @Autowired
    DatabaseRepository databaseRepository;

    @Test
    void databaseTests() {
        cn.org.wxstc.services.entity.Test test =
                new cn.org.wxstc.services.entity.Test("test_for_test", "test1", "/a/b/c");
        databaseRepository.insert(test);
        UUID uuid = test.getID();
        Optional<cn.org.wxstc.services.entity.Test> otest = databaseRepository.findByID(uuid);
        System.out.println(otest.toString());
        for (cn.org.wxstc.services.entity.Test ot : databaseRepository.findAll())
            System.out.println(ot.toString());
        for (cn.org.wxstc.services.entity.Test ot : databaseRepository.findAllByUSER("test1"))
            System.out.println(ot.toString());
    }

    @Autowired
    RedisUserRepository redisUserRepository;

    @Test
    void redisUserTests() {
        Map<String, SortedMap<UUID, String>> uuids = new HashMap<>();
        for (cn.org.wxstc.services.entity.Test ot : databaseRepository.findAll()) {
            SortedMap<UUID, String> uids = uuids.get(ot.getUSER());
            if (uids == null) uids = new TreeMap<>();
            uids.put(ot.getID(), ot.getName());
            uuids.put(ot.getUSER(), uids);
        }
        for (Map.Entry<String, SortedMap<UUID, String>> entry : uuids.entrySet()) {
            cn.org.wxstc.services.entity.User user =
                    new cn.org.wxstc.services.entity.User(entry.getKey(), entry.getValue());
            redisUserRepository.save(user);
        }
        for (cn.org.wxstc.services.entity.User u : redisUserRepository.findAll())
            System.out.println(u.toString());
    }

    @Autowired
    RedisTestRepository redisTestRepository;

    @Test
    void redisTestTests() {
        cn.org.wxstc.services.entity.Test test =
                new cn.org.wxstc.services.entity.Test("test-" + UUID.randomUUID(), "test1", "/a/b/c");
        redisTestRepository.save(test);
        UUID uuid = test.getID();
        Optional<cn.org.wxstc.services.entity.Test> otest = redisTestRepository.findByID(uuid);
        System.out.println(otest.toString());
        for (cn.org.wxstc.services.entity.Test ot : redisTestRepository.findAll())
            System.out.println(ot.toString());
    }
}
