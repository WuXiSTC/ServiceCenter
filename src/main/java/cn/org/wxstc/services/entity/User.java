package cn.org.wxstc.services.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash("user")
public class User {
    @Id
    String ID;
    UUID[] Tests;

    public User(String ID, UUID[] Tests) {
        this.ID = ID;
        this.Tests = Tests;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public UUID[] getTests() {
        return Tests;
    }

    public void setTests(UUID[] tests) {
        Tests = tests;
    }
}
