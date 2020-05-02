package cn.org.wxstc.services.entity;

import com.google.gson.Gson;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Table("Tests")
public class Test implements Serializable {
    public Test(UUID ID, String Name, String User, String JMXPath) {
        this.ID = ID;
        this.Name = Name;
        this.User = User;
        this.JMXPath = JMXPath;
        this.UploadedTime = LocalTime.now();
    }

    @PrimaryKey
    @Column(value = "ID")
    UUID ID;

    @Column(value = "NAME")
    String Name;

    @Column(value = "USER")
    String User;

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

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
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
