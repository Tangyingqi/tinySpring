package org.tinyspring.core.io.support;

import org.tinyspring.core.io.Resource;
import org.tinyspring.utils.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public class FileSystemResource implements Resource {

    private final File file;

    public FileSystemResource(String path) {
        this.file = new File(path);
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public String getDescription() {
        return "file [" + file.getAbsolutePath() + "]";
    }
}
