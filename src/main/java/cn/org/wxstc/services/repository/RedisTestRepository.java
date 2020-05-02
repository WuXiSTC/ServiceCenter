package cn.org.wxstc.services.repository;

import cn.org.wxstc.services.entity.Test;
import org.springframework.data.repository.CrudRepository;

public interface RedisTestRepository extends CrudRepository<Test, String> {
}
