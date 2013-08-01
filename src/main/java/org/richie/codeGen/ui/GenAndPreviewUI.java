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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang.StringUtils;
import org.richie.codeGen.core.exception.CGException;
import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.core.util.DateUtil;
import org.richie.codeGen.core.util.StringUtil;
import org.richie.codeGen.core.velocity.CodeGen;
import org.richie.codeGen.database.util.DataTypeUtils;
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

    private TableSelectUI     parent;
    private JTabbedPane       mainPanel;
    private JPanel            genPanel;
    private JButton           genBtn;
    private JButton           previewBtn;
    private JButton           addLineBtn;
    private CodeTemplateModel complateModel;
    private JTable            table;
    private String[]          templateNames;
    private String[]          rootPathNames;
    private JComboBox         templateNameCom;
    private JComboBox         rootPathCom;
    private JTextArea         logTextArea;
    private JSplitPane        split;
    private JButton           clearLogBtn;

    public GenAndPreviewUI(TableSelectUI parent){
        super();
        this.parent = parent;
        initLize();
    }

    public void initLize() {
        setSize(400, 400);
        setLayout(new BorderLayout());
        add(getMainPanel(), BorderLayout.CENTER);
        initLastTemplate();// 加载最后一次退出的模板
        addTabbedPaneMouseListener();
    }

    public JTabbedPane getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JTabbedPane();
            mainPanel.setTabPlacement(JTabbedPane.BOTTOM);
            mainPanel.add(getGenPanel(), "生成代码");
        }
        return mainPanel;
    }

    public JPanel getGenPanel() {
        if (genPanel == null) {
            genPanel = new JPanel();
            genPanel.setLayout(new BorderLayout());
            genPanel.add(getCenterPanel(), BorderLayout.CENTER);
            JPanel northPanel = new JPanel();
            addLineBtn = new JButton("增加模板");
            addLineBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/add.gif")));
            addLineBtn.addActionListener(this);
            northPanel.add(addLineBtn);
            previewBtn = new JButton("生成预览");
            previewBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/preview1.gif")));
            previewBtn.addActionListener(this);
            northPanel.add(previewBtn);
            genBtn = new JButton("生成文件");
            genBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/file.gif")));
            genBtn.addActionListener(this);
            northPanel.add(genBtn);
            genPanel.add(northPanel, BorderLayout.SOUTH);
        }
        return genPanel;
    }

    public JSplitPane getCenterPanel() {
        JToolBar logToolBar = new JToolBar();
        logToolBar.setFloatable(false);
        clearLogBtn = new JButton("清除日志");
        clearLogBtn.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/next.gif")));
        clearLogBtn.addActionListener(this);
        clearLogBtn.addActionListener(this);
        logToolBar.add(clearLogBtn);
        logToolBar.addSeparator();
        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BorderLayout());
        JScrollPane textPanel = new JScrollPane();
        logTextArea = new JTextArea();
        logTextArea.setMinimumSize(new Dimension(1, 1));
        textPanel.setViewportView(logTextArea);
        logPanel.add(logToolBar, BorderLayout.NORTH);
        logPanel.add(textPanel, BorderLayout.CENTER);
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, getTemplatePanel(), logPanel);
        split.setContinuousLayout(false);
        split.setOneTouchExpandable(true);
        split.setDividerLocation(400);
        return split;
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
            log.error("界面初始", e);
        }
        templateNameCom = new JComboBox(templateNames);
        rootPathCom = new JComboBox(rootPathNames);
        // 隐藏编辑列
        TemplateConfigWin.hideColumn(table, 8);
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(1).setCellEditor(new DefaultCellEditor(templateNameCom));
        tcm.getColumn(3).setCellEditor(new DefaultCellEditor(rootPathCom));
        tcm.getColumn(7).setCellRenderer(new ButtonRenderer());
        tcm.getColumn(7).setCellEditor(new ButtonEditor());
        tcm.getColumn(9).setCellRenderer(new ButtonRenderer());
        tcm.getColumn(9).setCellEditor(new ButtonEditor());
        tcm.getColumn(0).setPreferredWidth(5);
        tcm.getColumn(1).setPreferredWidth(50);
        tcm.getColumn(2).setPreferredWidth(30);
        tcm.getColumn(3).setPreferredWidth(40);
        tcm.getColumn(4).setPreferredWidth(150);
        tcm.getColumn(5).setPreferredWidth(30);
        tcm.getColumn(6).setPreferredWidth(10);
        tcm.getColumn(7).setPreferredWidth(6);
        tcm.getColumn(9).setPreferredWidth(6);
        JScrollPane tablePanel = new JScrollPane(table);
        // 增加table里按钮点击事件
        addTableListener();
        return tablePanel;
    }

    public void setLog(String text) {
        if (!StringUtils.isEmpty(text)) {
            logTextArea.setText(logTextArea.getText() + text);
            split.setDividerLocation(250);
            split.updateUI();
        }
    }

    /**
     * clear log
     */
    public void clearLog() {
        logTextArea.setText("");
        split.setDividerLocation(700);
        split.updateUI();
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

    /**
     * 根据模板预览生成代码
     * 
     * @param vo
     */
    public void previewCode(CodeTemplateVo vo) {
        try {
            String templateName = vo.getFileName();
            if (StringUtil.isEmpty(templateName)) {
                JOptionPane.showMessageDialog(this, "模板文件不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            clearLog();
            setPackageContext(complateModel.getTemplateList());
            boolean isSuccess = initCustomerVelocityContext(vo);
            if (!isSuccess) {
                return;
            }
            Date startDate = new Date();
            Table table = parent.getTable();
            CodeGen.initTableVelocityContext(table);
            String codeText = CodeGen.genCode(templateName, FileUtils.getTemplatePath());
            String fileName = null;
            if (table != null) {
                fileName = parent.getTable().getClassName() + vo.getSuffix();
            } else {
                fileName = vo.getSuffix();
            }
            logGenFile(startDate, fileName);
            addPreviewTablePanel(fileName, codeText);
            if (vo.getIsGenSubTable() != null && vo.getIsGenSubTable() && table != null
                && table.getOneToManyTables() != null) {// 如果生成子表
                startDate = new Date();
                CodeGen.initTableVelocityContext(table.getOneToManyTables());
                codeText = CodeGen.genCode(templateName, FileUtils.getTemplatePath());
                fileName = table.getOneToManyTables().getClassName() + vo.getSuffix();
                logGenFile(startDate, fileName);
                addPreviewTablePanel(fileName, codeText);
            }
        } catch (CGException e) {
            handException("预览出错", e);
        } catch (Exception e) {
            handException("预览出错", e);
        }
    }

    private void logGenFile(Date startDate, String fileName) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder("");
        try {
            File logFile = new File(FileUtils.CONFIG_ROOT_PATH + File.separator + "velocity.log");
            if (logFile.exists()) {
                reader = new BufferedReader(new FileReader(logFile));
                String line = null;
                boolean isStart = false;
                while ((line = reader.readLine()) != null) {
                    if (!isStart) {
                        String time = line.substring(0, 23);
                        if (time.startsWith("20")) {
                            String startDateStr = DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:ss,SSS");
                            if (time.compareTo(startDateStr) >= 0) {
                                isStart = true;
                            }
                        }
                    }
                    if (isStart) {
                        sb.append(line + "\n");
                    }
                }
            }
        } catch (Exception e) {
            log.error("读取velocity.log失败！", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (sb.length() > 0) {
            setLog("===================文件【" + fileName + "】的日志=======================\n" + sb.toString());
        }
    }

    /**
     * 增加文件预览卡片页面
     * 
     * @param fileName
     * @param fileContent
     */
    private void addPreviewTablePanel(String fileName, String fileContent) {
        final JScrollPane content = new JScrollPane();
        JTextArea viewTextArea = new JTextArea();
        viewTextArea.setText(fileContent);
        content.setViewportView(viewTextArea);
        JPanel tab = new JPanel();
        tab.setOpaque(false);
        JLabel tabLabel = new JLabel(fileName);
        ImageIcon closeXIcon = new ImageIcon(ClassLoader.getSystemResource("resources/images/close.gif"));
        JButton tabCloseButton = new JButton(closeXIcon);
        tabCloseButton.setToolTipText("close");
        tabCloseButton.setBorder(null);
        tabCloseButton.setContentAreaFilled(false);
        tabCloseButton.setPreferredSize(new Dimension(closeXIcon.getIconWidth(), closeXIcon.getIconHeight()));
        tabCloseButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int closeTabNumber = mainPanel.indexOfComponent(content);
                mainPanel.removeTabAt(closeTabNumber);
            }
        });

        tab.add(tabLabel, BorderLayout.WEST);
        tab.add(tabCloseButton, BorderLayout.EAST);

        mainPanel.addTab(null, content);
        mainPanel.setTabComponentAt(mainPanel.getTabCount() - 1, tab);
        mainPanel.setSelectedComponent(content);
    }

    /**
     * 根据模板生成文件
     */
    private void genCodeFiles(boolean isPreview) {
        try {
            List<CodeTemplateVo> list = complateModel.getTemplateList();
            if (!isTemplateValidate(list)) {
                return;
            }
            clearLog();
            StringBuilder sb = new StringBuilder();
            sb.append("成功生成如下文件：\n");
            setPackageContext(list);
            for (int i = 0; i < list.size(); i++) {
                CodeTemplateVo vo = list.get(i);
                if (vo.getIsSelected() == null || !vo.getIsSelected()) continue;
                boolean isSuccess = initCustomerVelocityContext(vo);
                if (!isSuccess) {
                    return;
                }
                String templateName = vo.getFileName();
                String outFilePath = GlobalData.getOutFilePathByName(vo.getOutFilePathRoot()) + File.separator
                                     + vo.getOutFilePath().replace("\u002E", File.separator);
                String name = "";
                if (parent.getTable() != null) {
                    name = parent.getTable().getClassName();
                }
                Date startDate = new Date();
                String fileName = name + vo.getSuffix();
                Table table = parent.getTable();
                CodeGen.initTableVelocityContext(table);
                if (isPreview) {
                    String fileContent = CodeGen.genCode(templateName, FileUtils.getTemplatePath());
                    addPreviewTablePanel(fileName, fileContent);
                } else {
                    CodeGen.genCode(templateName, FileUtils.getTemplatePath(), outFilePath, fileName);
                    sb.append(outFilePath + File.separator + fileName + "\n");
                }
                logGenFile(startDate, fileName);
                boolean isGenSubTable = vo.getIsGenSubTable() != null ? vo.getIsGenSubTable() : false;
                if (isGenSubTable && table != null && table.getOneToManyTables() != null) {// 如果生成子表
                    startDate = new Date();
                    CodeGen.initTableVelocityContext(table.getOneToManyTables());
                    fileName = table.getOneToManyTables().getClassName() + vo.getSuffix();
                    if (isPreview) {
                        String fileContent = CodeGen.genCode(templateName, FileUtils.getTemplatePath());
                        addPreviewTablePanel(fileName, fileContent);
                    } else {
                        CodeGen.genCode(templateName, FileUtils.getTemplatePath(), outFilePath, fileName);
                        sb.append(outFilePath + File.separator + fileName + "\n");
                    }
                    logGenFile(startDate, fileName);
                }
            }
            if (!isPreview) {
                JOptionPane.showMessageDialog(this, sb.toString(), "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (CGException e) {
            handException("生成文件出错", e);
        } catch (Exception e) {
            handException("生成文件出错", e);
        }
    }

    private void setTableValue() {
        if (parent.getTable() != null) {
            parent.getTable().setName(parent.mainTableName.getText());
            parent.getTable().setExtension1(parent.mainExtension1.getText());
            parent.getTable().setExtension2(parent.mainExtension2.getText());
            Table subTable = parent.getTable().getOneToManyTables();
            if (subTable != null) {
                subTable.setName(parent.subTableName.getText());
                subTable.setExtension1(parent.subExtension1.getText());
                subTable.setExtension2(parent.subExtension2.getText());
            }
        }
    }

    private void setPackageContext(List<CodeTemplateVo> list) {
        for (CodeTemplateVo vo : list) {
            String fileName = vo.getFileName();
            if (!StringUtils.isEmpty(fileName)) {
                String packageKey = fileName.substring(0, fileName.length() - 3) + "_package";
                CodeGen.putToolsToVelocityContext(packageKey, vo.getOutFilePath().replace("\\", "."));
            }
        }
    }

    private boolean initCustomerVelocityContext(CodeTemplateVo vo) throws Exception {
        Table selectedTable = parent.getTable();
        if (selectedTable == null) {
            int status = JOptionPane.showConfirmDialog(this, "没有选择表是否继续？", "提示", JOptionPane.OK_CANCEL_OPTION);
            if (status != JOptionPane.OK_OPTION) {
                return false;
            }
        }
        setTableValue();
        // 根据数据类型的配置来设置表字段的代码类型
        DataTypeUtils.setDataType(selectedTable);
        if (selectedTable != null && selectedTable.getOneToManyTables() != null) {
            DataTypeUtils.setDataType(selectedTable.getOneToManyTables());
        }
        Map<String, Object> map = GlobalData.getConstentMap();
        CodeGen.initCustomerVelocityContext(map);
        return true;
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
            genCodeFiles(false);
        } else if (e.getSource() == addLineBtn) {
            CodeTemplateVo vo = new CodeTemplateVo();
            complateModel.addRow(vo);
            table.updateUI();
        } else if (e.getSource() == previewBtn) {
            genCodeFiles(true);
        } else if (e.getSource() == clearLogBtn) {
            clearLog();
        }

    }

    /**
     * 增加tab面片事件 双击事件时，关闭所双击的tab
     */
    private void addTabbedPaneMouseListener() {
        mainPanel.addMouseListener(new MouseListener() {

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
                if (e.getClickCount() == 2) {
                    int closeTabNumber = mainPanel.getSelectedIndex();
                    if (closeTabNumber != 0) {
                        mainPanel.removeTabAt(closeTabNumber);
                    }
                }

            }
        });
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
                } else if (col == 7) {
                    List<CodeTemplateVo> list = complateModel.getTemplateList();
                    if (list != null && list.size() > 0) {
                        CodeTemplateVo vo = list.get(row);
                        previewCode(vo);
                    }
                } else if (col == 9) {
                    complateModel.removeRow(row);
                    table.updateUI();
                }
            }
        });
    }

}
