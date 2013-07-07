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

/**
 * @author wanghua 2013-6-29
 */
public class Table {

    private String       id;
    private String       tableCode;
    private String       tableName;
    private List<Column> fieldList;
    private String       javaName;
    private List<Table>  manyToOneTables;
    private Table        oneToManyTables;
    private long         updateTime;

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

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Column> fieldList) {
        this.fieldList = fieldList;
    }

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public void addManyToOneTable(Table table) {
        if (manyToOneTables == null) {
            manyToOneTables = new ArrayList<Table>();
        }
        manyToOneTables.add(table);
    }

    public String toString() {
        return ("tableCode:" + getTableCode() + " tableName:" + getTableName()
                + " Id:" + getId() + " ManyToOne:[" + (getManyToOneTables()!= null ?getManyToOneTables().size()+"" : null)
                + "] onToMany:" + getOneToManyTables());
    }
}
