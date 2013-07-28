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
// Created on 2013-7-17

package org.richie.codeGen.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.richie.codeGen.core.model.Column;

/**
 * @author elfkingw
 */
public class ColumnModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String[]          titles           = {  "列代码", "列名称", "主键", "数据类型", "长度", "精度", "不能为空", "备注", "外键",
            "外键对应表", "是否隐藏","界面控件", "扩展1", "扩展2"};
    private List<Column>      list;

    public ColumnModel(){
        list = new ArrayList<Column>();
    }

    public ColumnModel(List<Column> list){
        this.list = list;
    }

    public String getColumnName(int col) {
        return titles[col];
    }

    public void addRow(Column vo) {
        list.add(vo);
    }

    public void removeRow(int row) {
        list.remove(row);
    }

    public void removeRows(int row, int count) {
        for (int i = 0; i < count; i++) {
            if (list.size() > row) {
                list.remove(row);
            }
        }
    }

    /**
     * 让表格中某些值可修改，但需要setValueAt(Object value, int row, int col)方法配合才能使修改生效
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex >= 0 && columnIndex <= 5 && columnIndex !=1) {
            return false;
        }
        return true;
    }

    /**
     * 使修改的内容生效
     */
    public void setValueAt(Object value, int row, int col) {
        Column vo = list.get(row);
        switch (col) {
            case 0:
                vo.setCode((String) value);
                break;
            case 1:
                vo.setName((String) value);
                break;
            case 2:
                vo.setIsPrimarykey((Boolean) value);
                break;
            case 3:
                vo.setDataType((String) value);
                break;
            case 4:
                vo.setLength((Integer) value);
                break;
            case 5:
                vo.setPrecision((Integer) value);
                break;
            case 6:
                vo.setIsNotNull((Boolean) value);
                break;
            case 7:
                vo.setNote((String) value);
                break;
            case 8:
                vo.setIsForeignKey((Boolean) value);
                break;
            case 9:
                break;
            case 10:
                vo.setIsHiden((Boolean) value);
                break;
            case 11:
                vo.setUiType((String) value);
                break;
            case 12:
                vo.setExtension1((String) value);
                break;
            case 13:
                vo.setExtension2((String) value);
                break;
        }
        fireTableCellUpdated(row, col);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        return list.size();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        return titles.length;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (list == null || list.size() == 0) {
            return null;
        }
        Column vo = list.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
           
            case 0:
                value = vo.getCode();
                break;
            case 1:
                value = vo.getName();
                break;
            case 2:
                value = vo.getIsPrimarykey();
                break;
            case 3:
                value = vo.getDataType();
                break;
            case 4:
                value = vo.getLength();
                break;
            case 5:
                value = vo.getPrecision();
                break;
            case 6:
                value = vo.getIsNotNull();
                break;
            case 7:
                value = vo.getNote();
                break;
            case 8:
                value = vo.getIsForeignKey();
                break;
            case 9:
                if (vo.getRefTable() != null) value = vo.getRefTable().getTableCode();
                break;
            case 10:
                value = vo.getIsHiden();
                break;
            case 11:
                value = vo.getUiType();
                break;
            case 12:
                value = vo.getExtension1();
                break;
            case 13:
                value = vo.getExtension2();
                break;
        }
        return value;
    }

    public List<Column> getList() {
        return list;
    }

    public void setList(List<Column> list) {
        this.list = list;
    }

    public Class<?> getColumnClass(int col) {
        if (col ==  10 || col == 2 || col == 6 || col == 8) {
            return Boolean.class;
        } else {
            return String.class;
        }
    }
}
