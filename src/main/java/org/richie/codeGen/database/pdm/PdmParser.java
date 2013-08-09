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

package org.richie.codeGen.database.pdm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.richie.codeGen.core.exception.CGException;
import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.core.model.Column;
import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.database.Constants;

/**
 * @author elfkingw pdm解析类
 */
public class PdmParser {

    private static Log log = LogFacotry.getLogger(PdmParser.class);

    /**
     * 解析pdm文件
     * 
     * @param filePath pdm文件路径
     * @return
     * @throws CGException
     * @throws Exception
     */
    public static List<Table> parsePdmFile(String filePath) throws CGException, Exception {
        File f = new File(filePath);
        if (!f.exists()) {
            throw new CGException("pdm file is not exits");
        }
        SAXReader sr = new SAXReader();
        Document doc = null;
        List<Table> tables = null;
        try {
            doc = sr.read(f);
            Map<String, Table> tableMap = new HashMap<String, Table>();
            Map<String, Column> columnMap = new HashMap<String, Column>();
            tables = parseTables(doc, tableMap, columnMap);
            parseReference(doc, tableMap, columnMap);
        } catch (Exception e) {
            log.error("解析pdm失败：", e);
            throw new Exception("解析pdm失败:" + e.getMessage());
        }
        return tables;
    }

    /**
     * @param doc
     * @param tableMap
     * @param columnMap
     */
    @SuppressWarnings("unchecked")
    private static List<Table> parseTables(Document doc, Map<String, Table> tableMap, Map<String, Column> columnMap) {
        List<Table> tables = new ArrayList<Table>();
        String dataBaseCode = null;
        String dataBaseName = null;
        String dataBaseType = null;
        List<Element> headerList = doc.selectNodes("//?PowerDesigner");
        if (headerList != null) {
            Element rootElement = doc.getRootElement();
            dataBaseType = rootElement.attributeValue("Target");
        }
        List<Element> dList = doc.selectNodes("//o:Model");
        if (dList != null && dList.size() > 0) {
            Element element = dList.get(0);
            dataBaseName = element.elementText("Name");
            dataBaseCode = element.elementText("Code");
        }
        List<Element> tList = doc.selectNodes("//c:Tables//o:Table");
        for (Element tableElement : tList) {
            List<Column> list = new ArrayList<Column>();
            Table table = parseTableElement(tableElement);
            table.setDataBaseCode(dataBaseCode);
            table.setDataBaseName(dataBaseName);
            table.setDataBaseType(getDataBaseByPdmFile(dataBaseType));
            tableMap.put(table.getId(), table);
            String primaryKeyId = getPrimaryKeyId(tableElement);
            if(tableElement.element("Columns") == null) continue;
            List<Element> colList = tableElement.element("Columns").elements("Column");
            for (Element colElement : colList) {
                Column column = parseColumn(primaryKeyId, colElement);
                columnMap.put(column.getId(), column);
                list.add(column);
            }
            table.setFields(list);
            tables.add(table);
        }
        return tables;
    }

    /**
     * @param vo
     * @param tableElement
     */
    private static Table parseTableElement(Element tableElement) {
        Table table = new Table();
        table.setId(tableElement.attributeValue("Id"));
        table.setName(tableElement.elementTextTrim("Name"));
        table.setCode(tableElement.elementTextTrim("Code"));
        table.setUpdateTime(tableElement.elementTextTrim("ModificationDate"));
        return table;
    }

    /**
     * 解析表之间的关系
     * 
     * @param doc
     * @param tableMap
     * @param columnMap
     */
    @SuppressWarnings("unchecked")
    private static void parseReference(Document doc, Map<String, Table> tableMap, Map<String, Column> columnMap) {
        Iterator<Element> itr = doc.selectNodes("//c:References//o:Reference").iterator();
        while (itr.hasNext()) {
            Element refElement = itr.next();
            Table parentTable = tableMap.get(refElement.element("ParentTable").element("Table").attributeValue("Ref"));
            // System.out.println("parentTable:"+parentTable);
            Table childTable = tableMap.get(refElement.element("ChildTable").element("Table").attributeValue("Ref"));
            // System.out.println("childTable:"+childTable);
            parentTable.setOneToManyTables(childTable);
            childTable.addManyToOneTable(parentTable);
            Column pkColumn = columnMap.get(refElement.element("Joins").element("ReferenceJoin").element("Object2").element("Column").attributeValue("Ref"));
            if (pkColumn != null) {
                pkColumn.setIsForeignKey(true);
                pkColumn.setRefTable(parentTable);
            }
        }
    }

    /**
     * 解析表字段
     * 
     * @param col
     * @param primaryKeyId
     * @param colElement
     */
    private static Column parseColumn(String primaryKeyId, Element colElement) {
        Column col = new Column();
        String columnId = colElement.attributeValue("Id");
        col.setId(columnId);
        col.setDefaultValue(colElement.elementTextTrim("DefaultValue"));
        col.setName(colElement.elementTextTrim("Name"));
        col.setIsForeignKey(false);
        col.setIsPrimaryKey(false);
        col.setIsHiden(false);
        if (colElement.elementTextTrim("DataType") == null) {
            col.setDataType(null);
        } else if (colElement.elementTextTrim("DataType").indexOf("(") > 0) {
            col.setDataType(colElement.elementTextTrim("DataType").substring(0,
                                                                             colElement.elementTextTrim("DataType").indexOf("(")));
        } else {
            col.setDataType(colElement.elementTextTrim("DataType"));
        }
        col.setCode(colElement.elementTextTrim("Code"));
        if (colElement.elementTextTrim("Length") != null) {
            col.setLength(Integer.parseInt(colElement.elementTextTrim("Length")));
        }
        if (colElement.elementTextTrim("Precision") != null) {
            col.setPrecision(Integer.parseInt(colElement.elementTextTrim("Precision")));
        }
        if (colElement.elementTextTrim("Mandatory") != null && "1".equals(colElement.elementTextTrim("Mandatory"))) {
            col.setIsNotNull(true);
        } else {
            col.setIsNotNull(false);
        }
        if (columnId.equals(primaryKeyId)) {
            col.setIsPrimaryKey(true);
            col.setIsNotNull(true);
            col.setIsHiden(true);
        }
        return col;
    }

    @SuppressWarnings("unchecked")
    private static String getPrimaryKeyId(Element tableElement) {
        String primaryKeyId = null;
        Element keys = tableElement.element("Keys");
        if (keys == null) {
            return null;
        }
        List<Element> elements = keys.elements("Key");
        String keys_primarykey_ref_id = tableElement.element("PrimaryKey").element("Key").attributeValue("Ref");
        for (Element element : elements) {
            String keys_key_id = element.attributeValue("Id");
            String keys_column_ref = element.element("Key.Columns").element("Column").attributeValue("Ref");
            if (keys_primarykey_ref_id.equals(keys_key_id)) primaryKeyId = keys_column_ref;
        }
        return primaryKeyId;
    }

    private static String getDataBaseByPdmFile(String fileDataBaseType) {
        if (StringUtils.isEmpty(fileDataBaseType)) {
            return null;
        }
        if (fileDataBaseType.toLowerCase().contains(Constants.DATABASE_TYPE_MYSQL)) {
            return Constants.DATABASE_TYPE_MYSQL;
        } else if (fileDataBaseType.toLowerCase().contains(Constants.DATABASE_TYPE_MSSQL)) {
            return Constants.DATABASE_TYPE_MSSQL;
        } else if (fileDataBaseType.toLowerCase().contains(Constants.DATABASE_TYPE_ORACLE)) {
            return Constants.DATABASE_TYPE_ORACLE;
        } else if (fileDataBaseType.toLowerCase().contains(Constants.DATABASE_TYPE_DB2)) {
            return Constants.DATABASE_TYPE_DB2;
        } else if (fileDataBaseType.toLowerCase().contains(Constants.DATABASE_TYPE_INFORMIX)) {
            return Constants.DATABASE_TYPE_INFORMIX;
        }
        return null;
    }
}
