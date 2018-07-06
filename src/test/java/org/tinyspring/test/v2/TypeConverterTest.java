package org.tinyspring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.beans.SimpleTypeConverter;
import org.tinyspring.beans.TypeConverter;
import org.tinyspring.beans.TypeMismatchException;

import static org.junit.Assert.fail;

/**
 * @author tangyingqi
 * @date 2018/7/4
 */
public class TypeConverterTest {

    @Test
    public void testConvertStringToInt(){
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3",Integer.class);
        Assert.assertEquals(3,i.intValue());

        try {
            converter.convertIfNecessary("3.1",Integer.class);
        }catch (TypeMismatchException e){
            return;
        }

       fail();
    }

    @Test
    public void testConvertStringToBoolean(){
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("true", Boolean.class);
        Assert.assertTrue(b);

        try{
            converter.convertIfNecessary("xxxyyyzzz", Boolean.class);
        }catch(TypeMismatchException e){
            return;
        }
        fail();
    }
}
