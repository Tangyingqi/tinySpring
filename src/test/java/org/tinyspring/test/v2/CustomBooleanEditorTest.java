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
        Assert.assertTrue((Boolean) editor.getValue());
        editor.setAsText("false");
        Assert.assertFalse((Boolean) editor.getValue());

        editor.setAsText("on");
        Assert.assertTrue((Boolean) editor.getValue());
        editor.setAsText("off");
        Assert.assertFalse((Boolean) editor.getValue());

        editor.setAsText("yes");
        Assert.assertTrue((Boolean) editor.getValue());
        editor.setAsText("no");
        Assert.assertFalse((Boolean) editor.getValue());

        try{
            editor.setAsText("aaa");
        }catch (IllegalArgumentException e){
            return;
        }

        Assert.fail();

    }
}
