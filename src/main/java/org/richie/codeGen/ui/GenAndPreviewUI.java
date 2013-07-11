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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.richie.codeGen.core.exception.CGException;
import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.core.velocity.CodeGen;

/**
 * @author elfkingw
 */
public class GenAndPreviewUI extends JPanel implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JFrame            parent;
    private JTabbedPane       mainPanel;
    private JPanel            genPanel;
    private JScrollPane       previewPanel;
    private JTextArea         viewTextArea;
    private JButton           genBtn;

    public GenAndPreviewUI(JFrame parent){
        super();
        this.parent = parent;
        initLize();
    }

    public void initLize() {
        setSize(400, 400);
        setLayout(new BorderLayout());
        add(getMainPanel(), BorderLayout.CENTER);
        getGenBtn().addActionListener(this);
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
            genPanel.add(getGenBtn());
        }
        return genPanel;
    }

    public JScrollPane getPreviewPanel() {
        if (previewPanel == null) {
            previewPanel = new JScrollPane();
            previewPanel.setViewportView(getPreviewText());
        }
        return previewPanel;
    }

    public JButton getGenBtn() {
        if (genBtn == null) {
            genBtn = new JButton("生成");
        }
        return genBtn;
    }

    public JTextArea getPreviewText() {
        if (viewTextArea == null) {
            viewTextArea = new JTextArea();
        }
        return viewTextArea;
    }

    public void setCode(String codeText) {
        getPreviewText().setText(codeText);

    }

    public void previewCode() {
        String templaetName="add.js.vm";
        String templatesFolder= ClassLoader.getSystemResource("template").getPath();
//        String outFileFolder = ClassLoader.getSystemResource("out").getPath();
//        String outFileName = "add.js";
        try {
            Table table = new Table();
            table.setTableCode("FE_FUND");
            CodeGen.initTableVelocityContext(table);
            CodeGen.putToolsToVelocityContext("userName", "wanghua");
            String result = CodeGen.genCode(templaetName, templatesFolder+"");
            System.out.println(result);
            setCode(result);
            getMainPanel().setSelectedComponent(getPreviewPanel());
//            CodeGen.genCode(templaetName, templatesFolder,outFileFolder,outFileName);
        } catch (CGException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == genBtn) {
            previewCode();
        }

    }
}
