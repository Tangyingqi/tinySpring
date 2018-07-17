package org.tinyspring.core.type.classreading;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;
import org.tinyspring.core.type.ClassMetadata;
import org.tinyspring.utils.ClassUtils;

/**
 * @author tangyingqi
 * @date 2018/7/17
 */
public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {

    private String className;

    private boolean isInterface;

    private boolean isAbstract;

    private boolean isFinal;

    private String superClassName;

    private String[] interfaces;

    public ClassMetadataReadingVisitor() {
        super(SpringAsmInfo.ASM_VERSION);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
        this.className = ClassUtils.convertResourcePathToClassName(name);
        this.isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
        this.isAbstract = (access & Opcodes.ACC_ABSTRACT) != 0;
        this.isFinal = (access & Opcodes.ACC_FINAL) != 0;
        if (superName != null){
            this.superClassName = ClassUtils.convertResourcePathToClassName(superName);
        }
        this.interfaces = new String[interfaces.length];
        for (int i=0;i<interfaces.length;i++){
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }

    public String getClassName() {
        return this.className;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public boolean isAbstract() {
        return this.isAbstract;
    }

    public boolean isFinal() {
        return this.isFinal;
    }

    public boolean hasSuperClass() {
        return this.superClassName != null;
    }

    public String getSuperClassName() {
        return this.superClassName;
    }

    public String[] getInterfaceNames() {
        return this.interfaces;
    }
}
