package org.richie.codeGen.ui;

//DropDownTest.java

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class DropDownTest extends JFrame {

    final JButton           status   = new JButton("Color");

    final JPanel            panel    = new JPanel();

    final DropDownComponent dropdown = new DropDownComponent(status, panel);

    public DropDownTest(){
        super("Drop Down Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(new BorderLayout());

        JToolBar bar = new JToolBar();
        bar.add(dropdown);

        getContentPane().add("North", bar);
        getContentPane().add("Center", new JLabel("Drop Down Test"));

        pack();
        setSize(300, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DropDownTest();
    }
}
