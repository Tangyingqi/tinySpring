package org.tinyspring.beans.propertyeditors;

import org.tinyspring.utils.NumberUtils;
import org.tinyspring.utils.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

/**
 * @author tangyingqi
 * @date 2018/7/4
 */
public class CustomNumberEditor extends PropertyEditorSupport {

    private final Class<? extends Number> numberClass;

    private final NumberFormat numberFormat;

    private final boolean allowEmpty;

    public CustomNumberEditor(Class<? extends Number> numberClass,boolean allowEmpty) throws IllegalArgumentException{
        this(numberClass,allowEmpty,null);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass,boolean allowEmpty,NumberFormat numberFormat) throws IllegalArgumentException{

        if (numberClass == null || !Number.class.isAssignableFrom(numberClass)){
            throw new IllegalArgumentException("Property class must be a subclass of Number");
        }

        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)){
            setValue(null);
        }else if(this.numberFormat != null){
            setValue(NumberUtils.parseNumber(text,numberClass,numberFormat));
        }else{
            setValue(NumberUtils.parseNumber(text,numberClass));
        }
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Number){
            super.setValue(NumberUtils.convertNumberToTargetClass((Number)value,this.numberClass));
        }else{
            super.setValue(value);
        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();

        if (value == null){
            return "";
        }
        if (this.numberFormat != null){
            return numberFormat.format(value);
        }else{
            return value.toString();
        }

    }
}
