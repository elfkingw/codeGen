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

/**
 * @author elfkingw
 */
public class Column {

    private String  id;
    private String  code;
    private String  name;
    private String  javaName;
    private String  defaultValue;
    private String  dataType;
    private int     length;
    private int     precision;
    private boolean isNull;
    private String  note;
    private boolean isForeignKey;
    private boolean isPrimarykey;
    private Table   refTable;

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

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean isNull) {
        this.isNull = isNull;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isForeignKey() {
        return isForeignKey;
    }

    public void setForeignKey(boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public boolean isPrimarykey() {
        return isPrimarykey;
    }

    public void setPrimarykey(boolean isPrimarykey) {
        this.isPrimarykey = isPrimarykey;
    }

    public Table getRefTable() {
        return refTable;
    }

    public void setRefTable(Table refTable) {
        this.refTable = refTable;
    }

    public String toString() {
        return "id:" + getId() + " code:" + getCode() + " name:" + getName() + " javaName:" + getJavaName()
               + " isForeignKey:" + isForeignKey() + " isPrimaryKey:" + isPrimarykey()+" refTable:"+getRefTable();
    }
}
