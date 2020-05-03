package cn.org.wxstc.services.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cn.org.wxstc.services.testnet")
public class TestNetProperties {
    private String host;
    private int port;
    private String taskOperationPath = "/Task";
    private String GraphQueryPath = "/GraphQuery";
    private String TasksQueryPath = "getTasks";

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

    public void setTaskOperationPath(String taskOperationPath) {
        this.taskOperationPath = taskOperationPath;
    }

    public String getTaskOperationPath() {
        return taskOperationPath;
    }

    public void setGraphQueryPath(String graphQueryPath) {
        GraphQueryPath = graphQueryPath;
    }

    public String getGraphQueryPath() {
        return GraphQueryPath;
    }

    public void setTasksQueryPath(String tasksQueryPath) {
        TasksQueryPath = tasksQueryPath;
    }

    public String getTasksQueryPath() {
        return TasksQueryPath;
    }
}
