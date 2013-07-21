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
// Created on 2013-7-20

package org.richie.codeGen.database.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.richie.codeGen.core.exception.CGException;
import org.richie.codeGen.core.model.Column;
import org.richie.codeGen.core.model.DataType;
import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.database.Constants;
import org.richie.codeGen.ui.GlobalData;

/**
 * @author elfkingw
 */
public class DataTypeUtils {

    public static Map<String, String> db2DataTypeMap      = new HashMap<String, String>();
    public static Map<String, String> oracleDataTypeMap   = new HashMap<String, String>();
    public static Map<String, String> sybaseDataTypeMap   = new HashMap<String, String>();
    public static Map<String, String> mysqlDataTypeMap    = new HashMap<String, String>();
    public static Map<String, String> mssqlDataTypeMap    = new HashMap<String, String>();
    public static Map<String, String> informixDataTypeMap = new HashMap<String, String>();
    static {
        db2DataTypeMap.put("BIGINT", "Long");
        oracleDataTypeMap.put("NUMBER", "Long");
        sybaseDataTypeMap.put("BIGINT", "Long");
        mysqlDataTypeMap.put("INTEGER", "Long");
        mssqlDataTypeMap.put("BIGINT", "Long");
        informixDataTypeMap.put("INT8", "Long");

        db2DataTypeMap.put("INTEGER", "Integer");
        oracleDataTypeMap.put("INTEGER", "Integer");
        sybaseDataTypeMap.put("INTEGER", "Integer");
        mysqlDataTypeMap.put("int", "Integer");
        mysqlDataTypeMap.put("INTEGER", "Integer");
        mssqlDataTypeMap.put("INTEGER", "Integer");
        informixDataTypeMap.put("INTEGER", "Integer");

        // db2DataTypeMap.put("BLOB", "Boolean");
        // oracleDataTypeMap.put("BIT", "Boolean");
        sybaseDataTypeMap.put("BIT", "Boolean");
        mysqlDataTypeMap.put("BIT", "Boolean");
        mssqlDataTypeMap.put("BIT", "Boolean");
        informixDataTypeMap.put("BOOLEAN", "Boolean");

        db2DataTypeMap.put("BLOB", "Byte[]");
        oracleDataTypeMap.put("BLOB", "Byte[]");
        sybaseDataTypeMap.put("BLOB", "Byte[]");
        mysqlDataTypeMap.put("BLOB", "Byte[]");
        mssqlDataTypeMap.put("BLOB", "Byte[]");
        informixDataTypeMap.put("BLOB", "Byte[]");

        db2DataTypeMap.put("CHAR", "String");
        oracleDataTypeMap.put("CHAR", "String");
        sybaseDataTypeMap.put("CHAR", "String");
        mysqlDataTypeMap.put("CHAR", "String");
        mssqlDataTypeMap.put("CHAR", "String");
        informixDataTypeMap.put("CHAR", "String");

        db2DataTypeMap.put("VARCHAR", "String");
        oracleDataTypeMap.put("VARCHAR2", "String");
        sybaseDataTypeMap.put("VARCHAR", "String");
        mysqlDataTypeMap.put("VARCHAR", "String");
        mssqlDataTypeMap.put("VARCHAR", "String");
        informixDataTypeMap.put("VARCHAR", "String");

        db2DataTypeMap.put("CLOB", "String");
        oracleDataTypeMap.put("CLOB", "String");
        sybaseDataTypeMap.put("CLOB", "String");
        mysqlDataTypeMap.put("text", "String");
        mssqlDataTypeMap.put("text", "String");
        informixDataTypeMap.put("CLOB", "String");

        db2DataTypeMap.put("DATE", "java.sql.Date");
        oracleDataTypeMap.put("DATE", "java.sql.Date");
        sybaseDataTypeMap.put("DATE", "java.sql.Date");
        mysqlDataTypeMap.put("DATE", "java.sql.Date");
        mssqlDataTypeMap.put("DATE", "java.sql.Date");
        informixDataTypeMap.put("DATE", "java.sql.Date");

        // db2DataTypeMap.put("DATETIME", "java.sql.Date");
        // oracleDataTypeMap.put("DATETIME", "java.sql.Date");
        sybaseDataTypeMap.put("DATETIME", "java.sql.Date");
        mysqlDataTypeMap.put("DATETIME", "java.sql.Date");
        mssqlDataTypeMap.put("DATETIME", "java.sql.Date");
        informixDataTypeMap.put("DATETIME", "java.sql.Date");

        db2DataTypeMap.put("DECIMAL", "java.math.BigDecimal");
        oracleDataTypeMap.put("DECIMAL", "java.math.BigDecimal");
        sybaseDataTypeMap.put("DECIMAL", "java.math.BigDecimal");
        mysqlDataTypeMap.put("DECIMAL", "java.math.BigDecimal");
        mssqlDataTypeMap.put("DECIMAL", "java.math.BigDecimal");
        informixDataTypeMap.put("DECIMAL", "java.math.BigDecimal");

        db2DataTypeMap.put("NUMERIC", "java.math.BigDecimal");
        oracleDataTypeMap.put("NUMERIC", "java.math.BigDecimal");
        sybaseDataTypeMap.put("NUMERIC", "java.math.BigDecimal");
        mysqlDataTypeMap.put("NUMERIC", "java.math.BigDecimal");
        mssqlDataTypeMap.put("NUMERIC", "java.math.BigDecimal");
        informixDataTypeMap.put("NUMERIC", "java.math.BigDecimal");

        db2DataTypeMap.put("DOUBLE", "Double");
        oracleDataTypeMap.put("DOUBLE PRECISION", "Double");
        sybaseDataTypeMap.put("DOUBLE PRECISION", "Double");
        mysqlDataTypeMap.put("DOUBLE PRECISION", "Double");
        mssqlDataTypeMap.put("DOUBLE PRECISION", "Double");
        informixDataTypeMap.put("DOUBLE PRECISION", "Double");

    }

    public static List<DataType> getDataTypeByDataSource(String dataSource) {
        Map<String, String> map = null;
        if (Constants.DATABASE_TYPE_DB2.equalsIgnoreCase(dataSource)) {
            map = db2DataTypeMap;
        } else if (Constants.DATABASE_TYPE_ORACLE.equalsIgnoreCase(dataSource)) {
            map = oracleDataTypeMap;
        } else if (Constants.DATABASE_TYPE_MYSQL.equalsIgnoreCase(dataSource)) {
            map = mysqlDataTypeMap;
        } else if (Constants.DATABASE_TYPE_MSSQL.equalsIgnoreCase(dataSource)) {
            map = mssqlDataTypeMap;
        } else if (Constants.DATABASE_TYPE_INFORMIX.equalsIgnoreCase(dataSource)) {
            map = informixDataTypeMap;
        }
        List<DataType> list = new ArrayList<DataType>();
        if (map != null) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = map.get(key);
                DataType dataType = new DataType();
                dataType.setDataType(key);
                dataType.setJavaType(value);
                list.add(dataType);
            }
        }
        return list;
    }

    /**
     * 根据数据类型的配置来设置表字段的代码类型
     * 
     * @param table
     * @throws CGException
     * @throws Exception
     */
    public static void setDataType(Table table) throws CGException, Exception {
        Map<String, String> map = GlobalData.getDataTypeMap();
        if (table == null) return;
        List<Column> list = table.getFields();
        for (Column column : list) {
            setDataType(column, map);
            Table refTable = column.getRefTable();
            if (refTable != null) {
                refTable.getFields();
                List<Column> refist = table.getFields();
                for (Column refColumn : refist) {
                    setDataType(refColumn, map);
                }
            }
        }
    }

    public static void setDataType(Column column, Map<String, String> map) throws CGException {
        String dataTypeStr = column.getDataType() != null ? column.getDataType().toLowerCase() : null;
        String codeType = map.get(dataTypeStr);
        if (StringUtils.isEmpty(codeType)) {
            throw new CGException("字段【" + column.getCode() + "】数据库类型【" + column.getDataType()
                                  + "】在数据类型配置里没有找到，请在系统配置-->数据类型配置里配置！");
        }
        String type = type(codeType);
        column.setCodeType(type);
    }

    private static String type(String codeType) {
        String type = codeType;
        if (codeType.startsWith("java.lang.")) {
            type = codeType.replace("java.lang.", "");
        }
        return type;

    }

}
