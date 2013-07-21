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
// Created on 2013-7-12

package org.richie.codeGen.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.richie.codeGen.ui.GlobalData;

/**
 * @author elfkingw
 */
public class CodeTemplateModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long    serialVersionUID = 1L;
    private List<CodeTemplateVo> templateList;
    private boolean              isTemplateEdit;

    public CodeTemplateModel(boolean isTemplateEdit){
        this.isTemplateEdit = isTemplateEdit;
        templateList = new ArrayList<CodeTemplateVo>();
    }

    public CodeTemplateModel(List<CodeTemplateVo> list){
        templateList = list;
    }

    private String[] titles = { "选择", "模板名称", "模板文件", "输出文件根目录", "输出包", "后缀", "生成预览", "编辑模板", "删除" };

    public void addRow(CodeTemplateVo vo) {
        templateList.add(vo);
    }

    public void removeRow(int row) {
        templateList.remove(row);
    }

    public void removeRows(int row, int count) {
        for (int i = 0; i < count; i++) {
            if (templateList.size() > row) {
                templateList.remove(row);
            }
        }
    }

    /**
     * 让表格中某些值可修改，但需要setValueAt(Object value, int row, int col)方法配合才能使修改生效
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (!isTemplateEdit && columnIndex == 2) {
            return false;
        }
        return true;
    }

    /**
     * 使修改的内容生效
     */
    public void setValueAt(Object value, int row, int col) {
        CodeTemplateVo vo = templateList.get(row);
        if (col == 4 && value != null) {
            for (int i = 0; i < templateList.size(); i++) {
                CodeTemplateVo templatVo = templateList.get(i);
                if (i != row && (templatVo.getOutFilePath() == null || "".equals(templatVo.getOutFilePath()))) {
                    templateList.get(i).setOutFilePath((String) value);
                }
            }
        }
        if (col == 1 && value != null) {
            CodeTemplateVo tvo;
            try {
                tvo = GlobalData.getTemplateByName((String) value);
                if (tvo != null) {
                    vo.setFileName(tvo.getFileName());
                    vo.setOutFilePath(tvo.getOutFilePath());
                    vo.setOutFilePathRoot(tvo.getOutFilePathRoot());
                    vo.setSuffix(tvo.getSuffix());
                    for (int i = 0; i < titles.length - 1; i++) {
                        fireTableCellUpdated(row, i);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        switch (col) {
            case 0:
                vo.setIsSelected((Boolean) value);
                break;
            case 1:
                vo.setTemplateName((String) value);
                break;
            case 2:
                vo.setFileName((String) value);
                break;
            case 3:
                vo.setOutFilePathRoot((String) value);
                break;
            case 4:
                vo.setOutFilePath((String) value);
                break;
            case 5:
                vo.setSuffix((String) value);
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
        return templateList.size();
    }

    public String getColumnName(int col) {
        return titles[col];
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
        if (templateList == null || templateList.size() == 0) {
            return null;
        }
        CodeTemplateVo vo = templateList.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = vo.getIsSelected();
                break;
            case 1:
                value = vo.getTemplateName();
                break;
            case 2:
                value = vo.getFileName();
                break;
            case 3:
                value = vo.getOutFilePathRoot();
                break;
            case 4:
                value = vo.getOutFilePath();
                break;
            case 5:
                value = vo.getSuffix();
                break;
            case 6:
                value = "预览";
                break;
            case 7:
                value = "编辑";
                break;
            case 8:
                value = "删除";
                break;
        }
        return value;
    }

    public List<CodeTemplateVo> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<CodeTemplateVo> templateList) {
        this.templateList = templateList;
    }

    public Class<?> getColumnClass(int col) {
        if (col == 0) {
            return Boolean.class;
        } else {
            return String.class;
        }
    }

}
