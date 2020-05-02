package cn.org.wxstc.services;

import cn.org.wxstc.services.repository.DatabaseRepository;
import cn.org.wxstc.services.repository.RedisRepository;
import cn.org.wxstc.services.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

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
    }

    @Autowired
    RedisRepository redisRepository;

    @Test
    void redisTests(){

    }
}
