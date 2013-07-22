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

package org.richie.codeGen.database.util;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.richie.codeGen.core.exception.CGException;
import org.richie.codeGen.core.model.Column;
import org.richie.codeGen.core.model.Table;

/**
 * @author elfkingw
 */
public class PdmParser {

    @SuppressWarnings("unchecked")
    public static List<Table> parsePdmFile(String filePath) throws CGException {
        List<Table> tables = new ArrayList<Table>();
        Table table = null;
        File f = new File(filePath);
        if (!f.exists()) {
            throw new CGException("pdm file is not exits");
        }
        SAXReader sr = new SAXReader();
        Document doc = null;
        try {
            doc = sr.read(f);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String, Table> tableMap = new HashMap<String, Table>();
        Map<String, Column> columnMap = new HashMap<String, Column>();
        String dataBaseCode = null;
        String dataBaseName = null;
        List<Element> dList = doc.selectNodes("//o:Model");
        for (Element element : dList) {
            dataBaseName = element.elementText("Name");
            dataBaseCode = element.elementText("Code");

        }
        Iterator<Element> itr = doc.selectNodes("//c:Tables//o:Table").iterator();
        while (itr.hasNext()) {
            List<Column> list = new ArrayList<Column>();
            Column column = null;
            Element tableElement = itr.next();
            table = parseTableElement(tableElement, dataBaseCode, dataBaseName);
            tableMap.put(table.getId(), table);
            String primaryKeyId = getPrimaryKeyId(tableElement);
            Iterator<Element> colItr = tableElement.element("Columns").elements("Column").iterator();
            while (colItr.hasNext()) {
                try {
                    Element colElement = colItr.next();
                    column = parseColumn(primaryKeyId, colElement);
                    columnMap.put(column.getId(), column);
                    list.add(column);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            table.setFields(list);
            tables.add(table);
        }
        parseReference(doc, tableMap, columnMap);
        return tables;
    }

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
        col.setIsPrimarykey(false);
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
            col.setIsPrimarykey(true);
            col.setIsNotNull(true);
            col.setIsHiden(true);
        }
        return col;
    }

    /**
     * @param vo
     * @param tableElement
     */
    private static Table parseTableElement(Element tableElement, String dataBaseCode, String dataBaseName) {
        Table table = new Table();
        table.setId(tableElement.attributeValue("Id"));
        table.setTableName(tableElement.elementTextTrim("Name"));
        table.setTableCode(tableElement.elementTextTrim("Code"));
        table.setDataBaseCode(dataBaseCode);
        table.setDataBaseName(dataBaseName);
        return table;
    }

    private static String getPrimaryKeyId(Element tableElement) {
        String primaryKeyId = null;
        Element keys = tableElement.element("Keys");
        if (keys == null) {
            return null;
        }
        String keys_key_id = tableElement.element("Keys").element("Key").attributeValue("Id");
        String keys_column_ref = tableElement.element("Keys").element("Key").element("Key.Columns").element("Column").attributeValue("Ref");
        String keys_primarykey_ref_id = tableElement.element("PrimaryKey").element("Key").attributeValue("Ref");
        if (keys_primarykey_ref_id.equals(keys_key_id)) primaryKeyId = keys_column_ref;
        return primaryKeyId;
    }
}
