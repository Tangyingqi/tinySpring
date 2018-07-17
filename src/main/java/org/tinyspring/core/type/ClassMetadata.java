package org.tinyspring.core.type;

/**
 * @author tangyingqi
 * @date 2018/7/17
 */
public interface ClassMetadata {

    String getClassName();

    boolean isInterface();

    boolean isAbstract();

    boolean isFinal();

    boolean hasSuperClass();

    String getSuperClassName();

    String[] getInterfaceNames();
}
