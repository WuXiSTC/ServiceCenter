package cn.org.wxstc.services.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cn.org.wxstc.services.userauth")
public class UserAuthProperties {
    private String host;
    private int port;
    private String registerPath = "/register";
    private String verifyPath = "/verify";
    private String updatePath = "/update";

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setRegisterPath(String registerPath) {
        this.registerPath = registerPath;
    }

    public String getRegisterPath() {
        return registerPath;
    }

    public void setVerifyPath(String verifyPath) {
        this.verifyPath = verifyPath;
    }

    public String getVerifyPath() {
        return verifyPath;
    }

    public void setUpdatePath(String updatePath) {
        this.updatePath = updatePath;
    }

    public String getUpdatePath() {
        return updatePath;
    }
}
