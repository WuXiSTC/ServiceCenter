package cn.org.wxstc.services.entity;

import com.google.gson.Gson;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Table(value = "Tests")
@CachePut(value = "Tests")
public class Test implements Serializable {
    public Test(String Name, String USER, String JMXPath) {
        this.ID = UUID.randomUUID();
        this.Name = Name;
        this.USER = USER;
        this.JMXPath = JMXPath;
        this.UploadedTime = LocalTime.now();
    }

    @PrimaryKey
    @Id
    @Column(value = "ID")
    UUID ID;

    @Column(value = "NAME")
    String Name;

    @Column(value = "USER")
    String USER;

    @Column(value = "PLAN")
    String JMXPath;

    @Column(value = "LOG")
    String LOGPath;

    @Column(value = "RESULT")
    String JTLPath;

    @Column(value = "TIME")
    LocalTime UploadedTime;

    public UUID getID() {
        return ID;
    }

    public void setID(UUID id) {
        ID = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public String getJMXPath() {
        return JMXPath;
    }

    public void setJMXPath(String path) {
        JMXPath = path;
    }

    public String getLOGPath() {
        return LOGPath;
    }

    public void setLOGPath(String path) {
        LOGPath = path;
    }

    public String getJTLPath() {
        return JTLPath;
    }

    public void setJTLPath(String path) {
        JTLPath = path;
    }

    public LocalTime getUploadedTime() {
        return UploadedTime;
    }

    public void setUploadedTime(LocalTime time) {
        UploadedTime = time;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
