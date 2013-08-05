/*
 * Copyright 2013  elfkingw
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
// Created on 2013-6-29
package org.richie.codeGen.core.model;

import java.util.ArrayList;
import java.util.List;

import org.richie.codeGen.core.util.StringUtil;
import org.richie.codeGen.ui.GlobalData;
import org.richie.codeGen.ui.model.OutFileRootPathVo;

/**
 * @author wanghua 2013-6-29
 */
public class Table implements Cloneable {

    private String       id;
    private String       code;
    private String       name;
    private List<Column> fields;
    private String       className;
    private List<Table>  manyToOneTables;
    private Table        oneToManyTables;
    private String         updateTime;
    private String       dataBaseName;
    private String       dataBaseCode;
    private String       extension1;
    private String       extension2;
    private String       dataBaseType;

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getDataBaseCode() {
        return dataBaseCode;
    }

    public void setDataBaseCode(String dataBaseCode) {
        this.dataBaseCode = dataBaseCode;
    }

    public List<Table> getManyToOneTables() {
        return manyToOneTables;
    }

    public void setManyToOneTables(List<Table> manyToOneTables) {
        this.manyToOneTables = manyToOneTables;
    }

    public Table getOneToManyTables() {
        return oneToManyTables;
    }

    public void setOneToManyTables(Table oneToManyTables) {
        this.oneToManyTables = oneToManyTables;
    }

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

    public List<Column> getFields() {
        return fields;
    }

    public void setFields(List<Column> fields) {
        this.fields = fields;
    }

    public String getClassName() {
        if (className == null && code != null) {
            String prefix = null;
            try {
                OutFileRootPathVo vo = GlobalData.getOutFileRootPathVo();
                if (vo != null) {
                    prefix = vo.getTablePrefix();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String tableCode = getCode();
            if (prefix != null) {
                String[] prefixs = prefix.split(",");
                for (String prefixStr : prefixs) {
                    if (tableCode.startsWith(prefixStr)) {
                        tableCode = tableCode.substring(prefixStr.length());
                    }
                    if (tableCode.startsWith(prefixStr + "_")) {
                        tableCode = tableCode.substring(prefixStr.length() + 1);
                    }
                }
            }
            StringUtil util = new StringUtil();
            className = util.getClassName(tableCode);
        }
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void addManyToOneTable(Table table) {
        if (manyToOneTables == null) {
            manyToOneTables = new ArrayList<Table>();
        }
        manyToOneTables.add(table);
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

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String toString() {
        return ("tableCode:" + getCode() + " tableName:" + getName() + " Id:" + getId() + " ManyToOne:["
                + (getManyToOneTables() != null ? getManyToOneTables().size() + "" : null) + "] onToMany:" + getOneToManyTables());
    }

    public Object clone() {
        Table o = null;
        try {
            o = (Table) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

}
