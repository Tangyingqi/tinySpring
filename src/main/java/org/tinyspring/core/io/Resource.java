package org.tinyspring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public interface Resource {
    InputStream getInputStream() throws IOException;

    String getDescription();
}
