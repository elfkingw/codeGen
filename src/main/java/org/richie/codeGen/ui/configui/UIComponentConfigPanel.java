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
// Created on 2013年10月17日

package org.richie.codeGen.ui.configui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.core.model.UIComponent;
import org.richie.codeGen.ui.ButtonEditor;
import org.richie.codeGen.ui.ButtonRenderer;
import org.richie.codeGen.ui.GlobalData;
import org.richie.codeGen.ui.model.UIComponentModel;
import org.richie.codeGen.ui.util.FileUtils;
import org.richie.codeGen.ui.util.XmlParse;

/**
 * 界面组建配置panel 
 * 用于代码生成前界面组件配置的维护
 * 
 * @author elfkingw
 */
public class UIComponentConfigPanel extends JPanel implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JScrollPane       centerPanel;
    private JTable            table;
    private JToolBar          toolBar;
    private JButton           addLineBtn;
    private JButton           saveBtn;
    private UIComponentModel uiComponontModel;

    private BaseDataConfigWin parent;
    private Log log = LogFacotry.getLogger(UIComponentConfigPanel.class);
    public UIComponentConfigPanel(BaseDataConfigWin parent){
        super();
        this.setLayout(new BorderLayout());
        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getButtonPanel(), BorderLayout.NORTH);
        initData();
        this.parent = parent;
    }

    private void initData() {
        try {
            List<UIComponent> uiTypeList = GlobalData.getUIType();
            if (uiTypeList != null) {
                uiComponontModel.setUIComponentList(uiTypeList);
                table.updateUI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JScrollPane getCenterPanel() {

        if (centerPanel == null) {
            uiComponontModel = new UIComponentModel();
            table = new JTable(uiComponontModel);
            table.setBackground(Color.white);
            table.setSelectionBackground(Color.white);
            table.setSelectionForeground(Color.black);
            JTableHeader tableHeader = table.getTableHeader();
            tableHeader.setReorderingAllowed(false);// 表格列不可移动
            table.setFont(new Font("Dialog", 0, 13));
            table.setRowHeight(23);
            TableColumnModel tcm = table.getColumnModel();
            tcm.getColumn(3).setCellRenderer(new ButtonRenderer());
            tcm.getColumn(3).setCellEditor(new ButtonEditor());
            tcm.getColumn(0).setPreferredWidth(70);
            tcm.getColumn(1).setPreferredWidth(120);
            tcm.getColumn(2).setPreferredWidth(100);
            tcm.getColumn(3).setPreferredWidth(20);
            centerPanel = new JScrollPane(table);
            // 增加table里按钮点击事件
            addTableListener();
        }
        return centerPanel;
    }

    private JToolBar getButtonPanel() {
        if (toolBar == null) {
            toolBar = new JToolBar();
            addLineBtn = new JButton("增加类型");
            addLineBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/add.gif")));
            addLineBtn.addActionListener(this);
            toolBar.add(addLineBtn);
            toolBar.addSeparator();
            saveBtn = new JButton("保存");
            saveBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/save.png")));
            saveBtn.addActionListener(this);
            toolBar.add(saveBtn);
            toolBar.setFloatable(false);
            toolBar.setBorderPainted(true);
        }
        return toolBar;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addLineBtn) {
            UIComponent vo = new UIComponent();
            uiComponontModel.addRow(vo);
            table.updateUI();
        } else if (e.getSource() == saveBtn) {
            onSave();
        }
    }

    /**
     * 
     */
    private void onSave() {
        try {
            List<UIComponent> UIComponentList = uiComponontModel.getUIComponentList();
            GlobalData.setUiTypeList(UIComponentList);
            parent.getDataTypeConfigPanel().refreshComBoBox();
            JOptionPane.showMessageDialog(this, "保存成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            handException("保存失败", ex);
        }
    }

    /*
     * table cell里按钮的事件
     */
    private void addTableListener() {
        table.addMouseListener(new MouseListener() {

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
                int col = table.getSelectedColumn();
                int row = table.getSelectedRow();
                if (col == 3) {
                    uiComponontModel.removeRow(row);
                    table.updateUI();
                }
            }
        });
    }


    private void handException(String msg, Exception e) {
        JOptionPane.showMessageDialog(this, msg + ":" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
        log.error(msg, e);
    }
}
