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
// Created on 2013-7-13

package org.richie.codeGen.ui.configui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.core.util.StringUtil;
import org.richie.codeGen.ui.ButtonEditor;
import org.richie.codeGen.ui.ButtonRenderer;
import org.richie.codeGen.ui.GenAndPreviewUI;
import org.richie.codeGen.ui.GlobalData;
import org.richie.codeGen.ui.model.CodeTemplateModel;
import org.richie.codeGen.ui.model.CodeTemplateVo;
import org.richie.codeGen.ui.util.FileUtils;
import org.richie.codeGen.ui.util.XmlParse;

/**
 * @author elfkingw
 */
public class TemplateConfigWin extends JDialog implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Log               log              = LogFacotry.getLogger(TemplateConfigWin.class);
    private CodeTemplateModel templateModel;
    private JTable            table;
    private JButton           addLineBtn;
    private JButton           saveBtn;
    private JPanel            configPanel;
    private JScrollPane       templatePanel;
    private GenAndPreviewUI   parent;
    private String[]          rootPathNames;
    private JComboBox rootPathCom;

    public TemplateConfigWin(){
        initLize();
    }

    public TemplateConfigWin(GenAndPreviewUI parent){
        this.parent = parent;
        initLize();
    }

    public void initLize() {
        setTitle("模板配置");
        setSize(800, 500);
        add(getConifgPanel());
        initTableData();
    }

    public JPanel getConifgPanel() {
        if (configPanel == null) {
            configPanel = new JPanel();
            configPanel.setLayout(new BorderLayout());
            configPanel.add(getTemplatePanel(), BorderLayout.CENTER);
            JToolBar toolBar = new JToolBar();
            addLineBtn = new JButton("增加模板");
            addLineBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/add.gif")));
            addLineBtn.addActionListener(this);
            toolBar.add(addLineBtn);
            toolBar.addSeparator();
            saveBtn = new JButton("保存");
            saveBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/save.png")));
            saveBtn.addActionListener(this);
            toolBar.add(saveBtn);
            toolBar.setFloatable(false);
            configPanel.add(toolBar, BorderLayout.NORTH);
        }
        return configPanel;
    }

    public JScrollPane getTemplatePanel() {
        if (templatePanel == null) {
            templateModel = new CodeTemplateModel(true);
            table = new JTable(templateModel);
            table.setBackground(Color.white);
            table.setSelectionBackground(Color.white);
            table.setSelectionForeground(Color.black);
            JTableHeader tableHeader = table.getTableHeader();
            tableHeader.setReorderingAllowed(false);// 表格列不可移动
            table.setFont(new Font("Dialog", 0, 13));
            table.setRowHeight(23);
            TableColumnModel tcm = table.getColumnModel();
            try {
                rootPathNames = GlobalData.getOutFileRootNames();
            } catch (Exception e) {
                e.printStackTrace();
            }
            rootPathCom = new JComboBox(rootPathNames);
            hideColumn(table, 0);
            hideColumn(table, 5);
            hideColumn(table, 7);
            tcm.getColumn(3).setCellEditor(new DefaultCellEditor(rootPathCom));
            tcm.getColumn(8).setCellRenderer(new ButtonRenderer());
            tcm.getColumn(8).setCellEditor(new ButtonEditor());
            tcm.getColumn(9).setCellRenderer(new ButtonRenderer());
            tcm.getColumn(9).setCellEditor(new ButtonEditor());
            tcm.getColumn(1).setPreferredWidth(50);
            tcm.getColumn(2).setPreferredWidth(30);
            tcm.getColumn(3).setPreferredWidth(40);
            tcm.getColumn(4).setPreferredWidth(150);
            tcm.getColumn(5).setPreferredWidth(15);
            tcm.getColumn(8).setPreferredWidth(8);
            tcm.getColumn(9).setPreferredWidth(8);
            templatePanel = new JScrollPane(table);
            // 增加table里按钮点击事件
            addTableListener();
        }
        return templatePanel;
    }

    public void initTableData() {
        try {
            List<CodeTemplateVo> templateList = GlobalData.getTemplateList();
            templateModel.setTemplateList(templateList);
            table.updateUI();
        } catch (Exception e) {
            String msg = "initlize Data fauld";
            handException(msg, e);
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
                if (col == 8) {
                    CodeTemplateVo vo = templateModel.getTemplateList().get(row);
                    if (StringUtil.isEmpty(vo.getFileName())) {
                        JOptionPane.showMessageDialog(getTemplatePanel(), "该行没有选选择模板文件，请选择！", "提示",
                                                      JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    TemplateFileEditorWin win = new TemplateFileEditorWin(vo.getFileName());
                    win.setModal(true);
                    win.setBounds(getX() - 100, getY() - 50, win.getWidth(), win.getHeight());
                    win.setVisible(true);
                } else if (col == 2) {
                    JFileChooser jfc = new JFileChooser();// 文件选择器
                    jfc.setCurrentDirectory(new File(FileUtils.getTemplatePath()));
                    jfc.setFileSelectionMode(0);// 设定只能选择到文件
                    jfc.setFileFilter(new FileFilter() {

                        public boolean accept(File f) {
                            if (f.getName().endsWith(".vm") || f.isDirectory()) {
                                return true;
                            }
                            return false;
                        }

                        public String getDescription() {
                            return "vm文件(*.vm)";
                        }
                    });
                    int state = jfc.showOpenDialog(null);
                    if (state == 1) {
                        return;
                    } else {
                        try {
                            File file = jfc.getSelectedFile();
                            File templateFolder = new File(FileUtils.getTemplatePath());
                            if (!templateFolder.exists()) {
                                templateFolder.mkdirs();
                            }
                            File outFile = new File(FileUtils.getTemplatePath() + File.separator + file.getName());
                            boolean isUpLoad = true;
                            if (outFile.exists()) {
                                int status = JOptionPane.showConfirmDialog(getTemplatePanel(), "模板[" + file.getName()
                                                                                               + "]文件已存在是否覆盖?", "",
                                                                           JOptionPane.YES_NO_OPTION);
                                if (status == JOptionPane.NO_OPTION) {
                                    isUpLoad = false;
                                }
                            }
                            if (isUpLoad) {
                                FileUtils.uploadFile(templateFolder.getPath(), file.getName(), file);
                                templateModel.setValueAt(file.getName(), row, col);
                            }
                            table.updateUI();
                        } catch (Exception ex) {
                            String msg = "上传文件出错";
                            handException(msg, ex);
                        }
                    }

                } else if (col == 9) {
                    templateModel.removeRow(row);
                    table.updateUI();
                }

            }
        });
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            XmlParse<CodeTemplateVo> xmlParse = new XmlParse<CodeTemplateVo>(CodeTemplateVo.class);
            List<CodeTemplateVo> list = templateModel.getTemplateList();
            try {
                xmlParse.genVoToXmlFile(list, FileUtils.getConfigTemplatePath());
                if (parent != null) {
                    parent.refreshComBoBox();
                }
                JOptionPane.showMessageDialog(getTemplatePanel(), "保存模板成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                String msg = "保存模板出错";
                handException(msg, ex);
            }
        } else if (e.getSource() == addLineBtn) {
            CodeTemplateVo vo = new CodeTemplateVo();
            templateModel.addRow(vo);
            table.updateUI();
        }

    }

    public static void hideColumn(JTable table, int index) {
        TableColumn tc = table.getColumnModel().getColumn(index);
        tc.setMaxWidth(0);
        tc.setPreferredWidth(0);
        tc.setWidth(0);
        tc.setMinWidth(0);
        table.getTableHeader().getColumnModel().getColumn(index).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(0);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        TemplateConfigWin win = new TemplateConfigWin();
        win.setVisible(true);
    }

    private void handException(String msg, Exception e) {
        JOptionPane.showMessageDialog(getTemplatePanel(), msg + ":" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
        log.error(msg, e);
    }
}
