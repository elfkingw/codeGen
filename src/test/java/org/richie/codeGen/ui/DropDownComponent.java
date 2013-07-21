package org.richie.codeGen.ui;

//DropDownComponent.java

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;

import org.richie.codeGen.database.Constants;

public class DropDownComponent extends JComponent implements ActionListener {

    protected JComponent drop_down_comp;

    protected JComponent visible_comp;

    protected JMenuBar   mb;

    protected JWindow    popup;

    protected JPopupMenu pop   = new JPopupMenu();

    private JMenuItem[]  items = { new JMenuItem(Constants.DATABASE_TYPE_DB2),
            new JMenuItem(Constants.DATABASE_TYPE_ORACLE), new JMenuItem(Constants.DATABASE_TYPE_MYSQL),
            new JMenuItem(Constants.DATABASE_TYPE_MSSQL), new JMenuItem(Constants.DATABASE_TYPE_INFORMIX) };

    protected JButton    arrow;

    public DropDownComponent(JComponent vcomp, JComponent ddcomp){

        drop_down_comp = ddcomp;
        visible_comp = vcomp;
        arrow = (JButton) visible_comp;
        Insets insets = arrow.getMargin();
        arrow.setMargin(new Insets(insets.top, 1, insets.bottom, 1));
        arrow.addActionListener(this);

        for (int i = 0; i < items.length; i++) {
            items[i].addActionListener(this);
            pop.add(items[i]);
        }

        setupLayout();
    }

    protected void setupLayout() {
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gbl);

        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        gbl.setConstraints(visible_comp, c);
        add(visible_comp);

        c.weightx = 0;
        c.gridx++;
        gbl.setConstraints(arrow, c);
        add(arrow);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == arrow) {
            pop.show(arrow, arrow.getWidth() - visible_comp.getPreferredSize().width, arrow.getHeight());
        } else if (evt.getSource() instanceof JMenuItem) {
            JMenuItem mi = (JMenuItem) evt.getSource();
            System.out.println(mi.getText());
        }
    }
}
