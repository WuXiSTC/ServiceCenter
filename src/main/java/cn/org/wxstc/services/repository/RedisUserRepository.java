package cn.org.wxstc.services.repository;

import cn.org.wxstc.services.entity.Test;
import cn.org.wxstc.services.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RedisUserRepository extends CrudRepository<User, String> {
    Optional<User> findByID(String ID);
}
