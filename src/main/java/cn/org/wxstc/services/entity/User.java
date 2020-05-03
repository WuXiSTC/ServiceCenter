package cn.org.wxstc.services.entity;

import com.google.gson.Gson;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.SortedMap;
import java.util.UUID;

@RedisHash("user")
public class User {
    @Id
    String ID;
    SortedMap<UUID, String> Tests;

    public User(String ID, SortedMap<UUID, String> Tests) {
        this.ID = ID;
        this.Tests = Tests;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public SortedMap<UUID, String> getTests() {
        return Tests;
    }

    public void setTests(SortedMap<UUID, String> tests) {
        Tests = tests;
    }

    public void putTest(Test test) {
        Tests.put(test.getID(), test.getName());
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
