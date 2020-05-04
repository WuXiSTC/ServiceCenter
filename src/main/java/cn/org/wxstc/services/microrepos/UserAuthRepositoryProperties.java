package cn.org.wxstc.services.microrepos;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cn.org.wxstc.services.user-auth")
public class UserAuthRepositoryProperties {
    private String protocol = "http";
    private String host;
    private int port;
    private String registerPath = "/register";
    private String verifyPath = "/verify";
    private String updatePath = "/update";

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

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
