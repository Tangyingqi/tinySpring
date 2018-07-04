package org.tinyspring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.beans.propertyeditors.CustomBooleanEditor;

/**
 * @author tangyingqi
 * @date 2018/7/4
 */
public class CustomBooleanEditorTest {

    @Test
    public void testCovertStringToBoolean(){
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertEquals(true,((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("false");
        Assert.assertEquals(false,((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("on");
        Assert.assertEquals(true,((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("off");
        Assert.assertEquals(false,((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("yes");
        Assert.assertEquals(true,((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("no");
        Assert.assertEquals(false,((Boolean)editor.getValue()).booleanValue());

        try{
            editor.setAsText("aaa");
        }catch (IllegalArgumentException e){
            return;
        }

        Assert.fail();

    }
}
