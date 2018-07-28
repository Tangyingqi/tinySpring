package org.tinyspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.beans.factory.annotation.AutowireAnnotationProcessor;
import org.tinyspring.beans.factory.annotation.AutowiredFieldElement;
import org.tinyspring.beans.factory.annotation.InjectionElement;
import org.tinyspring.beans.factory.annotation.InjectionMetadata;
import org.tinyspring.beans.factory.config.DependencyDescriptor;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.dao.v4.AccountDao;
import org.tinyspring.dao.v4.ItemDao;
import org.tinyspring.service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/7/28
 */
public class AutowireAnnotationProcessorTest {

    AccountDao accountDao = new AccountDao();
    ItemDao itemDao = new ItemDao();
    DefaultBeanFactory beanFactory = new DefaultBeanFactory(){
        @Override
        public Object resolveDependency(DependencyDescriptor descriptor) {
            if (descriptor.getDependencyType().equals(AccountDao.class)){
                return accountDao;
            }
            if (descriptor.getDependencyType().equals(ItemDao.class)){
                return itemDao;
            }
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };

    @Test
    public void testGetInjectionMetadata(){

        AutowireAnnotationProcessor processor = new AutowireAnnotationProcessor();
        processor.setBeanFactory(beanFactory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2,elements.size());

        assertFieldExists(elements,"accountDao");
        assertFieldExists(elements,"itemDao");

        PetStoreService petStoreService = new PetStoreService();

        injectionMetadata.inject(petStoreService);

        Assert.assertTrue(petStoreService.getAccountDao() instanceof  AccountDao);
        Assert.assertTrue(petStoreService.getItemDao() instanceof ItemDao);

        PetStoreService petStore = new PetStoreService();

        injectionMetadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);

        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
    }

    private void assertFieldExists(List<InjectionElement> elements,String fieldName){
        for (InjectionElement ele : elements){
            AutowiredFieldElement fieldElement = (AutowiredFieldElement) ele;
            Field f = fieldElement.getField();
            if (f.getName().equals(fieldName)){
                return;
            }
        }

        Assert.fail(fieldName + "does not exist!");

    }
}
