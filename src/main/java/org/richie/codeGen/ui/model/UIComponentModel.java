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

import org.richie.codeGen.core.model.UIComponent;

/**
 * @author elfkingw
 */
public class UIComponentModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long      serialVersionUID = 1L;
    private List<UIComponent> UIComponentList;
    private String[]               titles           = { "控件代码","控件名称", "备注", "删除" };

    public UIComponentModel(){
        UIComponentList = new ArrayList<UIComponent>();
    }

    public void addRow(UIComponent vo) {
        UIComponentList.add(vo);
    }

    public void removeRow(int row) {
        UIComponentList.remove(row);
    }

    public void removeRows(int row, int count) {
        for (int i = 0; i < count; i++) {
            if (UIComponentList.size() > row) {
                UIComponentList.remove(row);
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
        return UIComponentList.size();
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
        UIComponent vo = UIComponentList.get(row);
        switch (col) {
            case 0:
                vo.setCode((String)value);
                break;
            case 1:
                vo.setName((String) value);
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
        if (UIComponentList == null || UIComponentList.size() == 0) {
            return null;
        }
        UIComponent vo = UIComponentList.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = vo.getCode();
                break;
            case 1:
                value = vo.getName();
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

    
    /**
     * @return the uIComponentList
     */
    public List<UIComponent> getUIComponentList() {
        return UIComponentList;
    }

    
    /**
     * @param uIComponentList the uIComponentList to set
     */
    public void setUIComponentList(List<UIComponent> uIComponentList) {
        UIComponentList = uIComponentList;
    }

   

}
