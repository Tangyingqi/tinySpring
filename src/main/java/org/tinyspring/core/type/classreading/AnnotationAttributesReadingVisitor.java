package org.tinyspring.core.type.classreading;

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;
import org.tinyspring.core.annotation.AnnotationAttributes;

import java.util.Map;

/**
 * @author tangyingqi
 * @date 2018/7/18
 */
final class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

    private final String annotationType;

    private final Map<String,AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType,Map<String,AnnotationAttributes> attributesMap){
        super(SpringAsmInfo.ASM_VERSION);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    @Override
    public void visitEnd() {
        this.attributesMap.put(this.annotationType,this.attributes);
    }

    @Override
    public void visit(String attributeName, Object attributeValue) {
       this.attributes.put(attributeName,attributeValue);
    }
}
