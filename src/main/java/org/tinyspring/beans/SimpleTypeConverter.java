package org.tinyspring.beans;

import org.tinyspring.beans.propertyeditors.CustomBooleanEditor;
import org.tinyspring.beans.propertyeditors.CustomNumberEditor;
import org.tinyspring.utils.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangyingqi
 * @date 2018/7/5
 */
public class SimpleTypeConverter implements TypeConverter {

    private Map<Class<?>,PropertyEditor> defaultEditors;

    public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {
        if (ClassUtils.isAssignableValue(requiredType,value)){
            return (T)value;
        }else{
            if (value instanceof String){
                PropertyEditor editor = findDefaultEditor(requiredType);
                try {
                    editor.setAsText((String)value);
                }catch (IllegalArgumentException e){
                    throw new TypeMismatchException(value,requiredType);
                }
                return (T)editor.getValue();
            }else{
                throw new RuntimeException("Todo : can't convert value for "+value +" class:"+requiredType);
            }
        }

    }

    private <T> PropertyEditor findDefaultEditor(Class<T> requiredType) {
        PropertyEditor editor = this.getDefaultEditor(requiredType);
        if (editor == null){
            throw new RuntimeException("Editor for "+ requiredType +" has not been implemented");
        }
        return editor;
    }

    private <T> PropertyEditor getDefaultEditor(Class<T> requiredType) {
        if (this.defaultEditors == null){
            createDefaultEditor();
        }
        return this.defaultEditors.get(requiredType);
    }

    private void createDefaultEditor() {

        this.defaultEditors = new HashMap<Class<?>, PropertyEditor>(64);

        this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));

        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
    }
}
