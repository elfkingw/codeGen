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
// Created on 2013-7-9

package org.richie.codeGen.ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringUtils;
import org.richie.codeGen.Version;
import org.richie.codeGen.core.exception.CGException;
import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.core.model.Column;
import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.database.Constants;
import org.richie.codeGen.database.DatabaseReader;
import org.richie.codeGen.database.ReaderFactory;
import org.richie.codeGen.ui.configui.ConstantConfigWin;
import org.richie.codeGen.ui.configui.BaseDataConfigWin;
import org.richie.codeGen.ui.configui.TemplateConfigWin;
import org.richie.codeGen.ui.model.LastOperateVo;
import org.richie.codeGen.ui.model.TableTreeNode;
import org.richie.codeGen.ui.util.FileUtils;
import org.richie.codeGen.ui.util.JarFileUtils;

/**
 * @author elfkingw
 */
public class CodeGenMainUI extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static Log log = LogFacotry.getLogger(CodeGenMainUI.class);

    private JTree tree;
    private List<Table> tableList;
    /**
     * 左边面板
     */

    private JPanel westPanel;
    // 中间面板
    private JTabbedPane centerPanel;
    // 树面板
    private JScrollPane treePanel;
    // 生成代码面板
    private GenAndPreviewUI genAndPreviewPanel;
    // 生成代码面板
    private TableSelectUI tableSelectPanel;
    private JTextField filterField;

    /**
     * pdm文件菜单项
     */
    private JMenuItem openPdmFileItem;
    private JMenuItem openExamplePdmFileItem;
    // 模板配置菜单项
    private JMenuItem templateConfigItem;
    /**
     * 常量配置菜单项
     */
    private JMenuItem consConfigItem;
    /**
     * 数据类型菜单项
     */
    private JMenuItem dataTypeConfigItem;
    // 怎么编写模板
    private JMenuItem helpDoc;
    // 关于菜单项
    private JMenuItem miAbout;

    public CodeGenMainUI() {
        initlize();
    }

    private void initlize() {
        setTitle("代码生成器");
        setBounds(120, 80, 1200, 550);
        setDefaultCloseOperation(3);
        setLayout(new BorderLayout(5, 5));
        // 初始化MenuBar
        initMenuBar();
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getWestPanel(), getCenterPanel());
        split.setContinuousLayout(false);
        split.setOneTouchExpandable(true);
        split.setDividerLocation(170);
        add(split, BorderLayout.CENTER);
        //打开最后一次打开的pdm文件
        openLastPdmFile();
        addCloseListener();
        this.setIconImage(new ImageIcon(ClassLoader.getSystemResource("resources/images/logo.jpg")).getImage());
    }

    public void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        menuBar.add(fileMenu);
        openPdmFileItem = new JMenuItem("打开dpm");
        openPdmFileItem.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/pdm.png")));
        fileMenu.add(openPdmFileItem);
        openExamplePdmFileItem = new JMenuItem("打开样例dpm");
        openExamplePdmFileItem.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/pdm.png")));
        fileMenu.add(openExamplePdmFileItem);
        JMenu configMenu = new JMenu("系统配置");
        menuBar.add(configMenu);
        templateConfigItem = new JMenuItem("模板配置");
        templateConfigItem.addActionListener(this);
        configMenu.add(templateConfigItem);
        consConfigItem = new JMenuItem("常量配置");
        consConfigItem.addActionListener(this);
        configMenu.add(consConfigItem);
        dataTypeConfigItem = new JMenuItem("数据类型配置");
        dataTypeConfigItem.addActionListener(this);
        configMenu.add(dataTypeConfigItem);
        JMenu mnHelp = new JMenu("帮助");
        menuBar.add(mnHelp);
        helpDoc = new JMenuItem("帮助文档");
        helpDoc.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/images/docs.gif")));
        mnHelp.add(helpDoc);
        helpDoc.addActionListener(this);
        miAbout = new JMenuItem("关于");
        mnHelp.add(miAbout);
        miAbout.addActionListener(this);
        openPdmFileItem.addActionListener(this);
        openExamplePdmFileItem.addActionListener(this);
        this.setJMenuBar(menuBar);
    }

    public JPanel getWestPanel() {
        if (westPanel == null) {
            westPanel = new JPanel();
            westPanel.setLayout(new BorderLayout());
            JLabel filterLable = new JLabel("过滤");
            JPanel filterPanel = new JPanel();
            filterPanel.add(filterLable);
            filterField = new JTextField(10);
            Document doc = filterField.getDocument();
            doc.addDocumentListener(new TextDocumentListener());
            filterPanel.add(filterField);
            filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            westPanel.setPreferredSize(new Dimension(250, 650));// 关键代码,设置JPanel的大
            westPanel.add(filterPanel, BorderLayout.NORTH);
            westPanel.add(getTreepanel(), BorderLayout.CENTER);
        }
        return westPanel;
    }

    public JTabbedPane getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JTabbedPane();
            centerPanel.add(getTableSelectPanel(), "表选择区");
            centerPanel.add(getGenAndPreviewPanel(), "代码生成区");
        }
        return centerPanel;
    }

    public TableSelectUI getTableSelectPanel() {
        if (tableSelectPanel == null) {
            tableSelectPanel = new TableSelectUI(this);
        }
        return tableSelectPanel;
    }

    public GenAndPreviewUI getGenAndPreviewPanel() {
        if (genAndPreviewPanel == null) {
            genAndPreviewPanel = new GenAndPreviewUI(getTableSelectPanel());
        }
        return genAndPreviewPanel;
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

    private void doMouseClicked(MouseEvent me) {
        TableTreeNode node = (TableTreeNode) tree.getLastSelectedPathComponent();
        if (node != null && !node.isRoot() && getCenterPanel().getSelectedIndex() == 0) {
            if (node.getTable() != null) {
                // 克隆一份
                Table table = (Table) node.getTable().clone();
                getTableSelectPanel().initTable(table);
            }
        }

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

    private void initTree(String filePath) {
        try {
            DatabaseReader reader = ReaderFactory.getReaderInstance(Constants.DATABASE_READER_TYPE_PDM,
                    Constants.DATABASE_TYPE_MYSQL, filePath);
            tableList = reader.getTables();
            tree = initTreeData(tableList);
            treePanel.setViewportView(tree);
            getTreepanel().updateUI();
        } catch (CGException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * @param list
     * @return
     */
    private JTree initTreeData(List<Table> list) {
        TableTreeNode root = null;
        if (list == null || list.size() == 0) {
            root = new TableTreeNode("请导入文件");
        } else {
            root = new TableTreeNode(list.get(0).getDataBaseCode() + ":" + list.get(0).getDataBaseName());
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
                doMouseClicked(me);
            }

        });
        return newTree;
    }

    /**
     * 打开最后一次打开过的pdm
     */
    private void openLastPdmFile() {
        LastOperateVo lastOperateVo = null;
        try {
            lastOperateVo = GlobalData.getLastOperateVo();
            //如果第一次打开该工具自动载入自带pdm例子文件
            if (lastOperateVo == null || StringUtils.isEmpty(lastOperateVo.getPdmFilePath())) {
                initTree("example.pdm");
            } else {
                String pdmFilePath = lastOperateVo.getPdmFilePath();
                File f = new File(pdmFilePath);
                if (f.exists()) {
                    initTree(f.getAbsolutePath());
                } else {
                    initTree("example.pdm");
                }
            }
        } catch (Exception e1) {
            log.error("获取最后操作文xml件失败", e1);
        }
    }

    public void addCloseListener() {
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    GlobalData.saveLastConfig();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    log.error("保存最后打开pdm文件失败！", e1);
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {
                log.info("window is closed");
            }

            @Override
            public void windowActivated(WindowEvent e) {

            }
        });
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openPdmFileItem) {
            openPdmFile();
        } else if (e.getSource() == openExamplePdmFileItem) {
            initTree("example.pdm");
        } else if (e.getSource() == templateConfigItem) {
            TemplateConfigWin win = new TemplateConfigWin(getGenAndPreviewPanel());
            win.setModal(true);
            win.setBounds(this.getX() + 100, this.getY() + 30, win.getWidth(), win.getHeight());
            win.setVisible(true);
        } else if (e.getSource() == consConfigItem) {
            ConstantConfigWin win = new ConstantConfigWin(getGenAndPreviewPanel());
            win.setModal(true);
            win.setBounds(this.getX() + 250, this.getY() + 30, win.getWidth(), win.getHeight());
            win.setVisible(true);
        } else if (e.getSource() == dataTypeConfigItem) {
            BaseDataConfigWin win = new BaseDataConfigWin();
            win.setModal(true);
            win.setBounds(this.getX() + 250, this.getY() + 30, win.getWidth(), win.getHeight());
            win.setVisible(true);
        } else if (e.getSource() == helpDoc) {
            try {
                Desktop.getDesktop().open(new File(FileUtils.getHelpPath()));
            } catch (Exception e1) {
                log.error("打开帮助文档失败", e1);
            }
        } else if (e.getSource() == miAbout) {
            JOptionPane.showMessageDialog(this, "版本：codeGen-" + Version.getVersionNumber() + "\nelfkingw版权所有  elfkingw@gmail.com", "关于",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     *
     */
    private void openPdmFile() {
        JFileChooser jfc = new JFileChooser();// 文件选择器
        LastOperateVo lastOperateVo = null;
        try {
            lastOperateVo = GlobalData.getLastOperateVo();
        } catch (Exception e2) {
            log.error("获取最后一次打开文件失败", e2);
        }
        if (lastOperateVo != null && lastOperateVo.getPdmFilePath() != null) {
            jfc.setCurrentDirectory(new File(lastOperateVo.getPdmFilePath()));// 文件选择器的初始目录定为d盘
        } else {
            jfc.setCurrentDirectory(new File("d:\\"));// 文件选择器的初始目录定为d盘

        }
        jfc.setFileSelectionMode(0);// 设定只能选择到文件
        jfc.setFileFilter(new FileFilter() {

            public boolean accept(File f) {
                if (f.getName().endsWith(".pdm") || f.isDirectory()) {
                    return true;
                }
                return false;
            }

            public String getDescription() {
                return "pdm文件(*.pdm)";
            }
        });
        int state = jfc.showOpenDialog(null);
        if (state == 1) {
            return;
        } else {
            File f = jfc.getSelectedFile();
            initTree(f.getAbsolutePath());
            try {
                if (lastOperateVo == null) {
                    lastOperateVo = new LastOperateVo();
                }
                lastOperateVo.setPdmFilePath(f.getAbsolutePath());
                GlobalData.setLastOperateVo(lastOperateVo);
//                GlobalData.saveLastOperateVo();
            } catch (Exception e1) {
                log.error("保存最后一次代码pdm路径出错", e1);
            }
        }
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            CodeGenMainUI ui = new CodeGenMainUI();
            ui.setVisible(true);
            ui.getTableSelectPanel().setDividerLocation();
        } catch (Exception e) {
            log.error("初始化界面失败", e);
            e.printStackTrace();
        }
        // ui.pack();
    }
}
