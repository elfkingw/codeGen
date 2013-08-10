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
 * 数据表信息<br>
 * 该工具以 table 对应该类<br>
 * 模板中调用例如：$table.code<br>
 * 
 * @author wanghua 2013-6-29
 */
public class Table implements Cloneable {

    private String       id;
    private String       code;
    private String       name;
    private List<Column> fields;
    private String       className;
    private List<Table>  manyToOneTables;
    private Table        childTable;
    private String       updateTime;
    private String       dataBaseName;
    private String       dataBaseCode;
    private String       extension1;
    private String       extension2;
    private String       dataBaseType;

    /**
     * 返回数据库中文名
     * 
     * @return 数据库中文名
     */
    public String getDataBaseName() {
        return dataBaseName;
    }

    /**
     * 设置数据库中文名
     * 
     * @return 数据库中文名
     */
    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    /**
     * 返回数据库名
     * 
     * @return 数据库名
     */
    public String getDataBaseCode() {
        return dataBaseCode;
    }

    /**
     * 设置数据库名
     * 
     * @return 数据库名
     */
    public void setDataBaseCode(String dataBaseCode) {
        this.dataBaseCode = dataBaseCode;
    }

    /**
     * 返回 manyToOneTables即该表的外键字段对应的表集合
     * 
     * @return manyToOneTables
     */
    public List<Table> getManyToOneTables() {
        return manyToOneTables;
    }

    /**
     * 设置manyToOneTables即表的所有外键字段对应的表集合
     * 
     * @param manyToOneTables
     */
    public void setManyToOneTables(List<Table> manyToOneTables) {
        this.manyToOneTables = manyToOneTables;
    }

    /**
     * 获取子表信息，目前只支持一个子表信息，即操作界面表选择区下方子表
     * <p>
     * 模板中掉用方法 ： ${table.ChildTable()) 或者 ${table.getChildTable} 调用方法和java用法一样，下同
     * 
     * @return 子表信息
     */
    public Table getChildTable() {
        return childTable;
    }

    /**
     * 设置子表
     * 
     * @param childTable
     */
    public void setChildTable(Table childTable) {
        this.childTable = childTable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 返回数据库表名 对应Pdm文件中表的Code
     * <p>
     * 模板中掉用方法 ： ${table.getCode()) 或者 ${table.code} 调用方法和java用法一样，下同
     * 
     * @return 返回表名
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置数据库表名
     * 
     * @param code 表名
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 返回 因数据库表中文名对应Pdm文件中表的Name
     * <p>
     * 模板中掉用方法 ： ${table.getName()) 或者 ${table.name}
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置表中文名
     * 
     * @param name 表中文名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回表的所有字段集合
     * <p>
     * 模板中调用方法：${table.getFileds()} 或者 ${table.fields}
     * 
     * @return
     */
    public List<Column> getFields() {
        return fields;
    }

    /**
     * 设置表的所有字段
     * 
     * @param fields 表字段集合
     */
    public void setFields(List<Column> fields) {
        this.fields = fields;
    }

    /**
     * 返回表对应类名，以驼峰式结构类名，同时去掉 变量设置中的设置的表名前缀
     * <p>
     * 例如： 1.表名为 SM_SYSTEM_CONFIG 变量设置中表前缀为：SM <br>
     * 返回的类名为 SystemConfig <br>
     * 2.表名为SYSTEM_CONFIG 变量设置中表前缀没有设置 <br>
     * 返回的类名为SystemConfig
     * <p>
     * 模板中调用方法：${table.getClassName()} 或者 ${table.className}
     * 
     * @return
     */
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

    /**
     * 设置表对应的类名
     * 
     * @param className 表对应的类名
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 返回pdm文件中表的修改时间
     * 
     * @return pdm文件中表的修改时间
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置表修改时间
     * 
     * @param updateTime 表修改时间
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 增加和该表多对一关系的表增加到 manyToOneTables里 manyToOneTables即该表的外键字段对应的表集合
     * 
     * @param table 表信息
     */
    public void addManyToOneTable(Table table) {
        if (manyToOneTables == null) {
            manyToOneTables = new ArrayList<Table>();
        }
        manyToOneTables.add(table);
    }

    /**
     * 返回扩展1 界面中表选择区扩展1对列应的值 <b> 可在界面上按需求设置，写模板时用到
     * <p>
     * 模板中调用方法：${table.getExtension1()} 或者 ${table.extension1}
     * 
     * @return 展1
     */
    public String getExtension1() {
        return extension1;
    }

    /**
     * 设置扩展1 界面中表选择区扩展1对列应的值
     * 
     * @param extension1 扩展1
     */
    public void setExtension1(String extension1) {
        this.extension1 = extension1;
    }

    /**
     * 返回扩展2 界面中表选择区扩展2列对应的值 <br>
     * 可在界面上按需求设置，写模板时用到
     * <p>
     * 模板中调用方法：${table.getExtension2()} 或者 ${table.extension2}
     * 
     * @return 扩展2
     */
    public String getExtension2() {
        return extension2;
    }

    /**
     * 设置扩展2 界面中表选择区扩展1对列应的值
     * 
     * @param extension1 扩展2
     */
    public void setExtension2(String extension2) {
        this.extension2 = extension2;
    }

    /**
     * 返回数据库类型
     * 
     * @return 数据库类型
     */
    public String getDataBaseType() {
        return dataBaseType;
    }

    /**
     * 设置数据库类型
     * 
     * @param dataBaseType 数据库类型
     */
    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String toString() {
        return ("tableCode:" + getCode() + " tableName:" + getName() + " Id:" + getId() + " ManyToOne:["
                + (getManyToOneTables() != null ? getManyToOneTables().size() + "" : null) + "] onToMany:" + getChildTable());
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
