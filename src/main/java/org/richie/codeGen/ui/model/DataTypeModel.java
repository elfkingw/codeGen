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
// Created on 2013-7-14

package org.richie.codeGen.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.richie.codeGen.core.model.DataType;

/**
 * @author elfkingw
 */
public class DataTypeModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long      serialVersionUID = 1L;
    private List<DataType> dataTypeList;
    private String[]               titles           = { "数据类型","编程语言程序类型",  "备注", "删除" };

    public DataTypeModel(){
        dataTypeList = new ArrayList<DataType>();
    }

    public void addRow(DataType vo) {
        dataTypeList.add(vo);
    }

    public void removeRow(int row) {
        dataTypeList.remove(row);
    }

    public void removeRows(int row, int count) {
        for (int i = 0; i < count; i++) {
            if (dataTypeList.size() > row) {
                dataTypeList.remove(row);
            }
        }
    }

    /**
     * 让表格中某些值可修改，但需要setValueAt(Object value, int row, int col)方法配合才能使修改生效
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        return dataTypeList.size();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        return titles.length;
    }

    public String getColumnName(int col) {
        return titles[col];
    }

    /**
     * 使修改的内容生效
     */
    public void setValueAt(Object value, int row, int col) {
        DataType vo = dataTypeList.get(row);
        switch (col) {
            case 0:
                vo.setDataType((String)value);
                break;
            case 1:
                vo.setJavaType((String) value);
                break;
            case 2:
                vo.setNote((String) value);
                break;
        }
        fireTableCellUpdated(row, col);  
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (dataTypeList == null || dataTypeList.size() == 0) {
            return null;
        }
        DataType vo = dataTypeList.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = vo.getDataType();
                break;
            case 1:
                value = vo.getJavaType();
                break;
            case 2:
                value = vo.getNote();
                break;
            case 3:
                value = "删除";
                break;
        }
        return value;
    }

    public List<DataType> getConstantConfigList() {
        return dataTypeList;
    }

    public void setConstantConfigList(List<DataType> dataTypeList) {
        this.dataTypeList = dataTypeList;
    }

}
