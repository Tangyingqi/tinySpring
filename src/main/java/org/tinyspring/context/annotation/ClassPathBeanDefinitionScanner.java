package org.tinyspring.context.annotation;

import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.factory.BeanDefinitionStoreException;
import org.tinyspring.beans.factory.support.BeanDefinitionRegistry;
import org.tinyspring.beans.factory.support.BeanNameGenerator;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.PackageResourceLoader;
import org.tinyspring.core.type.classreading.MetadataReader;
import org.tinyspring.core.type.classreading.SimpleMetadataReader;
import org.tinyspring.stereotype.Component;
import org.tinyspring.utils.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author tangyingqi
 * @date 2018/7/19
 */
public class ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packagesToScan) {

        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan, ",");

        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                beanDefinitions.add(candidate);
                registry.registerBeanDefinition(candidate.getID(), candidate);
            }
        }
        return beanDefinitions;
    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Resource[] resources = this.resourceLoader.getResources(basePackage);
        for (Resource resource : resources) {
            try {
                MetadataReader metadataReader = new SimpleMetadataReader(resource);

                if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                    ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
                    String beanName = beanNameGenerator.generateBeanName(sbd, registry);
                    sbd.setId(beanName);
                    candidates.add(sbd);
                }
            } catch (Throwable ex) {
                throw new BeanDefinitionStoreException(
                        "Failed to read candidate component class: " + resource, ex);
            }
        }

        return candidates;
    }
}

