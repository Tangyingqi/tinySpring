package org.tinyspring.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.ConstructorArgument;
import org.tinyspring.beans.SimpleTypeConverter;
import org.tinyspring.beans.factory.BeanCreationException;
import org.tinyspring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/7/10
 */
public class ConstructorResolver {

    private final Log logger = LogFactory.getLog(getClass());

    private final AbstractBeanFactory beanFactory;

    public ConstructorResolver(AbstractBeanFactory factory) {
        this.beanFactory = factory;
    }

    public Object autowireConstructor(final BeanDefinition bd) {

        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;

        Class<?> beanClass = null;

        try {
            beanClass = beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(bd.getID(),"Instantiation bean failed,can't resolve class",e);
        }

        Constructor<?>[] candidates = beanClass.getConstructors();

        BeanDefinitionValueResolver valueResolver =
                new BeanDefinitionValueResolver(this.beanFactory);

        ConstructorArgument cargs = bd.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        for (int i=0;i<candidates.length;i++){

            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            if (parameterTypes.length != cargs.getArgumentCount()){
                continue;
            }
            argsToUse = new Object[parameterTypes.length];

            boolean result = valueMatchTypes(parameterTypes,
                    cargs.getArgumentValues(),
                    argsToUse,
                    valueResolver,
                    typeConverter);

            if (result){
                constructorToUse = candidates[i];
                break;
            }
        }

        if (constructorToUse == null){
            throw new BeanCreationException(bd.getID(),"can't find a appropriate constructor");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException( bd.getID(), "can't find a create instance using "+constructorToUse);
        }
    }

    private boolean valueMatchTypes(Class<?>[] parameterTypes,
                                    List<ConstructorArgument.ValueHolder> valueHolders,
                                    Object[] argsToUse,
                                    BeanDefinitionValueResolver valueResolver,
                                    SimpleTypeConverter typeConverter) {

        for (int i=0;i<parameterTypes.length;i++){
            ConstructorArgument.ValueHolder valueHolder
                    = valueHolders.get(i);
            //获取参数的值，可能是 RuntimeReference 或 TypedStringValue
            Object originValue = valueHolder.getValue();

            try {
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originValue);
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue,parameterTypes[i]);
                argsToUse[i] = convertedValue;
            }catch (Exception e){
                logger.error(e);
                return false;
            }
        }
        return true;

    }
}

