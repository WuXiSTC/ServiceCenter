package cn.org.wxstc.services.repository;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;

import java.util.*;

@org.springframework.stereotype.Repository("TestRepository")
public class TestRepository implements Repository<Test, String> {
    @Autowired
    DatabaseRepository databaseRepository;
    @Autowired
    RedisTestRepository redisTestRepository;
    @Autowired
    UserRepository userRepository;

    public <S extends Test> S save(S test) {
        test = databaseRepository.insert(test);//数据库中插入
        test = redisTestRepository.save(test);//Test缓存中插入
        Optional<User> user = userRepository.findByID(test.getUSER());//获取用户
        if (user.isPresent()) user.get().putTest(test);//若存在则添加测试
        else {
            User u = new User(test.getUSER(), new TreeMap<>());
            u.putTest(test);
            user = Optional.of(u);//不存在则新建用户
        }
        userRepository.save(user.get());//更新用户信息缓存
        return test;
    }

    private Optional<Test> findByID(UUID ID) {
        Optional<Test> test = redisTestRepository.findByID(ID);//先查Test缓存
        if (test.isPresent()) return test;//存在则返回
        test = databaseRepository.findByID(ID);//否则就从数据库查询该测试
        test.ifPresent(t -> redisTestRepository.save(t));//并缓存之
        return test;
    }

    public Optional<Test> findByIDAndUSER(UUID ID, String USER) {
        Optional<Test> test = findByID(ID);
        if (test.isPresent()) {//Test缓存中有
            if (test.get().getUSER().equals(USER)) return test;//且用户符合即返回
            return Optional.empty();//用户不符合返回空
        }
        return test;
    }
}
