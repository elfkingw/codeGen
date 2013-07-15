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
// Created on 2013-7-12

package org.richie.codeGen.ui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * @author elfkingw
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {  
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ButtonRenderer() {  
        setOpaque(true);  
    }  
  
    public Component getTableCellRendererComponent(JTable table, Object value,  
            boolean isSelected, boolean hasFocus, int row, int column) {  
        if (isSelected) {  
            setForeground(table.getSelectionForeground());  
            setBackground(table.getSelectionBackground());  
        } else {  
            setForeground(table.getForeground());  
            setBackground(UIManager.getColor("UIManager"));  
        }  
        setText((value == null) ? "" : value.toString());  
        return this;  
    }  
}  