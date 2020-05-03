package cn.org.wxstc.services.repository;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

@org.springframework.stereotype.Repository("UserRepository")
public class UserRepository implements Repository<User, String> {
    @Autowired
    DatabaseRepository databaseRepository;
    @Autowired
    RedisUserRepository redisUserRepository;
    @Autowired
    RedisTestRepository redisTestRepository;

    /**
     * 读取数据库中按用户查询得到的Test ID缓存到Redis用户数据中
     *
     * @param ID 指定要缓存的用户
     */
    private User cacheByID(String ID) {
        SortedMap<UUID, String> TestSet = new TreeMap<>();
        for (Test t : databaseRepository.findAllByUSER(ID)) {
            TestSet.put(t.getID(), t.getName());
            redisTestRepository.save(t);
        }
        return redisUserRepository.save(new User(ID, TestSet));
    }

    public Optional<User> findByID(String ID) {
        Optional<User> user = redisUserRepository.findByID(ID);//查缓存
        if (!user.isPresent()) {//缓存没有就查数据库
            user = Optional.of(cacheByID(ID));
        }
        return user;
    }

    public <S extends User> S save(S user) {
        return redisUserRepository.save(user);
    }
}
