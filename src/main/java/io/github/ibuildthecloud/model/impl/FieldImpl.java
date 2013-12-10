package io.github.ibuildthecloud.model.impl;

import io.github.ibuildthecloud.gdapi.model.Field;
import io.github.ibuildthecloud.gdapi.model.FieldType;
import io.github.ibuildthecloud.gdapi.model.FieldType.TypeAndName;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

public class FieldImpl implements Field {

    String name, type, validChars, invalidChars;
    Integer displayIndex;
    boolean create, update, includeInList = true, nullable, unique, required;
    FieldType typeEnum;
    List<FieldType> subTypeEnums;
    List<String> subTypes;
    Long min, max, minLength, maxLength;
    String defaultValue;
    List<String> options;
    Method readMethod;
//    Class<?> typeClass, subTypeClass;

    @Override
    public Object getValue(Object object) {
        if ( readMethod == null || object == null )
            return null;

        try {
            return readMethod.invoke(object);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    @XmlTransient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Integer getDisplayIndex() {
        return displayIndex;
    }

    public void setDisplayIndex(Integer displayIndex) {
        this.displayIndex = displayIndex;
    }

    @Override
    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    @Override
    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    @Override
    public boolean isIncludeInList() {
        return includeInList;
    }

    public void setIncludeInList(boolean includeInList) {
        this.includeInList = includeInList;
    }

    @Override
    public String getType() {
        if ( type == null && typeEnum != null ) {
            type = FieldType.toString(type, typeEnum, subTypes);
        }
        return type;
    }

    public void setType(String typeName) {
        if ( typeName == null ) {
            type = null;
        } else {
            List<TypeAndName> parts = FieldType.parse(typeName);
            if ( parts.size() == 0 ) {
                throw new IllegalArgumentException("Failed to parse type [" + typeName + "]");
            }

            TypeAndName part = parts.get(0);
            this.type = part.getName();
            this.typeEnum = part.getType();

            parts.remove(0);
            setSubTypesList(parts);
        }
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public FieldType getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(FieldType typeEnum) {
        this.typeEnum = typeEnum;
        this.type = null;
        this.subTypeEnums = Collections.emptyList();
        this.subTypes = Collections.emptyList();
    }

    @Override
    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    @Override
    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    @Override
    public Long getMinLength() {
        return minLength;
    }

    public void setMinLength(Long minLength) {
        this.minLength = minLength;
    }

    @Override
    public Long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String getDefault() {
        return defaultValue;
    }

    public void setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @XmlTransient
    public Method getReadMethod() {
        return readMethod;
    }

    public void setReadMethod(Method readMethod) {
        this.readMethod = readMethod;
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String getValidChars() {
        return validChars;
    }

    public void setValidChars(String validChars) {
        this.validChars = validChars;
    }

    @Override
    public String getInvalidChars() {
        return invalidChars;
    }

    public void setInvalidChars(String invalidChars) {
        this.invalidChars = invalidChars;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setSubTypesList(List<TypeAndName> subTypes) {
        this.subTypes = new ArrayList<String>(subTypes.size());
        this.subTypeEnums = new ArrayList<FieldType>(subTypes.size());

        for ( TypeAndName typeAndName : subTypes ) {
            this.subTypes.add(typeAndName.getName());
            this.subTypeEnums.add(typeAndName.getType());
        }

        this.type = FieldType.toString(type, typeEnum, this.subTypes);
    }

    @Override
    public List<FieldType> getSubTypeEnums() {
        return subTypeEnums;
    }

    @Override
    public List<String> getSubTypes() {
        return subTypes;
    }


//    @Override
//    public Class<?> getTypeClass() {
//        return typeClass;
//    }
//
//    public void setTypeClass(Class<?> typeClass) {
//        this.typeClass = typeClass;
//    }
//
//    @Override
//    public Class<?> getSubTypeClass() {
//        return subTypeClass;
//    }
//
//    public void setSubTypeClass(Class<?> subTypeClass) {
//        this.subTypeClass = subTypeClass;
//    }


}