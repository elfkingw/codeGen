/*
 * Copyright 2013 elfkingw
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Created on 2013-7-7

package org.richie.codeGen.core.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author elfkingw
 */
public class Column {

    private Boolean isHiden;
    private String  id;
    private String  code;
    private String  name;
    private String  javaName;
    private String  defaultValue;
    private String  dataType;
    private String  codeType;
    private String  shortCodeType;
    private int     length;
    private int     precision;
    private Boolean isNotNull;
    private String  note;
    private Boolean isForeignKey;
    private Boolean isPrimaryKey;
    private Table   refTable;
    private String  uiType;
    private String  extension1;
    private String  extension2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Table getRefTable() {
        return refTable;
    }

    public void setRefTable(Table refTable) {
        this.refTable = refTable;
    }

    public Boolean getIsHiden() {
        return isHiden;
    }

    public void setIsHiden(Boolean isHiden) {
        this.isHiden = isHiden;
    }

    public Boolean getIsNotNull() {
        return isNotNull;
    }

    public void setIsNotNull(Boolean isNotNull) {
        this.isNotNull = isNotNull;
    }

    public Boolean getIsForeignKey() {
        return isForeignKey;
    }

    public void setIsForeignKey(Boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getUiType() {
        if (uiType == null) {
            if ("date".equalsIgnoreCase(dataType)) {
                uiType = "DateField";
            } else if (isForeignKey) {
                uiType = "ComboBox";
            } else {
                uiType = "TextField";
            }
        }
        return uiType;
    }

    public void setUiType(String uiType) {
        this.uiType = uiType;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    /**
     * 去掉包名的类型 eg: 如果codeType= java.math.BigDecimal 那么 shortCodeType=BigDecimal
     * 
     * @return
     */
    public String getShortCodeType() {
        if (!StringUtils.isEmpty(codeType)) {
            int index = codeType.lastIndexOf(".");
            shortCodeType = codeType.substring(index + 1);
        }
        return shortCodeType;
    }

    public void setShortCodeType(String shortCodeType) {
        this.shortCodeType = shortCodeType;
    }

    public String getExtension1() {
        return extension1;
    }

    public void setExtension1(String extension1) {
        this.extension1 = extension1;
    }

    public String getExtension2() {
        return extension2;
    }

    public void setExtension2(String extension2) {
        this.extension2 = extension2;
    }

    public String toString() {
        return "id:" + getId() + " code:" + getCode() + " name:" + getName() + " javaName:" + getJavaName()
               + " isForeignKey:" + getIsForeignKey() + " isPrimaryKey:" + getIsPrimaryKey() + " refTable:"
               + getRefTable();
    }

    public static void main(String[] args) {
        Column column = new Column();
        column.setCodeType("java.math.BigDecimal");
        System.out.println(column.getShortCodeType());
    }
}
