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
// Created on 2013-7-25

package org.richie.codeGen.ui.configui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;

import org.richie.codeGen.core.model.Column;
import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.ui.CodeGenMainUI;
import org.richie.codeGen.ui.TableTreeRender;
import org.richie.codeGen.ui.model.TableTreeNode;

/**
 * @author elfkingw
 */
public class TableTreeWin extends JDialog implements ActionListener {

    /**
     * 
     */
    private static final long  serialVersionUID = 1L;
    private JPanel             westPanel;
    private JTextField         filterField;
    private JScrollPane        treePanel;
    private JTree              tree;
    private List<Table>        tableList;
    private CodeGenMainUI      parent;
    
    private Table selectedTable;


    public TableTreeWin(CodeGenMainUI parent){
        this.parent = parent;
        initlize();
    }

    public void initlize() {
        add(getWestPanel());
        setSize(200, 300);
        setTitle("选择表");
        tableList = parent.getTableList();
        initTree(tableList);
    }

    public JPanel getWestPanel() {
        if (westPanel == null) {
            westPanel = new JPanel();
            westPanel.setLayout(new BorderLayout());
            JLabel filterLable = new JLabel("过滤");
            JPanel filterPanel = new JPanel();
            filterPanel.add(filterLable);
            filterField = new JTextField(15);
            Document doc = filterField.getDocument();
            doc.addDocumentListener(new TextDocumentListener());
            filterPanel.add(filterField);
            filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            westPanel.setPreferredSize(new Dimension(200, 650));// 关键代码,设置JPanel的大
            westPanel.add(filterPanel, BorderLayout.NORTH);
            westPanel.add(getTreepanel(), BorderLayout.CENTER);
        }
        return westPanel;
    }

    public JScrollPane getTreepanel() {
        if (treePanel == null) {
            treePanel = new JScrollPane();
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("请导入文件");
            tree = new JTree(root);
            treePanel.setViewportView(tree);
        }
        return treePanel;
    }

    private class TextDocumentListener implements DocumentListener {

        /*
         * (non-Javadoc)
         * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            processChange(e);
        }

        /*
         * (non-Javadoc)
         * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            processChange(e);
        }

        /*
         * (non-Javadoc)
         * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            processChange(e);
        }

        private void processChange(DocumentEvent e) {
            Document doc = e.getDocument();
            try {
                String s = doc.getText(0, doc.getLength());
                filterTree(s);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }

    }

    private void filterTree(String filterStr) {
        if (filterStr == null || filterStr.equals("")) {
            tree = initTreeData(tableList);
            treePanel.setViewportView(tree);
            getTreepanel().updateUI();
        }
        if (filterStr != null && filterStr.length() <= 1) {
            return;
        }
        List<Table> list = new ArrayList<Table>();
        for (Table table : tableList) {
            if (table.getName().toLowerCase().contains(filterStr.toLowerCase())
                || table.getCode().toLowerCase().contains(filterStr.toLowerCase())) {
                list.add(table);
            }
        }
        tree = initTreeData(list);
        treePanel.setViewportView(tree);
        getTreepanel().updateUI();

    }

    private void initTree(List<Table> list) {
        try {
            tree = initTreeData(list);
            treePanel.setViewportView(tree);
            getTreepanel().updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param list
     * @return
     */
    private JTree initTreeData(List<Table> list) {
        TableTreeNode root = null;
        if (list == null || list.size() == 0) {
            root = new TableTreeNode("请导入文件");
        } else {
            root = new TableTreeNode(list.get(0).getDataBaseName() + ":" + list.get(0).getDataBaseCode());
            for (Table table : list) {
                list.get(0).getDataBaseName();
                TableTreeNode node = new TableTreeNode(table);
                root.add(node);
                List<Column> columnList = table.getFields();
                for (Column column : columnList) {
                    TableTreeNode columnNode = new TableTreeNode(column);
                    node.add(columnNode);
                }
            }
        }
        JTree newTree = new JTree(root);
        newTree.setCellRenderer(new TableTreeRender());
        newTree.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent me) {
                if(me.getClickCount() ==2){
                    doDbMouseClicked(me);
                }
            }

        });
        return newTree;
    }

    private void doDbMouseClicked(MouseEvent me) {
        TableTreeNode node = (TableTreeNode) tree.getLastSelectedPathComponent();
        if (node != null && !node.isRoot()) {
            if (node.getTable() != null) {
//                listener.doSelected(node.getTable(), targetSource);
                setSelectedTable(node.getTable());
                this.setVisible(false);
            }
        }

    }

    
    public Table getSelectedTable() {
        return selectedTable;
    }

    
    public void setSelectedTable(Table selectedTable) {
        this.selectedTable = selectedTable;
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
