package org.tinyspring.core.io.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tinyspring.core.io.Resource;
import org.tinyspring.utils.Assert;
import org.tinyspring.utils.ClassUtils;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tangyingqi
 * @date 2018/7/17
 */
public class PackageResourceLoader {

    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);

    private final ClassLoader classLoader;

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ResourceLoader must not be null");
        this.classLoader = classLoader;
    }

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public Resource[] getResources(String basePackage) {

        Assert.notNull(basePackage, "basePackage must not be null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader cl = getClassLoader();
        URL url = cl.getResource(location);
        File rootDir = new File(url.getFile());

        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] result = new Resource[matchingFiles.size()];
        int i=0;
        for (File file : matchingFiles){
            result[i++] = new FileSystemResource(file);
        }
        return result;
    }

    private Set<File> retrieveMatchingFiles(File rootDir) {

        if (!rootDir.exists()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exist.");
            }
            return Collections.emptySet();
        }
        if (!rootDir.isDirectory()) {
            if (logger.isWarnEnabled()) {
                logger.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory");
            }
            return Collections.emptySet();
        }
        if (!rootDir.canRead()) {
            if (logger.isWarnEnabled()) {
                logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath() +
                        "] because the application is not allowed to read the directory");
                return Collections.emptySet();
            }
        }

        Set<File> result = new LinkedHashSet<File>(8);
        doRetrieveMatchingFiles(rootDir,result);
        return result;
    }

    private void doRetrieveMatchingFiles(File rootDir, Set<File> result) {
        File[] dirContents = rootDir.listFiles();
        if (dirContents == null){
            if (logger.isWarnEnabled()){
                logger.warn("Could not retrieve contents of directory ["+rootDir.getAbsolutePath()+"]");
            }
            return;
        }

        for (File content : dirContents){

            if (content.isDirectory()){
                if (!content.canRead()){
                    if (logger.isDebugEnabled()){
                        logger.debug("Skipping subDirectory ["+content.getAbsolutePath() +
                                "] because the application is not ");
                    }
                }else{
                    doRetrieveMatchingFiles(content,result);
                }
            }else{
                result.add(content);
            }
        }
    }
}
