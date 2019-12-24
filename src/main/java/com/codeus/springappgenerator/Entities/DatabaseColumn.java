/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeus.springappgenerator.Entities;

import com.codeus.springappgenerator.Util.Util;
import com.codeus.springappgenerator.Entities.*;
import com.codeus.springappgenerator.Entities.Case;
/**
 *
 * @author PC
 */
public class DatabaseColumn {
    String name, dataType, defaultValue, attributeName;
    Boolean nullable, autoIncrement;
    DatabaseKey key;

    public DatabaseColumn() {
        key = DatabaseKey.NONE;
    }
    
    public DatabaseColumn(String name, String dataType) {
        this.name = Util.transformCase(name, Case.CAMEL);
        this.attributeName = Util.transformCase(name, Case.ATTRIBUTE);
        this.dataType = dataType;
        key = DatabaseKey.NONE;
    }
    
    public String getName() {
        return name;
    }

    public Boolean isAutoIncrement()
    {
        return autoIncrement;
    }
    
    public void setAutoIncrement(Boolean value)
    {
        autoIncrement = value;
    }
    
    public void setName(String name) {
        this.name = Util.transformCase(name, Case.CAMEL);
        this.attributeName = Util.transformCase(name, Case.ATTRIBUTE);
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    
    

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public DatabaseKey getKey() {
        return key;
    }

    public void setKey(DatabaseKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "DatabaseColumn{" + "name=" + name + ", dataType=" + dataType + ", defaultValue=" + defaultValue +", nullable=" + nullable + ", key=" + key + '}';
    }
    
}
