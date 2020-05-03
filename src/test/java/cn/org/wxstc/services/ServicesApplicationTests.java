package cn.org.wxstc.services;

import cn.org.wxstc.services.repository.DatabaseRepository;
import cn.org.wxstc.services.repository.RedisTestRepository;
import cn.org.wxstc.services.repository.RedisUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class ServicesApplicationTests {

    @Test
    void contextLoads() {
    }

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


    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @Autowired
    RedisUserRepository redisUserRepository;

    @Test
    void redisUserTests() {
        Map<String, UUID[]> uuids = new HashMap<>();
        for (cn.org.wxstc.services.entity.Test ot : databaseRepository.findAll()) {
            UUID[] uids = uuids.get(ot.getUSER());
            if (uids == null) uuids.put(ot.getUSER(), new UUID[]{ot.getID()});
            else uuids.put(ot.getUSER(), concat(uids, new UUID[]{ot.getID()}));
        }
        for (Map.Entry<String, UUID[]> entry : uuids.entrySet()) {
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
