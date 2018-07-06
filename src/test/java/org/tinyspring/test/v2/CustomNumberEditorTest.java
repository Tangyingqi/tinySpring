package org.tinyspring.test.v2;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;
import org.tinyspring.beans.propertyeditors.CustomNumberEditor;

/**
 * @author tangyingqi
 * @date 2018/7/4
 */
public class CustomNumberEditorTest {

    @Test
    public void testConvertString() {
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
        editor.setAsText("3");
        Object value = editor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3, ((Integer) editor.getValue()).intValue());

        editor.setAsText("");
        Assert.assertNull(editor.getValue());

        try {
            editor.setAsText("3.1");
        } catch (IllegalArgumentException e) {
            return;
        }

        fail();
    }
}
