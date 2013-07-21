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
// Created on 2013-7-20

package org.richie.codeGen.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;


/**
 * @author elfkingw
 *
 */
public class UiTest {
    public static void main(final String args[]) {
        JFrame frame = new JFrame("JToolBar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);

        
        JButton button = new JButton("button");
        toolbar.add(button);
        toolbar.addSeparator();
        
        
        JPopupMenu popupMenu = new JPopupMenu();
        JMenu menu = new JMenu("menu1");
        JMenuItem item1 = new JMenuItem("item1");
        JMenuItem item2 = new JMenuItem("item2");
        menu.add(item1);
        popupMenu.add(menu);
        popupMenu.add(item2);
        toolbar.add(popupMenu);
        Container contentPane = frame.getContentPane();
        contentPane.add(toolbar, BorderLayout.NORTH);
        
        JTextArea textArea = new JTextArea();
        JScrollPane pane = new JScrollPane(textArea);
        contentPane.add(pane, BorderLayout.CENTER);
        
        frame.setSize(350, 150);
        frame.setVisible(true);
      }
}
