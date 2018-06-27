package org.tinyspring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.factory.BeanDefinitionStoreException;
import org.tinyspring.beans.factory.support.BeanDefinitionRegistry;
import org.tinyspring.beans.factory.support.GenericBeanDefinition;
import org.tinyspring.core.io.Resource;
import org.tinyspring.utils.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public class XmlBeanDefinitionReader {

    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String SCOPE_ATTRIBUTE = "scope";

    BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinition(Resource resource) {
        InputStream is = null;

        ClassLoader cl = ClassUtils.getDefaultClassLoader();

        try {
            is = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(is);
            Element root = document.getRootElement();
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()){
                Element ele = iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                String scope = ele.attributeValue(SCOPE_ATTRIBUTE);
                if (scope != null){
                    bd.setScope(scope);
                }

                this.registry.registerBeanDefinition(id,bd);
            }
        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document failed");
        }finally {
            if (is != null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
