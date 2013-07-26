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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.richie.codeGen.core.model.Column;
import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.ui.configui.TableTreeWin;
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

    private JTextField        mainTableCode;
    public JTextField         mainTableName;
    public JTextField         mainExtension1;
    public JTextField         mainExtension2;

    private JLabel            subTableCodeLabel;
    private JTextField        subTableCode;
    private JLabel            subTableNameLabel;
    public JTextField         subTableName;
    private JLabel            subExtension1Label;
    public JTextField         subExtension1;
    private JLabel            subExtension2Label;
    public JTextField         subExtension2;

    private JButton           addBtn;
    private JButton           delBtn;
    private CodeGenMainUI     parent;

    public TableSelectUI(JFrame parent){
        super();
        this.parent = (CodeGenMainUI) parent;
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
         addTableListener(mainTable);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(getMainToolBar(), BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);
        return panel;
    }

    public JToolBar getMainToolBar() {
        JToolBar mainToolBar = new JToolBar();
        mainToolBar.add(new JLabel("主表表名："));
        mainTableCode = new JTextField(15);
        mainToolBar.add(mainTableCode);
        mainTableCode.setEnabled(false);
        mainToolBar.add(new JLabel("名称："));
        mainTableName = new JTextField(20);
        mainToolBar.add(mainTableName);
        mainToolBar.add(new JLabel("扩展1："));
        mainExtension1 = new JTextField(20);
        mainToolBar.add(mainExtension1);
        mainToolBar.add(new JLabel("扩展2："));
        mainExtension2 = new JTextField(20);
        mainToolBar.add(mainExtension2);
        mainToolBar.add(new JLabel("                    "));
        mainToolBar.setFloatable(false);
        return mainToolBar;
    }

    public JToolBar getSubToolBar() {
        JToolBar subToolBar = new JToolBar();
        addBtn = new JButton("增加子表");
        addBtn.addActionListener(this);
        subToolBar.add(addBtn);
        delBtn = new JButton("删除子表");
        delBtn.addActionListener(this);
        subToolBar.add(delBtn);
        subToolBar.addSeparator();
        subTableCodeLabel = new JLabel("子表表名：");
        subToolBar.add(subTableCodeLabel);
        subTableCode = new JTextField(15);
        subToolBar.add(subTableCode);
        subTableCode.setEnabled(false);
        subTableNameLabel = new JLabel("名称：");
        subToolBar.add(subTableNameLabel);
        subTableName = new JTextField(20);
        subToolBar.add(subTableName);
        subExtension1Label = new JLabel("扩展1：");
        subToolBar.add(subExtension1Label);
        subExtension1 = new JTextField(20);
        subToolBar.add(subExtension1);
        subExtension2Label = new JLabel("扩展2：");
        subToolBar.add(subExtension2Label);
        subExtension2 = new JTextField(20);
        subToolBar.add(subExtension2);
        subToolBar.setFloatable(false);
        showSubDescription(false);
        return subToolBar;
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
        JScrollPane tablePanel = new JScrollPane(subTable);
        // 增加table里按钮点击事件
        // addTableListener();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(getSubToolBar(), BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);
        addTableListener(subTable);
        return panel;
    }

    /**
     * 主界面菜单选择数据库表，界面联动显示表明细
     * 
     * @param table
     */
    public void initTable(Table table) {
        setTable(table);
        mainTableCode.setText(table.getTableCode());
        mainTableName.setText(table.getTableName());
        List<Column> list = table.getFields();
        mainTableModel.setList(list);
        mainTable.updateUI();
        Table sTable = table.getOneToManyTables();
        if (sTable != null) {
            showSubDescription(true);
            subTableCode.setText(sTable.getTableCode());
            subTableName.setText(sTable.getTableName());
            List<Column> subList = sTable.getFields();
            subTableModel.setList(subList);
            subTable.updateUI();
        } else {
            showSubDescription(false);
            subTableModel.setList(new ArrayList<Column>());
            subTable.updateUI();
        }
    }

    private void showSubDescription(boolean isVisible) {
        if (isVisible) {
            subTableCodeLabel.setVisible(true);
            subTableNameLabel.setVisible(true);
            subExtension1Label.setVisible(true);
            subExtension2Label.setVisible(true);
            subTableCode.setVisible(true);
            subTableName.setVisible(true);
            subExtension1.setVisible(true);
            subExtension2.setVisible(true);
            delBtn.setVisible(true);
            addBtn.setVisible(false);
        } else {
            subTableCodeLabel.setVisible(false);
            subTableNameLabel.setVisible(false);
            subExtension1Label.setVisible(false);
            subExtension2Label.setVisible(false);
            subTableCode.setVisible(false);
            subTableName.setVisible(false);
            subExtension1.setVisible(false);
            subExtension2.setVisible(false);
            delBtn.setVisible(false);
            addBtn.setVisible(true);
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
        if(e.getSource() == delBtn){
            table.setOneToManyTables(null);
            showSubDescription(false);
            subTableModel.setList(new ArrayList<Column>());
            subTable.updateUI();
        }else if(e.getSource() == addBtn){
            TableTreeWin win = new TableTreeWin(parent);
            win.setBounds(this.getX()+400, this.getY()+150, win.getWidth(), win.getHeight());
            win.setModal(true);
            win.setVisible(true);
            doSelected(win.getSelectedTable(), "addBtn");
        }
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.ui.configui.TreeSelectListener#doSelected(org.richie.codeGen.core.model.Table, java.lang.String)
     */
    public void doSelected(Table selectTable, String targetSource) {
        if("addBtn".equals(targetSource)){
            if(table == null){
                JOptionPane.showMessageDialog(this, "请先选择主表");
                return;
            }
            table.setOneToManyTables(selectTable);
            Table sTable = selectTable;
            if (sTable != null) {
                showSubDescription(true);
                subTableCode.setText(sTable.getTableCode());
                subTableName.setText(sTable.getTableName());
                List<Column> subList = sTable.getFields();
                subTableModel.setList(subList);
                subTable.updateUI();
            } else {
                showSubDescription(false);
                subTableModel.setList(new ArrayList<Column>());
                subTable.updateUI();
            }
        }
    }
    /**
     * table cell里按钮的事件
     */
    private void addTableListener(JTable selectedTable) {
        selectedTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table =   (JTable) e.getSource();
                int col = table.getSelectedColumn();
                int row = table.getSelectedRow();
                if (col == 9) {
                    List<Column> list = mainTableModel.getList();
                    if(table == mainTable){
                        list = mainTableModel.getList();
                    }else{
                        list = subTableModel.getList();
                    }
                    Column column = list.get(row);
                    TableTreeWin win = new TableTreeWin(parent);
                    win.setBounds(getMainPanel().getX()+400, getMainPanel().getY()+150, win.getWidth(), win.getHeight());
                    win.setModal(true);
                    win.setVisible(true);
                    Table selectedTable = win.getSelectedTable();
                    column.setRefTable(selectedTable);
                    if(selectedTable !=  null){
                        column.setIsForeignKey(true);
                    }else{
                        column.setIsForeignKey(false);
                    }
                    table.updateUI();
                }
            }
        });
    }

}
