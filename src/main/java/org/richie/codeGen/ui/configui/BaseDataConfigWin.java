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

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

/**
 * 数据类型数据，界面控件数据配置
 * 
 * @author elfkingw
 */
public class BaseDataConfigWin extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTabbedPane       centerPanel;

    public BaseDataConfigWin(){

        super();
        initLize();
    }

    public void initLize() {
        setTitle("数据类型设置");
        setSize(560, 400);
        add(getCenterPanel());
    }

    public JTabbedPane getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JTabbedPane();
            centerPanel.add(new DataTypeConfigPanel(), "数据类型设置");
            centerPanel.add(new UIComponentConfigPanel(this), "界面控件设置");
        }
        return centerPanel;
    }

    public DataTypeConfigPanel getDataTypeConfigPanel() {
        return new DataTypeConfigPanel();
    }
}
