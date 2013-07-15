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
// Created on 2013-7-11

package org.richie.codeGen.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.richie.codeGen.core.exception.CGException;
import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.core.util.StringUtil;
import org.richie.codeGen.core.velocity.CodeGen;
import org.richie.codeGen.ui.configui.TemplateConfigWin;
import org.richie.codeGen.ui.model.CodeTemplateModel;
import org.richie.codeGen.ui.model.CodeTemplateVo;
import org.richie.codeGen.ui.util.FileUtils;
import org.richie.codeGen.ui.util.XmlParse;

/**
 * @author elfkingw
 */
public class GenAndPreviewUI extends JPanel implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Log               log              = LogFacotry.getLogger(GenAndPreviewUI.class);

    private JFrame            parent;
    private JTabbedPane       mainPanel;
    private JPanel            genPanel;
    private JScrollPane       previewPanel;
    private JTextArea         viewTextArea;
    private JButton           genBtn;
    private JButton           addLineBtn;
    private CodeTemplateModel complateModel;
    private JTable            table;
    private String[]          templateNames;
    private String[]          rootPathNames;
    private JComboBox         templateNameCom;
    private JComboBox         rootPathCom;

    public GenAndPreviewUI(JFrame parent){
        super();
        this.parent = parent;
        initLize();
    }

    public void initLize() {
        setSize(400, 400);
        setLayout(new BorderLayout());
        add(getMainPanel(), BorderLayout.CENTER);
        initLastTemplate();// 加载最后一次推出的模板
    }

    public JTabbedPane getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JTabbedPane();
            mainPanel.setTabPlacement(JTabbedPane.BOTTOM);
            mainPanel.add(getGenPanel(), "生成代码");
            mainPanel.add(getPreviewPanel(), "预览");
        }
        return mainPanel;
    }

    public JPanel getGenPanel() {
        if (genPanel == null) {
            genPanel = new JPanel();
            genPanel.setLayout(new BorderLayout());
            genPanel.add(getTemplatePanel(), BorderLayout.CENTER);
            JPanel northPanel = new JPanel();
            addLineBtn = new JButton("增加模板");
            addLineBtn.addActionListener(this);
            northPanel.add(addLineBtn);
            genBtn = new JButton("生成文件");
            genBtn.addActionListener(this);
            northPanel.add(genBtn);
            genPanel.add(northPanel, BorderLayout.SOUTH);
        }
        return genPanel;
    }

    public JScrollPane getTemplatePanel() {
        complateModel = new CodeTemplateModel(false);
        table = new JTable(complateModel);
        table.setBackground(Color.white);
        table.setSelectionBackground(Color.white);
        table.setSelectionForeground(Color.black);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);// 表格列不可移动
        table.setFont(new Font("Dialog", 0, 13));
        table.setRowHeight(23);
        try {
            templateNames = GlobalData.getTemplateNames();
            rootPathNames = GlobalData.getOutFileRootNames();
        } catch (Exception e) {
            e.printStackTrace();
        }
        templateNameCom = new JComboBox(templateNames);
        rootPathCom = new JComboBox(rootPathNames);
        // 隐藏编辑列
        TemplateConfigWin.hideColumn(table, 7);
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(1).setCellEditor(new DefaultCellEditor(templateNameCom));
        tcm.getColumn(3).setCellEditor(new DefaultCellEditor(rootPathCom));
        tcm.getColumn(6).setCellRenderer(new ButtonRenderer());
        tcm.getColumn(6).setCellEditor(new ButtonEditor());
        tcm.getColumn(8).setCellRenderer(new ButtonRenderer());
        tcm.getColumn(8).setCellEditor(new ButtonEditor());
        tcm.getColumn(0).setPreferredWidth(5);
        tcm.getColumn(1).setPreferredWidth(50);
        tcm.getColumn(2).setPreferredWidth(30);
        tcm.getColumn(3).setPreferredWidth(40);
        tcm.getColumn(4).setPreferredWidth(150);
        tcm.getColumn(5).setPreferredWidth(15);
        tcm.getColumn(6).setPreferredWidth(10);
        tcm.getColumn(8).setPreferredWidth(10);
        JScrollPane tablePanel = new JScrollPane(table);
        // 增加table里按钮点击事件
        addTableListener();
        return tablePanel;
    }

    public JScrollPane getPreviewPanel() {
        if (previewPanel == null) {
            previewPanel = new JScrollPane();
            previewPanel.setViewportView(getPreviewText());
        }
        return previewPanel;
    }

    public JTextArea getPreviewText() {
        if (viewTextArea == null) {
            viewTextArea = new JTextArea();
        }
        return viewTextArea;
    }

    public void refreshComBoBox() {
        try {
            templateNames = GlobalData.getTemplateNames();
            rootPathNames = GlobalData.getOutFileRootNames();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        templateNameCom = new JComboBox(templateNames);
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(1).setCellEditor(new DefaultCellEditor(templateNameCom));
        templateNameCom.updateUI();

        rootPathCom = new JComboBox(rootPathNames);
        TableColumnModel tcm1 = table.getColumnModel();
        tcm1.getColumn(3).setCellEditor(new DefaultCellEditor(rootPathCom));
        rootPathCom.updateUI();
    }

    public void previewCode(CodeTemplateVo vo) {
        try {
            String templateName = vo.getFileName();
            if (StringUtil.isEmpty(templateName)) {
                JOptionPane.showMessageDialog(this, "模板文件不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            CodeGen.putToolsToVelocityContext("package", vo.getOutFilePath());
            CodeGen.putToolsToVelocityContext("userName", "elfkingw");
            CodeGen.initCustomerVelocityContext(map);
            String codeText = CodeGen.genCode(templateName, FileUtils.getTemplatePath());
            getPreviewText().setText(codeText);
            getMainPanel().setSelectedComponent(getPreviewPanel());
        } catch (CGException e) {
            handException("预览出错", e);
        } catch (Exception e) {
            handException("预览出错", e);
        }
    }

    /**
     * 根据模板生成文件
     */
    private void genCodeFiles() {
        try {
            List<CodeTemplateVo> list = complateModel.getTemplateList();
            if (!isTemplateValidate(list)) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("成功生成如下文件：/n");
            for (int i = 0; i < list.size(); i++) {
                CodeTemplateVo vo = list.get(i);
                if (!vo.getIsSelected()) continue;
                String templateName = vo.getFileName();
                Map<String, Object> map = new HashMap<String, Object>();
                CodeGen.putToolsToVelocityContext("package", vo.getOutFilePath());
                CodeGen.initCustomerVelocityContext(map);
                String outFilePath = GlobalData.getOutFilePathByName(vo.getOutFilePathRoot()) + File.separator
                                     + vo.getOutFilePath().replace("\u002E", File.separator);
                // TODO
                CodeGen.genCode(templateName, FileUtils.getTemplatePath(), outFilePath, vo.getSuffix());
                sb.append(outFilePath + File.separator + vo.getSuffix() + "/n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (CGException e) {
            handException("预览出错", e);
        } catch (Exception e) {
            handException("预览出错", e);
        }
    }

    private boolean isTemplateValidate(List<CodeTemplateVo> list) {
        StringBuilder msg = new StringBuilder();
        int selectedCount = 0;
        for (int i = 0; i < list.size(); i++) {
            CodeTemplateVo vo = list.get(i);
            if (vo == null || vo.getIsSelected() == null || !vo.getIsSelected()) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            if (StringUtil.isEmpty(vo.getFileName())) {
                sb.append(",【模板文件】");
            }
            if (StringUtil.isEmpty(vo.getOutFilePathRoot())) {
                sb.append(",【输出文件根目录】");
            }
            if (StringUtil.isEmpty(vo.getOutFilePath())) {
                sb.append(",【输出包】");
            }
            if (StringUtil.isEmpty(vo.getSuffix())) {
                sb.append(",【文件后缀】");
            }
            if (!StringUtil.isEmpty(sb.toString())) {
                msg.append("第" + (i + 1) + "行" + sb.toString().substring(1) + "不能为空\n");
            }
            selectedCount++;
        }
        if (!StringUtil.isEmpty(msg.toString())) {
            JOptionPane.showMessageDialog(this, msg.toString(), "提示", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (selectedCount == 0) {
            JOptionPane.showMessageDialog(this, "请勾选要生成文件的模板！", "提示", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == genBtn) {
            genCodeFiles();
        } else if (e.getSource() == addLineBtn) {
            CodeTemplateVo vo = new CodeTemplateVo();
            complateModel.addRow(vo);
            table.updateUI();
        }

    }

    public void initLastTemplate() {
        try {
            XmlParse<CodeTemplateVo> xmlParse = new XmlParse<CodeTemplateVo>(CodeTemplateVo.class);
            List<CodeTemplateVo> list = xmlParse.parseXmlFileToVo(FileUtils.getLastTemplatePath());
            complateModel.setTemplateList(list);
            table.updateUI();
        } catch (Exception ex) {
            String msg = "保存模板出错";
            log.error(msg, ex);
        }
    }

    public void saveLastTemplate() {
        List<CodeTemplateVo> list = complateModel.getTemplateList();
        if (list == null) return;
        XmlParse<CodeTemplateVo> xmlParse = new XmlParse<CodeTemplateVo>(CodeTemplateVo.class);
        try {
            xmlParse.genVoToXmlFile(list, FileUtils.getLastTemplatePath());
        } catch (Exception ex) {
            String msg = "保存模板出错";
            log.error(msg, ex);
        }
    }

    private void handException(String msg, Exception e) {
        JOptionPane.showMessageDialog(this, msg + ":" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
        log.error(msg, e);
    }

    /**
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
                if (col == 1) {
                    List<CodeTemplateVo> list = complateModel.getTemplateList();
                    CodeTemplateVo vo = list.get(row);
                    try {
                        vo = GlobalData.getTemplateByName(vo.getTemplateName());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    table.updateUI();
                } else if (col == 6) {
                    List<CodeTemplateVo> list = complateModel.getTemplateList();
                    if (list != null && list.size() > 0) {
                        CodeTemplateVo vo = list.get(row);
                        previewCode(vo);
                    }
                } else if (col == 8) {
                    complateModel.removeRow(row);
                    table.updateUI();
                }
            }
        });
    }

}
