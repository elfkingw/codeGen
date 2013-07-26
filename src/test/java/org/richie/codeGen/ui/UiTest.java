package org.richie.codeGen.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;

/** 
 * Swing 缁勪欢娴嬭瘯绋嬪簭
 * 娴嬭瘯Swing鎵€鏈夌粍浠跺強鍏剁浉搴旂殑浜嬩欢
 * @author 澶╃考.鏉� 2003.4.17 鏅�23:14
 * @link http://www.robochina.org
 * @link robococde@etang.com
 */
public class UiTest extends JFrame
{
    /**
     * 涓绘ā鍧楋紝鍒濆鍖栨墍鏈夊瓙妯″潡锛屽苟璁剧疆涓绘鏋剁殑鐩稿叧灞炴€�
     */
    public UiTest()
    {
        // 鍒濆鍖栨墍鏈夋ā鍧�
        MenuTest menuTest = new MenuTest();
        LeftPanel leftPanel = new LeftPanel();
        RightPanel rightPanel = new RightPanel();
        BottomPanel bottomPanel = new BottomPanel();
        CenterPanel centerPanel = new CenterPanel();
        
        // 璁剧疆涓绘鏋剁殑甯冨眬
        Container c = this.getContentPane();
        // c.setLayout(new BorderLayout())
        this.setJMenuBar(menuTest);
        
        c.add(leftPanel,BorderLayout.WEST);
        c.add(rightPanel,BorderLayout.EAST);
        c.add(centerPanel,BorderLayout.CENTER);
        c.add(bottomPanel,BorderLayout.SOUTH);
        
        // 鍒╃敤鏃犲悕鍐呴殣绫伙紝澧炲姞绐楀彛浜嬩欢
        this.addWindowListener(new WindowAdapter()
            {
                public void WindowClosing(WindowEvent e)
                {   
                    // 閲婃斁璧勬簮锛岄€€鍑虹▼搴�
                    dispose();
                    System.exit(0);
                }
            });
            
        
        
        setSize(700,500);
        setTitle("Swing 缁勪欢澶у叏绠€浣撶増");
        // 闅愯棌frame鐨勬爣棰樻爮,姝ゅ姛鏆傛椂鍏抽棴锛屼互鏂逛究浣跨敤window浜嬩欢
        // setUndecorated(true);
        setLocation(200,150);
        this.setVisible(true);  
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * 鑿滃崟鏍忓鐞嗘ā鍧�
     * JMenuBar --+
     *            --JMenu--+
     *                     --JMenuItem  --ActionListener 
     *              
     */
    class MenuTest extends JMenuBar
    {
        private JDialog aboutDialog;
            
        /**
         * 鑿滃崟鍒濆鍖栨搷浣�
         */ 
        public MenuTest()
        {
            JMenu fileMenu = new JMenu("鏂囦欢");
            JMenuItem exitMenuItem = new JMenuItem("閫€鍑�",KeyEvent.VK_E);
            JMenuItem aboutMenuItem = new JMenuItem("鍏充簬...",KeyEvent.VK_A);            
                                                
            fileMenu.add(exitMenuItem);
            fileMenu.add(aboutMenuItem);
            
            this.add(fileMenu);     
            
                    
            aboutDialog = new JDialog();
            initAboutDialog();
                        
            // 鑿滃崟浜嬩欢
            exitMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    dispose();
                    System.exit(0);
                }
            });
            
            aboutMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // "鍏充簬"瀵硅瘽妗嗙殑澶勭悊
                    aboutDialog.show();
                }
            });         
                        
        }
        
        /**
         * 杩斿洖鍏充簬瀵硅瘽妗�
         */
        public JDialog getAboutDialog()
        {
            return aboutDialog;
        }
        
        /**
         * 璁剧疆"鍏充簬"瀵硅瘽妗嗙殑澶栬鍙婂搷搴斾簨浠�,鎿嶄綔鍜孞Frame涓€鏍烽兘鏄湪鍐呭
         * 妗嗘灦涓婅繘琛岀殑
         */
        public void initAboutDialog()
        {
            aboutDialog.setTitle("鍏充簬");
            
            Container con =aboutDialog.getContentPane();
             
            // Swing 涓娇鐢╤tml璇彞
            Icon icon = new ImageIcon("smile.gif");
            JLabel aboutLabel = new JLabel("<html><b><font size=5>"+
            "<center>Swing 缁勪欢澶у叏绠€浣撶増锛�"+"<br>澶╃考.鏉�",icon,JLabel.CENTER);
                        
            //JLabel aboutLabel = new JLabel("Swing 缁勪欢澶у叏绠€浣撶増锛�",icon,JLabel.CENTER);
            con.add(aboutLabel,BorderLayout.CENTER);
            
            aboutDialog.setSize(450,225);
            aboutDialog.setLocation(300,300);                       
            aboutDialog.addWindowListener(new WindowAdapter()
            {
                public void WindowClosing(WindowEvent e)
                {
                    dispose();
                }                   
            });         
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 鏈€宸﹁竟妯″潡锛岀户鎵縅Panel,鍒濆鍖栧唴瀹逛负JTree
     * JPanel--+
     *         --JTree
     */
    class LeftPanel extends JPanel
    {
        private int i = 0;
        public LeftPanel()
        {
            
            DefaultMutableTreeNode  root = new DefaultMutableTreeNode("Root");
            DefaultMutableTreeNode child = new DefaultMutableTreeNode("Child");
            DefaultMutableTreeNode select = new DefaultMutableTreeNode("select");
            
            DefaultMutableTreeNode child1 = new DefaultMutableTreeNode(""+i);
            
            root.add(child);        
            root.add(select);
            child.add(child1);  
            
            JTree tree = new JTree(root);
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
            
            // 姣忎釜鑺傜偣鐨勮楂�
            tree.setRowHeight(20);          
            tree.addTreeSelectionListener(new TreeSelectionListener ()
            {
                public void valueChanged(TreeSelectionEvent e)
                {
                    // 鍐呴殣绫讳笉鑳界洿鎺ュ紩鐢ㄥ閮ㄧ被tree锛�1.澶栭儴鍙橀噺鍙敵鏄庝负final 2.鏂板缓澶栭儴绫荤殑瀵硅薄
                    JTree tree =(JTree)e.getSource();
                    DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                    i++;
                    selectNode.add(new DefaultMutableTreeNode(""+i));
                }
            });         
            
            tree.setPreferredSize(new Dimension(100,300));
        //  tree.setEnabled(true);
            JScrollPane scrollPane = new JScrollPane(tree);
            //scrollPane.setSize(100,350);
            this.add(scrollPane);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 鏈€涓嬮潰灞傛ā鍧楋紝缁ф壙JPanel,鍒濆鍖栧唴瀹逛负杩涘害鏉★紝骞剁敱瀹氭椂鍣ㄦ帶鍒�
     * JPanel--+
     *         --JProcessBar  --Timer
     */
    class BottomPanel extends JPanel
    {
        private JProgressBar pb;
        public BottomPanel()
        {
            pb = new JProgressBar();
            pb.setPreferredSize(new Dimension(680,20));
            
            // 璁剧疆瀹氭椂鍣紝鐢ㄦ潵鎺у埗杩涘害鏉＄殑澶勭悊
            Timer time = new Timer(1,new ActionListener()
            { 
                int counter = 0;
                public void actionPerformed(ActionEvent e)
                {
                    counter++;
                    pb.setValue(counter);
                    Timer t = (Timer)e.getSource();
                    
                    // 濡傛灉杩涘害鏉¤揪鍒版渶澶у€奸噸鏂板紑鍙戣鏁�
                    if (counter == pb.getMaximum())
                    {
                        t.stop();
                        counter =0;
                        t.start();
                    }                   
                }
            });
            time.start();
            
            pb.setStringPainted(true);
            pb.setMinimum(0);
            pb.setMaximum(1000);
            pb.setBackground(Color.white);
            pb.setForeground(Color.red);
                        
            this.add(pb);               
        }
        
        /**
         * 璁剧疆杩涘害鏉＄殑鏁版嵁妯″瀷
         */
        public void setProcessBar(BoundedRangeModel rangeModel)
        {
            pb.setModel(rangeModel);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 鏈€鍙宠竟妯″潡锛岀户鎵縅Panel,鍒濆鍖栧悇绉嶆寜閽�
     * JPanel--+
     *         --JButton  --JToggleButton -- JList -- JCombox --JCheckBox ....
     */
    class RightPanel extends JPanel
    {
        public RightPanel()
        {
            this.setLayout(new GridLayout(8,1));        
            
            
            // 鍒濆鍖栧悇绉嶆寜閽�
            JCheckBox checkBox = new JCheckBox("澶嶉€夋寜閽�");          
            JButton button = new JButton("鎵撳紑鏂囦欢");
            button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JFileChooser file = new JFileChooser();
                    int result = file.showOpenDialog(new JPanel());
                    if (result ==file.APPROVE_OPTION) 
                    {
                        String fileName = file.getSelectedFile().getName();                 
                        String dir = file.getCurrentDirectory().toString();
                        JOptionPane.showConfirmDialog(null,dir+"\\"+fileName,"閫夋嫨鐨勬枃浠�",JOptionPane.YES_OPTION);
                     }
                }
            });
                    
            JToggleButton toggleButton = new JToggleButton("鍙屾€佹寜閽�");
            
            ButtonGroup buttonGroup = new ButtonGroup();
            JRadioButton radioButton1 = new JRadioButton("鍗曢€夋寜閽�1",false);
            JRadioButton radioButton2 = new JRadioButton("鍗曢€夋寜閽�2",false);
            
            // 缁勫悎妗嗙殑澶勭悊
            JComboBox comboBox = new JComboBox();
            comboBox.setToolTipText("鐐瑰嚮涓嬫媺鍒楄〃澧炲姞閫夐」");
            comboBox.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e)
                {
                    JComboBox comboBox =(JComboBox)e.getSource();
                    comboBox.addItem("绋嬪簭鍛�");
                    comboBox.addItem("鍒嗘瀽鍛�");
                }
            });
            
            // 鍒楄〃妗嗙殑澶勭悊
            DefaultListModel litem = new DefaultListModel();
            litem.addElement("棣欒晧");
            litem.addElement("姘存灉");
            JList list = new JList(litem);
            
            
            list.addListSelectionListener(new ListSelectionListener ()
            {
                public void valueChanged(ListSelectionEvent e)
                {
                    JList l = (JList)e.getSource();
                    Object s= l.getSelectedValue();
                    JOptionPane.showMessageDialog(null,s,"娑堟伅妗�",JOptionPane.YES_OPTION);
                }
            });
            
            // 澧炲姞鎸夐挳缁�
            buttonGroup.add(radioButton1);
            buttonGroup.add(radioButton2);
            
            // 澧炲姞鍚勭鎸夐挳鍒癑Panel涓樉绀�
            add(button);
            add(toggleButton);
            add(checkBox);
            add(radioButton1);          
            add(radioButton2);
            add(comboBox);
            
            add(list);
            
            this.setBorder(new EtchedBorder(EtchedBorder.LOWERED,Color.LIGHT_GRAY,Color.blue));
        }       
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 涓棿灞傛ā鍧楋紝缁ф壙JPanel,鍒濆鍖栭〉绛�,骞跺湪椤电涓缃枃鏈尯锛岃〃鏍�,
     * 鏂囨湰鍖轰笂涓嬬敤鍒嗛殧鏉″垎闅�
     * JPanel--+
     *         -JTabbedPane--+
     *                       --Draw --JTable  -JTextAreas -JText --JPopupMenu
     */
    class CenterPanel extends JPanel
    {
        public CenterPanel()
        {
            JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
            
            JTextField textField = new JTextField("鏂囨湰鍩�,鐐瑰嚮鎵撳紑<鏂囦欢鎸夐挳>鍙€夋嫨鏂囦欢");
            textField.setActionCommand("textField");
            
            JTextPane textPane = new JTextPane();
            textPane.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            textPane.setText("缂栬緫鍣�,璇曠潃鐐瑰嚮鏂囨湰鍖猴紝璇曠潃鎷夊姩鍒嗛殧鏉°€�");
                        
            textPane.addMouseListener(new MouseAdapter () 
            {
                public void mousePressed (MouseEvent e)
                {
                    JTextPane textPane = (JTextPane)e.getSource();
                    textPane.setText("缂栬緫鍣ㄧ偣鍑诲懡浠ゆ垚鍔�");
                //  textField.setText(""+textPane.getText());
                }
            });
            
            /*
            UpperCaseDocument doc = new Document(); 
            textField.setDocumentsetDocument(doc);
            doc.addDocumentListener(new DocumentListener()
            {
                public void changedUpdate(DocumentEvent e){}
                public void removeUpdate(DocumentEvent e){}
                public void insertUpdate(DocumentEvent e)
                {
                    Document text = (Document)e.getDocument();
                    text.setText("澶嶅埗鎴愬姛");
                }               
            });
            */
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,textField,textPane);
            
                
            JTable table = new JTable(10,10);
            //table.showHorizontalLines(true);
            //table.showVerticalLines(true);
            //table.gridColor(Color.blue);
            
            JPanel pane  = new JPanel();
            pane.add(table.getTableHeader(),BorderLayout.NORTH);
            pane.add(table);
                        
            tab.addTab("鏂囨湰婕旂ず",splitPane);
            //tab.addTab(table.getTableHeader());
            tab.addTab("琛ㄦ牸婕旂ず",pane);
            tab.setPreferredSize(new Dimension(500,600));
            this.add(tab);
            this.setEnabled(true);          
        }
    }
    
    
    public static void main(String args[])
    {
        // 璁剧疆涓绘鏋跺睘鎬�,姝ゅ娌℃湁浣跨敤锛屽彲鎵撳紑鐪嬬湅鏁堟灉
        //try
        //{
        //  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //}
        //catch  (Exception e){}
        new UiTest();                        
    }
}