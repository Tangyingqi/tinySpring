package org.tinyspring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.PropertyValue;
import org.tinyspring.beans.factory.BeanDefinitionStoreException;
import org.tinyspring.beans.factory.config.RuntimeBeanReference;
import org.tinyspring.beans.factory.config.TypedStringValue;
import org.tinyspring.beans.factory.support.BeanDefinitionRegistry;
import org.tinyspring.beans.factory.support.GenericBeanDefinition;
import org.tinyspring.core.io.Resource;
import org.tinyspring.utils.ClassUtils;
import org.tinyspring.utils.StringUtils;

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

    public static final String PROPERTY_ELEMENT = "property";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String VAULE_ATTRIBUTE = "value";

    public static final String NAME_ATTRIBUTE = "name";

    BeanDefinitionRegistry registry;

    protected final Log logger = LogFactory.getLog(getClass());

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinition(Resource resource) {
        InputStream is = null;

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
                parsePropertyElement(ele,bd);
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

    public void parsePropertyElement(Element beanElement,BeanDefinition bd){

        Iterator iterator = beanElement.elementIterator(PROPERTY_ELEMENT);
        while (iterator.hasNext()){
            Element propElement = (Element) iterator.next();
            String propName = propElement.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propName)){
                logger.fatal("Tag 'property' must have a 'name' attribute");
            }

            Object val = parsePropertyValue(propElement,propName);
            PropertyValue pv = new PropertyValue(propName,val);

            bd.getPropertyValues().add(pv);
        }
    }

    private Object parsePropertyValue(Element ele, String propName) {

        String elementName = propName != null ?
                "<property> element for property'"+propName+"'" :
                "<constructor-arg> element";
        boolean hasRefAttribute = ele.attribute(REF_ATTRIBUTE) != null;
        boolean hasValueAttribute = ele.attribute(VAULE_ATTRIBUTE) != null;

        if (hasRefAttribute){
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName)){
                logger.error(elementName + " contains empty 'ref' attribute");
            }
            return new RuntimeBeanReference(refName);
        }else if(hasValueAttribute){
            return  new TypedStringValue(ele.attributeValue(VAULE_ATTRIBUTE));
        }else{
            throw new RuntimeException(elementName + "must specify a ref or value");
        }
    }
}
