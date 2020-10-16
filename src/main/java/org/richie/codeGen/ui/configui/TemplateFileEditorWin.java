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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.richie.codeGen.core.exception.CGException;
import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.ui.util.FileUtils;
import org.richie.codeGen.ui.util.JarFileUtils;

/**
 * @author elfkingw
 */
public class TemplateFileEditorWin extends JDialog implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Log               log              = LogFacotry.getLogger(TemplateFileEditorWin.class);
    private String            fileName;
    private JScrollPane       previewPanel;
    private JTextArea         viewTextArea;
    private JPanel            buttonPanel;
    private JButton           saveBtn;
    private JButton           cancelBtn;

    public TemplateFileEditorWin(String fileName){
        super();
        this.fileName = fileName;
        initlize();
    }

    private void initlize() {
        setSize(1000, 600);
        setTitle(fileName);
        setLayout(new BorderLayout());
        add(getPreviewPanel(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.SOUTH);
        try {
            initFileContent();
        } catch (Exception e) {
            handException("读取文件失败", e);
        }
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

    public JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            saveBtn = new JButton("保存");
            saveBtn.addActionListener(this);
            cancelBtn = new JButton("取消");
            cancelBtn.addActionListener(this);
            buttonPanel.add(saveBtn);
            buttonPanel.add(cancelBtn);
        }
        return buttonPanel;
    }

    private void initFileContent() throws Exception {
        File file = new File(JarFileUtils.TEMP_TEMPLATE_FILE_PATH+ File.separator + fileName);
        InputStream inputStream =null;
        //先从临时文件夹读取
        if (file.exists()) {
            inputStream = new FileInputStream(file);
        } else {
            //再从jar包中文件夹读取
            inputStream = JarFileUtils.class.getResourceAsStream("/resources/template/"+fileName);
        }
        if (inputStream == null) {
            throw new Exception("模板文件【" + fileName + "】不存在！");
        }
        viewTextArea.setText(FileUtils.readFile(inputStream));

    }

    private void saveFile() throws Exception {
//        File file = new File(JarFileUtils.TEMP_TEMPLATE_FILE_PATH+ File.separator + fileName);
//        if (!file.exists()) {
//            throw new Exception("模板文件【" + fileName + "】不存在！");
//        }
        FileUtils.witerFile(JarFileUtils.TEMP_TEMPLATE_FILE_PATH, fileName, viewTextArea.getText());
        this.setVisible(false);
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            try {
                saveFile();
            } catch (Exception e1) {
                handException("保存失败", e1);
            }
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    private void handException(String msg, Exception e) {
        JOptionPane.showMessageDialog(this, msg + ":" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
        log.error(msg, e);
    }

    public static void main(String[] args) {
        TemplateFileEditorWin win = new TemplateFileEditorWin("et");
        win.setVisible(true);
    }
}
