package cn.org.wxstc.services.microrepos;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.io.IOException;

@ConfigurationProperties(prefix = "cn.org.wxstc.services.tmp")
public class TempProperties {
    private String dir = "./";
    private String prefix = "cn.org.wxstc.services.";
    private String suffix = ".tmp";

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return dir;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public File TempFile() throws IOException {
        return File.createTempFile(prefix, suffix, new File(dir));
    }
}
