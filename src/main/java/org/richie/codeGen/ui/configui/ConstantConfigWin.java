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

package org.richie.codeGen.ui.configui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang.StringUtils;
import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.ui.ButtonEditor;
import org.richie.codeGen.ui.ButtonRenderer;
import org.richie.codeGen.ui.GenAndPreviewUI;
import org.richie.codeGen.ui.GlobalData;
import org.richie.codeGen.ui.model.ConstantConfigModel;
import org.richie.codeGen.ui.model.ConstantConfigVo;
import org.richie.codeGen.ui.model.OutFileRootPathVo;
import org.richie.codeGen.ui.util.FileUtils;
import org.richie.codeGen.ui.util.XmlParse;

/**
 * @author elfkingw
 */
public class ConstantConfigWin extends JDialog implements ActionListener {

    /**
     * 
     */
    private static final long   serialVersionUID = 1L;
    private JPanel              configPanel;
    private JPanel              northPanel;
    private JScrollPane         centerPanel;
    private JTable              table;
    private JPanel              buttonPanel;
    private JButton             addLineBtn;
    private JButton             saveBtn;
    private JTextField          rootPathName1;
    private JTextField          rootPathValue1;
    private JTextField          rootPathName2;
    private JTextField          rootPathValue2;
    private JTextField          rootPathName3;
    private JTextField          rootPathValue3;
    private JTextField          rootPathName4;
    private JTextField          rootPathValue4;
    private JTextField          tablePrefix;
    private JButton             chooseBtn1;
    private JButton             chooseBtn2;
    private JButton             chooseBtn3;
    private JButton             chooseBtn4;
    private GenAndPreviewUI     previewUI;

    private Log                 log              = LogFacotry.getLogger(ConstantConfigWin.class);
    private ConstantConfigModel constantConfigModel;

    public ConstantConfigWin(GenAndPreviewUI previewUI){

        super();
        this.previewUI = previewUI;
        initLize();
    }

    public void initLize() {
        setTitle("变量设置");
        setSize(560, 500);
        add(getConfigPanel());
        initData();
    }

    public JPanel getConfigPanel() {
        if (configPanel == null) {
            configPanel = new JPanel();
            configPanel.setLayout(new BorderLayout(20, 20));
            configPanel.add(getNorthPanel(), BorderLayout.NORTH);
            configPanel.add(getCenterPanel(), BorderLayout.CENTER);
            configPanel.add(getButtonPanel(), BorderLayout.SOUTH);
        }
        return configPanel;
    }

    private void initData() {
        try {
            List<ConstantConfigVo> constList = GlobalData.getConstantList();
            if (constList != null) {
                constantConfigModel.setConstantConfigList(constList);
                table.updateUI();
            }
            OutFileRootPathVo rootVo = GlobalData.getOutFileRootPathVo();
            if (rootVo != null) setOutFileRootPathVo(rootVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel getNorthPanel() {
        if (northPanel == null) {
            northPanel = new JPanel();
            northPanel.setSize(560, 100);
            northPanel.setLayout(new GridLayout(5, 1));
            JPanel panel1 = new JPanel();
            JLabel rootPahthLabel1 = new JLabel("输出文件根目录1:");
            panel1.add(rootPahthLabel1);
            JLabel rootNameLabel1 = new JLabel("名称:");
            panel1.add(rootNameLabel1);
            rootPathName1 = new JTextField(15);
            rootPathName1.setEnabled(false);
            panel1.add(rootPathName1);
            JLabel rootVavlueLabel1 = new JLabel("路径:");
            panel1.add(rootVavlueLabel1);
            rootPathValue1 = new JTextField(30);
            panel1.add(rootPathValue1);
            rootPathValue1.setEnabled(false);
            chooseBtn1 = new JButton("...");
            chooseBtn1.addActionListener(this);
            chooseBtn1.setEnabled(false);
            panel1.add(chooseBtn1);
            northPanel.add(panel1);

            JPanel panel2 = new JPanel();
            JLabel rootPahthLabel2 = new JLabel("输出文件根目录2:");
            panel2.add(rootPahthLabel2);
            JLabel rootNameLabel2 = new JLabel("名称:");
            panel2.add(rootNameLabel2);
            rootPathName2 = new JTextField(15);
            panel2.add(rootPathName2);
            JLabel rootVavlueLabel2 = new JLabel("路径:");
            panel2.add(rootVavlueLabel2);
            rootPathValue2 = new JTextField(30);
            panel2.add(rootPathValue2);
            chooseBtn2 = new JButton("...");
            chooseBtn2.addActionListener(this);
            panel2.add(chooseBtn2);
            northPanel.add(panel2);

            JPanel panel3 = new JPanel();
            JLabel rootPahthLabel3 = new JLabel("输出文件根目录3:");
            panel3.add(rootPahthLabel3);
            JLabel rootNameLabel3 = new JLabel("名称:");
            panel3.add(rootNameLabel3);
            rootPathName3 = new JTextField(15);
            panel3.add(rootPathName3);
            JLabel rootVavlueLabel3 = new JLabel("路径:");
            panel3.add(rootVavlueLabel3);
            rootPathValue3 = new JTextField(30);
            panel3.add(rootPathValue3);
            chooseBtn3 = new JButton("...");
            chooseBtn3.addActionListener(this);
            panel3.add(chooseBtn3);
            northPanel.add(panel3);

            JPanel panel4 = new JPanel();
            JLabel rootPahthLabel4 = new JLabel("输出文件根目录4:");
            panel4.add(rootPahthLabel4);
            JLabel rootNameLabel4 = new JLabel("名称:");
            panel4.add(rootNameLabel4);
            rootPathName4 = new JTextField(15);
            panel4.add(rootPathName4);
            JLabel rootVavlueLabel4 = new JLabel("路径:");
            panel4.add(rootVavlueLabel4);
            rootPathValue4 = new JTextField(30);
            panel4.add(rootPathValue4);
            chooseBtn4 = new JButton("...");
            chooseBtn4.addActionListener(this);
            panel4.add(chooseBtn4);
            northPanel.add(panel4);
            
            JPanel panel5 = new JPanel();
            JLabel tablePrefixLabel = new JLabel("表名前缀(用逗号隔开): ");
            panel5.add(tablePrefixLabel);
            tablePrefix = new JTextField(23);
            panel5.add(tablePrefix);
            JLabel tablePrefixNoteLabel = new JLabel("例如 (SM,GL)，生成代码时会去掉表名前缀");
            panel5.add(tablePrefixNoteLabel);
            northPanel.add(panel5);
        }
        return northPanel;
    }

    public JScrollPane getCenterPanel() {

        if (centerPanel == null) {
            constantConfigModel = new ConstantConfigModel();
            table = new JTable(constantConfigModel);
            table.setBackground(Color.white);
            table.setSelectionBackground(Color.white);
            table.setSelectionForeground(Color.black);
            JTableHeader tableHeader = table.getTableHeader();
            tableHeader.setReorderingAllowed(false);// 表格列不可移动
            table.setFont(new Font("Dialog", 0, 13));
            table.setRowHeight(23);
            TemplateConfigWin.hideColumn(table, 3);
            JComboBox<String> cm = new JComboBox<String>(GlobalData.costantType);
            TableColumnModel tcm = table.getColumnModel();
            tcm.getColumn(4).setCellRenderer(new ButtonRenderer());
            tcm.getColumn(4).setCellEditor(new ButtonEditor());
            tcm.getColumn(1).setCellEditor(new DefaultCellEditor(cm));
            tcm.getColumn(0).setPreferredWidth(50);
            tcm.getColumn(1).setPreferredWidth(30);
            tcm.getColumn(2).setPreferredWidth(240);
            tcm.getColumn(4).setPreferredWidth(20);
            centerPanel = new JScrollPane(table);
            // 增加table里按钮点击事件
            addTableListener();
        }
        return centerPanel;
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            addLineBtn = new JButton("增加变量");
            addLineBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/add.gif")));
            addLineBtn.addActionListener(this);
            buttonPanel.add(addLineBtn);
            saveBtn = new JButton("保存");
            saveBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/save.png")));
            saveBtn.addActionListener(this);
            buttonPanel.add(saveBtn);
        }
        return buttonPanel;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addLineBtn) {
            ConstantConfigVo vo = new ConstantConfigVo();
            constantConfigModel.addRow(vo);
            table.updateUI();
        } else if (e.getSource() == saveBtn) {
            onSave();
        } else if (e.getSource() == chooseBtn1 || e.getSource() == chooseBtn2 || e.getSource() == chooseBtn3
                   || e.getSource() == chooseBtn4) {
            JFileChooser jfc = new JFileChooser();// 文件选择器
            if (e.getSource() == chooseBtn1) {
                jfc.setCurrentDirectory(new File(rootPathValue1.getText()));
            } else if (e.getSource() == chooseBtn2) {
                jfc.setCurrentDirectory(new File(rootPathValue2.getText()));
            } else if (e.getSource() == chooseBtn3) {
                jfc.setCurrentDirectory(new File(rootPathValue3.getText()));
            } else if (e.getSource() == chooseBtn4) {
                jfc.setCurrentDirectory(new File(rootPathValue4.getText()));
            }
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 设定只能选择到文件
            int status = jfc.showOpenDialog(this);
            if (status == JFileChooser.CANCEL_OPTION) {
                return;
            } else {
                File file = jfc.getSelectedFile();
                if (e.getSource() == chooseBtn1) {
                    rootPathValue1.setText(file.getAbsolutePath());
                } else if (e.getSource() == chooseBtn2) {
                    rootPathValue2.setText(file.getAbsolutePath());
                } else if (e.getSource() == chooseBtn3) {
                    rootPathValue3.setText(file.getAbsolutePath());
                } else if (e.getSource() == chooseBtn4) {
                    rootPathValue4.setText(file.getAbsolutePath());
                }
            }
        }

    }

    /**
     * 
     */
    private void onSave() {
        try {
            List<ConstantConfigVo> constList = constantConfigModel.getConstantConfigList();
            XmlParse<ConstantConfigVo> consXmlParse = new XmlParse<ConstantConfigVo>(ConstantConfigVo.class);
            consXmlParse.genVoToXmlFile(constList, FileUtils.getConstantConfigPath());
            OutFileRootPathVo rootVo = GlobalData.getOutFileRootPathVo();
            getOutFileRootPathVo(rootVo);
            if(!StringUtils.isEmpty(rootVo.getName1()) && StringUtils.isEmpty(rootVo.getPath1())){
                JOptionPane.showMessageDialog(this, "请输入根目录1的路径！", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else if(!StringUtils.isEmpty(rootVo.getName2()) && StringUtils.isEmpty(rootVo.getPath2())){
                JOptionPane.showMessageDialog(this, "请输入根目录2的路径！", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else if(!StringUtils.isEmpty(rootVo.getName3()) && StringUtils.isEmpty(rootVo.getPath3())){
                JOptionPane.showMessageDialog(this, "请输入根目录3的路径！", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else if(!StringUtils.isEmpty(rootVo.getName4()) && StringUtils.isEmpty(rootVo.getPath4())){
                JOptionPane.showMessageDialog(this, "请输入根目录4的路径！", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            XmlParse<OutFileRootPathVo> outFileXmlParse = new XmlParse<OutFileRootPathVo>(OutFileRootPathVo.class);
            outFileXmlParse.genVoToXmlFile(rootVo, FileUtils.getOutFileRootPath());
            previewUI.refreshComBoBox();
            JOptionPane.showMessageDialog(this, "保存成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            handException("保存失败", ex);
        }
    }

    private OutFileRootPathVo getOutFileRootPathVo(OutFileRootPathVo rootVo) {
        if(rootVo == null){
            rootVo= new OutFileRootPathVo();
        }
        rootVo.setName1(rootPathName1.getText());
        rootVo.setName2(rootPathName2.getText());
        rootVo.setName3(rootPathName3.getText());
        rootVo.setName4(rootPathName4.getText());
        rootVo.setPath1(rootPathValue1.getText());
        rootVo.setPath2(rootPathValue2.getText());
        rootVo.setPath3(rootPathValue3.getText());
        rootVo.setPath4(rootPathValue4.getText());
        rootVo.setTablePrefix(tablePrefix.getText());
        return rootVo;
    }

    private void setOutFileRootPathVo(OutFileRootPathVo vo) {
        rootPathName1.setText(vo.getName1());
        rootPathName2.setText(vo.getName2());
        rootPathName3.setText(vo.getName3());
        rootPathName4.setText(vo.getName4());
        rootPathValue1.setText(vo.getPath1());
        rootPathValue2.setText(vo.getPath2());
        rootPathValue3.setText(vo.getPath3());
        rootPathValue4.setText(vo.getPath4());
        tablePrefix.setText(vo.getTablePrefix());
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
                    constantConfigModel.removeRow(row);
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
