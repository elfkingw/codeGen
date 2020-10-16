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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.core.model.DataType;
import org.richie.codeGen.database.Constants;
import org.richie.codeGen.database.util.DataTypeUtils;
import org.richie.codeGen.ui.ButtonEditor;
import org.richie.codeGen.ui.ButtonRenderer;
import org.richie.codeGen.ui.GlobalData;
import org.richie.codeGen.ui.model.DataTypeModel;
import org.richie.codeGen.ui.util.FileUtils;
import org.richie.codeGen.ui.util.XmlParse;

/**
 * @author elfkingw
 */
public class DataTypeConfigPanel extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel configPanel;
    private JScrollPane centerPanel;
    private JTable table;
    private JToolBar toolBar;
    private JButton addLineBtn;
    private JButton saveBtn;
    private JComboBox uiTypesCom;
    private String[] uiTypes;

    private Log log = LogFacotry.getLogger(BaseDataConfigWin.class);
    private DataTypeModel dataTypeModel;

    public DataTypeConfigPanel() {
        super();
        this.setLayout(new BorderLayout());
        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getButtonPanel(), BorderLayout.NORTH);
        initData();
    }

    public JPanel getConfigPanel() {
        if (configPanel == null) {
            configPanel = new JPanel();
            configPanel.setLayout(new BorderLayout());
            configPanel.add(getCenterPanel(), BorderLayout.CENTER);
            configPanel.add(getButtonPanel(), BorderLayout.NORTH);
        }
        return configPanel;
    }

    private void initData() {
        try {
            List<DataType> constList = GlobalData.getDataType();
            if (constList != null) {
                dataTypeModel.setConstantConfigList(constList);
                table.updateUI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JScrollPane getCenterPanel() {

        if (centerPanel == null) {
            dataTypeModel = new DataTypeModel();
            table = new JTable(dataTypeModel);
            table.setBackground(Color.white);
            table.setSelectionBackground(Color.white);
            table.setSelectionForeground(Color.black);
            JTableHeader tableHeader = table.getTableHeader();
            tableHeader.setReorderingAllowed(false);// 表格列不可移动
            table.setFont(new Font("Dialog", 0, 13));
            table.setRowHeight(23);
            try {
                uiTypes = GlobalData.getUITypeStrs();
            } catch (Exception e) {
                log.error("界面初始", e);
            }
            uiTypesCom = new JComboBox(uiTypes);
            TableColumnModel tcm = table.getColumnModel();
            tcm.getColumn(2).setCellEditor(new DefaultCellEditor(uiTypesCom));
            tcm.getColumn(4).setCellRenderer(new ButtonRenderer());
            tcm.getColumn(4).setCellEditor(new ButtonEditor());
            tcm.getColumn(0).setPreferredWidth(50);
            tcm.getColumn(1).setPreferredWidth(120);
            tcm.getColumn(2).setPreferredWidth(100);
            tcm.getColumn(3).setPreferredWidth(100);
            tcm.getColumn(4).setPreferredWidth(50);
            centerPanel = new JScrollPane(table);
            // 增加table里按钮点击事件
            addTableListener();
        }
        return centerPanel;
    }

    public void refreshComBoBox() {
        try {
            uiTypes = GlobalData.getUITypeStrs();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        TableColumnModel tcm = table.getColumnModel();
        uiTypesCom = new JComboBox(uiTypes);
        tcm.getColumn(3).setCellEditor(new DefaultCellEditor(uiTypesCom));
        uiTypesCom.updateUI();
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
            toolBar.addSeparator();
            JButton setDefaultBtn = new JButton("设置默认类型");
            setDefaultBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/config.gif")));
            new DropDownComponent(setDefaultBtn, getButtonPanel());
            toolBar.add(setDefaultBtn);
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
            DataType vo = new DataType();
            dataTypeModel.addRow(vo);
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
            List<DataType> dataTypeList = dataTypeModel.getConstantConfigList();
            GlobalData.setDataType(dataTypeList);
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
                if (col == 4) {
                    dataTypeModel.removeRow(row);
                    table.updateUI();
                }
            }
        });
    }

    class DropDownComponent extends JComponent implements ActionListener {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        protected JComponent drop_down_comp;

        protected JComponent visible_comp;

        protected JMenuBar mb;

        protected JWindow popup;

        protected JPopupMenu pop = new JPopupMenu();

        private JMenuItem[] items = {new JMenuItem(Constants.DATABASE_TYPE_DB2),
                new JMenuItem(Constants.DATABASE_TYPE_ORACLE),
                new JMenuItem(Constants.DATABASE_TYPE_MYSQL),
                new JMenuItem(Constants.DATABASE_TYPE_MSSQL),
                new JMenuItem(Constants.DATABASE_TYPE_INFORMIX)};

        protected JButton arrow;

        public DropDownComponent(JComponent vcomp, JComponent ddcomp) {

            drop_down_comp = ddcomp;
            visible_comp = vcomp;
            arrow = (JButton) visible_comp;
            Insets insets = arrow.getMargin();
            arrow.setMargin(new Insets(insets.top, 1, insets.bottom, 1));
            arrow.addActionListener(this);

            for (int i = 0; i < items.length; i++) {
                items[i].addActionListener(this);
                pop.add(items[i]);
            }

            setupLayout();
        }

        protected void setupLayout() {
            GridBagLayout gbl = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            setLayout(gbl);

            c.weightx = 1.0;
            c.weighty = 1.0;
            c.gridx = 0;
            c.gridy = 0;
            c.fill = GridBagConstraints.BOTH;
            gbl.setConstraints(visible_comp, c);
            add(visible_comp);

            c.weightx = 0;
            c.gridx++;
            gbl.setConstraints(arrow, c);
            add(arrow);
        }

        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == arrow) {
                pop.show(arrow, arrow.getWidth() - visible_comp.getPreferredSize().width, arrow.getHeight());
            } else if (evt.getSource() instanceof JMenuItem) {
                JMenuItem mi = (JMenuItem) evt.getSource();
                System.out.println(mi.getText());
                List<DataType> list = DataTypeUtils.getDataTypeByDataSource(mi.getText());
                dataTypeModel.setConstantConfigList(list);
                table.updateUI();
            }
        }
    }

    private void handException(String msg, Exception e) {
        JOptionPane.showMessageDialog(this, msg + ":" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
        log.error(msg, e);
    }
}
