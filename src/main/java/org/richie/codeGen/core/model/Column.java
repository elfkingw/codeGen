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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.richie.codeGen.ui.GlobalData;

/**
 * 数据库表字段信息，界面上 表选择区中列表中的信息
 * <p>
 * 模板中调用为 例如：<br>
 * #foreach($f in $table.fields)<br>
 * 表名为： ${f.code} 表中文名为：!{f.name}<br>
 * #end
 * 
 * @author elfkingw
 */
public class Column {

    private Boolean isHiden;
    private String  id;
    private String  code;
    private String  name;
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

    /**
     * 返回数据表的字段名 对应pdm中字段的Code 界面中字段列表中的【列代码】列
     * <p>
     * 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * ${f.code} 或者 ${f.getCode()} <br>
     * #end
     * 
     * @return 数据表的字段名
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置数据库表的字段名
     * 
     * @param code 数据库表的字段名
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 返回数据表的字段名 对应pdm中字段的Name 界面中字段列表中的【列名称】列
     * <p>
     * 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * ${f.name} 或者 ${f.getName()}<br>
     * #end
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置数据库表字段的中文名
     * 
     * @param name 数据库表字段的的中文名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回数据表字段默认值
     * <p>
     * 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * ${f.defaultValue} 或者 ${f.getDefaultValue()}<br>
     * #end
     * 
     * @return 数据表字段默认值
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置数据表字段默认值
     * 
     * @param defaultValue 数据表字段默认值
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 返回数据表字段数据库类型
     * <p>
     * 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * ${f.dataType} 或者 ${f.getDataType()}<br>
     * #end
     * 
     * @return 数据表字段数据库类型
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * 设置数据表字段数据库类型
     * 
     * @param defaultValue 数据表字段数据库类型
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * 返回数据表字段数据库长度
     * <p>
     * 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * ${f.length} 或者 ${f.getLength()}<br>
     * #end
     * 
     * @return 数据表字段数据库长度
     */
    public int getLength() {
        return length;
    }

    /**
     * 设置数据表字段数据库长度
     * 
     * @param 数据表字段数据库长度
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * 返回数据表字段数据库精度 小数点精度
     * <p>
     * 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * ${f.precision} 或者 ${f.getPrecision()}<br>
     * #end
     * 
     * @return 数据表字段数据库长度
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * 设置数据表字段数据库精度
     * 
     * @param precision 数据表字段数据库精度
     */
    public void setPrecision(int precision) {
        this.precision = precision;
    }

    /**
     * 返回数据表字段备注
     * <p>
     * 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * ${f.note} 或者 ${f.getNote()}<br>
     * #end
     * 
     * @return 数据表字段备注
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置数据表字段备注
     * 
     * @param note 数据表字段备注
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 返回数据库表字段（该字段为外键） 对应表 对应界面上【外键对应表】列<br>
     * 对应pdm文件中之间关系
     * <p>
     * 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * if($cf.refTable != null)<br>
     * foreach($cf in $table.fields)<br>
     * $cf.code #end #end #end
     * 
     * @return 数据库表字段（该字段为外键） 对应表
     */
    public Table getRefTable() {
        return refTable;
    }

    /**
     * 设置数据库表字段（该字段为外键） 对应表
     * 
     * @param refTable 数据库表字段（该字段为外键） 对应表
     */
    public void setRefTable(Table refTable) {
        this.refTable = refTable;
    }

    /**
     * 数据库表字段是否隐藏，对应界面中【是否隐藏】字段 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * if($f.isHiden) 或者 if($f.getIsHiden())<br>
     * #end<br>
     * #end
     * 
     * @return 是否隐藏
     */
    public Boolean getIsHiden() {
        return isHiden;
    }

    /**
     * 设置数据库表字段是否隐藏
     * 
     * @param isHiden 数据库表字段是否隐藏
     */
    public void setIsHiden(Boolean isHiden) {
        this.isHiden = isHiden;
    }

    /**
     * 数据库表字段是否为空，对应界面中【不能为空】字段 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * if($f.isNotNull) 或者 if($f.getIsNotNull())<br>
     * #end<br>
     * #end
     * 
     * @return 是否不能为空
     */
    public Boolean getIsNotNull() {
        return isNotNull;
    }

    /**
     * 设置数据库表字段是否为空
     * 
     * @param isNotNull 数据库表字段是否为空
     */
    public void setIsNotNull(Boolean isNotNull) {
        this.isNotNull = isNotNull;
    }

    /**
     * 数据库表字段是否外键，对应界面中【是否外键】字段 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * if($f.isForeignKey) 或者 if($f.getIsForeignKey())<br>
     * #end<br>
     * #end
     * 
     * @return 是否外键
     */
    public Boolean getIsForeignKey() {
        return isForeignKey;
    }

    /**
     * 设置数据库表字段是否外键
     * 
     * @param isForeignKey 数据库表字段是否外键
     */
    public void setIsForeignKey(Boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    /**
     * 数据库表字段是否主键，对应界面中【是否主键】字段 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * if($f.isPrimaryKey) 或者 if($f.getIsPrimaryKey())<br>
     * #end<br>
     * #end
     * 
     * @return 是否主键
     */
    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    /**
     * 设置数据库表字段是否主键
     * 
     * @param isPrimaryKey 数据库表字段是否主键
     */
    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    /**
     * 数据库表字段的界面控件类型，对应界面中【界面控件】字段 模板中应用如：<br>
     * #foreach($f in $table.fields)<br>
     * $table.uiType 或者 $table.getUiType() <br>
     * #end
     * 
     * @return
     */
    public String getUiType() {
        if (uiType == null) {
            //根据数据类型的默认配置控件来设置
            try {
                List<DataType> list = GlobalData.getDataType();
                for (DataType type : list) {
                    if (type.getDataType().equals(dataType)) {
                        uiType = type.getUiType();
                    }
                }
                if(uiType == null){
                    uiType = "TextField";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return uiType;
    }

    /**
     * 设置数据库表字段的界面控件类型
     * 
     * @param uiType 数据库表字段的界面控件类型
     */
    public void setUiType(String uiType) {
        this.uiType = uiType;
    }

    /**
     * 数据库表字段的代码类型，根据系统配置-->常量配置中类型对应获取代码类型<br>
     * #foreach($f in $table.fields)<br>
     * $table.codeType 或者 $table.getCodeType() <br>
     * #end
     * 
     * @return
     */
    public String getCodeType() {
        return codeType;
    }

    /**
     * 数据库表字段的代码类型
     * 
     * @param codeType 数据库表字段的代码类型
     */
    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    /**
     * 去掉包名的类型 <br>
     * eg: 如果codeType= java.math.BigDecimal 那么 shortCodeType=BigDecimal
     * 
     * @return 去掉包名的类型
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

    /**
     * 返回扩展1 界面中表选择区扩展1对列应的值 <br>
     * 可在界面上按需求设置，写模板时用到
     * <p>
     * 模板中调用方法：<br>
     * #foreach($f in $table.fields)<br>
     * $table.codeType 或者 $table.getCodeType() <br>
     * #end
     * 
     * @return 扩展1
     */
    public String getExtension1() {
        return extension1;
    }

    public void setExtension1(String extension1) {
        this.extension1 = extension1;
    }

    /**
     * 返回扩展2 界面中表选择区扩展2对列应的值 <br>
     * 可在界面上按需求设置，写模板时用到
     * <p>
     * 模板中调用方法：<br>
     * #foreach($f in $table.fields)<br>
     * $table.codeType 或者 $table.getCodeType() <br>
     * #end
     * 
     * @return 扩展2
     */
    public String getExtension2() {
        return extension2;
    }

    public void setExtension2(String extension2) {
        this.extension2 = extension2;
    }

    public String toString() {
        return "id:" + getId() + " code:" + getCode() + " name:" + getName() + " isForeignKey:" + getIsForeignKey()
               + " isPrimaryKey:" + getIsPrimaryKey() + " refTable:" + getRefTable();
    }

    public static void main(String[] args) {
        Column column = new Column();
        column.setCodeType("java.math.BigDecimal");
        System.out.println(column.getShortCodeType());
    }
}
