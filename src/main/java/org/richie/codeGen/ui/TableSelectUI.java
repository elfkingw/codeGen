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

package org.richie.codeGen.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.richie.codeGen.core.model.Column;
import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.ui.model.ColumnModel;

/**
 * @author elfkingw
 */
public class TableSelectUI extends JPanel implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JSplitPane        centerPanel;
    private ColumnModel       mainTableModel;
    private JTable            mainTable;
    private ColumnModel       subTableModel;
    private JTable            subTable;
    private Table             table;

    public TableSelectUI(JFrame parent){
        super();
        initLize();
    }

    public void initLize() {
        setSize(400, 400);
        setLayout(new BorderLayout());
        add(getMainPanel(), BorderLayout.CENTER);
    }

    public JSplitPane getMainPanel() {
        if (centerPanel == null) {
            centerPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            centerPanel.add(getMainTablePanel(), JSplitPane.LEFT);
            centerPanel.add(getSubTablePanel(), JSplitPane.RIGHT);
            centerPanel.setDividerLocation(0.6);
        }
        return centerPanel;
    }

    public void setDividerLocation() {
        getMainPanel().setDividerLocation(0.5);
    }

    public JPanel getMainTablePanel() {
        mainTableModel = new ColumnModel();
        mainTable = new JTable(mainTableModel);
        mainTable.setBackground(Color.white);
        mainTable.setSelectionBackground(Color.white);
        mainTable.setSelectionForeground(Color.black);
        JTableHeader tableHeader = mainTable.getTableHeader();
        tableHeader.setReorderingAllowed(false);// 表格列不可移动
        mainTable.setFont(new Font("Dialog", 0, 12));
        mainTable.setRowHeight(23);
        TableColumnModel tcm = mainTable.getColumnModel();
        JComboBox uiTypeComboBox = new JComboBox(GlobalData.uiType);
        tcm.getColumn(11).setCellEditor(new DefaultCellEditor(uiTypeComboBox));
        tcm.getColumn(0).setPreferredWidth(70);
        tcm.getColumn(1).setPreferredWidth(60);
        tcm.getColumn(2).setPreferredWidth(4);
        tcm.getColumn(3).setPreferredWidth(35);
        tcm.getColumn(4).setPreferredWidth(4);
        tcm.getColumn(5).setPreferredWidth(4);
        tcm.getColumn(6).setPreferredWidth(35);
        tcm.getColumn(7).setPreferredWidth(4);
        tcm.getColumn(8).setPreferredWidth(4);
        tcm.getColumn(9).setPreferredWidth(4);
        tcm.getColumn(10).setPreferredWidth(35);
        tcm.getColumn(11).setPreferredWidth(35);
        tcm.getColumn(12).setPreferredWidth(10);
        tcm.getColumn(13).setPreferredWidth(10);
        tcm.getColumn(14).setPreferredWidth(10);
        JScrollPane tablePanel = new JScrollPane(mainTable);
        // 增加table里按钮点击事件
        // addTableListener();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel nPanel = new JPanel();
        nPanel.add(new JLabel("--主表区--"));
        panel.add(nPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);
        return panel;
    }

    public JPanel getSubTablePanel() {
        subTableModel = new ColumnModel();
        subTable = new JTable(subTableModel);
        subTable.setBackground(Color.white);
        subTable.setSelectionBackground(Color.white);
        subTable.setSelectionForeground(Color.black);
        JTableHeader tableHeader = subTable.getTableHeader();
        tableHeader.setReorderingAllowed(false);// 表格列不可移动
        subTable.setFont(new Font("Dialog", 0, 12));
        subTable.setRowHeight(23);
        TableColumnModel tcm = subTable.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(70);
        tcm.getColumn(1).setPreferredWidth(60);
        tcm.getColumn(2).setPreferredWidth(4);
        tcm.getColumn(3).setPreferredWidth(35);
        tcm.getColumn(4).setPreferredWidth(4);
        tcm.getColumn(5).setPreferredWidth(4);
        tcm.getColumn(6).setPreferredWidth(35);
        tcm.getColumn(7).setPreferredWidth(4);
        tcm.getColumn(8).setPreferredWidth(4);
        tcm.getColumn(9).setPreferredWidth(4);
        tcm.getColumn(10).setPreferredWidth(35);
        tcm.getColumn(11).setPreferredWidth(35);
        tcm.getColumn(12).setPreferredWidth(10);
        tcm.getColumn(13).setPreferredWidth(10);
        tcm.getColumn(14).setPreferredWidth(10);
        JScrollPane tablePanel = new JScrollPane(subTable);
        // 增加table里按钮点击事件
        // addTableListener();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel nPanel = new JPanel();
        nPanel.add(new JLabel("--子表区--"));
        panel.add(nPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);
        return panel;
    }

    public void initTable(Table table) {
        setTable(table);
        List<Column> list = table.getFields();
        mainTableModel.setList(list);
        mainTable.updateUI();
        Table sTable = table.getOneToManyTables();
        if (sTable != null) {
            List<Column> subList = sTable.getFields();
            subTableModel.setList(subList);
            subTable.updateUI();
        } else {
            subTableModel.setList(new ArrayList<Column>());
            subTable.updateUI();
        }
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}
