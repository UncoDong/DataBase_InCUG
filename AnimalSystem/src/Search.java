import oracle.ConOracle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Vector;

public class Search {

    boolean Worker_or_Boss;
    String username;
    JLabel id_text = new JLabel("动物编号");
    JLabel begin_text = new JLabel("开始日期");
    JLabel end_text = new JLabel("结束日期");
    JFrame frame = new JFrame("数据查询");
    JButton bt[]= new JButton[2];
    JTable table = null;
    ImageIcon icon;
    String SaveTableName[] = {"清洗信息表","训练信息表","进食信息表","疾病信息表","打扫信息表","接种信息表"};
    Mypanel panel = new Mypanel();
    JLabel animal_name;
    JLabel people_name;
    JCheckBox check[] = new JCheckBox[6];
    DateChooserJButton datebegin ;			//日期选择按钮
    DateChooserJButton dateend;
    JLabel message = new JLabel("备注内容："+"未选定数据");
    JTextField animal_id = new JTextField("CAT1");
    //创建表头
    Vector columnNames = new Vector();
    //创建显示数据
    Vector data = new Vector();



    void init() throws InterruptedException {
        icon = new ImageIcon("pictures\\雪花.jpg");
        panel.setimg(icon);

        Tableint();
        animal_name = new JLabel("动物名：");
        people_name = new JLabel("处理人：");

        datebegin = new DateChooserJButton("开始日期");
        dateend = new DateChooserJButton("结束日期");

        bt[0] = new JButton("查询");
        bt[1] = new JButton("返回");

        check[0] = new JCheckBox("清洗信息",true);
        check[1] = new JCheckBox("训练信息",true);
        check[2] = new JCheckBox("进食信息",true);
        check[3] = new JCheckBox("疾病信息");
        check[4] = new JCheckBox("打扫信息");
        check[5] = new JCheckBox("接种信息");

        panel.setLayout(null);
        panel.add(id_text);
        panel.add(people_name);
        panel.add(animal_name);
        panel.add(animal_id);
        panel.add(datebegin);
        panel.add(dateend);
        panel.add(bt[0]);
        panel.add(bt[1]);
        for(int i = 0;i<6;i++)
        {
            panel.add(check[i]);
            check[i].setFont( new Font("楷体",Font.BOLD,20) );
        }
        panel.add(message);
        panel.add(begin_text);
        panel.add(end_text);

        begin_text.setFont( new Font("楷体",Font.BOLD,20) );
        end_text.setFont( new Font("楷体",Font.BOLD,20) );
        id_text.setFont( new Font("楷体",Font.BOLD,20) );
        animal_name.setFont( new Font("楷体",Font.BOLD,20) );
        people_name.setFont( new Font("楷体",Font.BOLD,20) );
        message.setFont( new Font("楷体",Font.BOLD,20) );
        //message.setForeground(Color.white);
        // JlabelSetText(message,"备注内容："+"未选定数据");
        id_text.setBounds(25,20,150,30);
        animal_id.setBounds(10,50,100,30);
        begin_text.setBounds(25,80,100,30);
        datebegin.setBounds(10,120,120,30);
        end_text.setBounds(25,150,120,30);
        dateend.setBounds(10,180,120,30);
        bt[0].setBounds(10,230,120,30);
        bt[1].setBounds(10,270,120,30);
        check[0].setBounds(100,380,120,30);
        check[1].setBounds(300,380,120,30);
        check[2].setBounds(500,380,120,30);
        check[3].setBounds(100,430,120,30);
        check[4].setBounds(300,430,120,30);
        check[5].setBounds(500,430,120,30);

        animal_name.setBounds(200,300,300,30);
        people_name.setBounds(450,300,300,30);
        message.setBounds(270,240,500,30);
        frame.add(panel);
        frame.setSize(700,550);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


//    void JlabelSetText(JLabel jLabel, String longString)
//            throws InterruptedException {
//        StringBuilder builder = new StringBuilder("<html>");
//        char[] chars = longString.toCharArray();
//        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
//        int start = 0;
//        int len = 0;
//        while (start + len < longString.length()) {
//            while (true) {
//                len++;
//                if (start + len > longString.length())break;
//                if (fontMetrics.charsWidth(chars, start, len)
//                        > jLabel.getWidth()) {
//                    break;
//                }
//            }
//            builder.append(chars, start, len-1).append("<br/>");
//            start = start + len - 1;
//            len = 0;
//        }
//        builder.append(chars, start, longString.length()-start);
//        builder.append("</html>");
//        jLabel.setText(builder.toString());
//
//    }
    void Button_init()
    {
        bt[0].addActionListener( new ActionListener()		//查找
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.print(datebegin.getText());
                System.out.print(dateend.getText());
                System.out.print(datebegin.getText().compareTo(dateend.getText()));
                if(datebegin.getText().compareTo(dateend.getText())>0)
                    JOptionPane.showMessageDialog(null, "时间输入错误！", "重新输入",JOptionPane.WARNING_MESSAGE);
                else
                    OracleSearch();
            }
        } );

        bt[1].addActionListener( new ActionListener()		//返回上一步
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO Auto-generated method stub
                frame.dispose();//关闭窗口
                new Menu(Worker_or_Boss,username);
            }
        });
    }
    Search(boolean Worker_or_Boss,String username) throws InterruptedException {
        this.Worker_or_Boss = Worker_or_Boss;
        this.username = username;
        init();
        Button_init();
    }

    void OracleSearch()  {
        Vector input = new Vector();
        //添加表名
        for(int i = 0;i<6;i++)
            if(check[i].isSelected())
                input.add(SaveTableName[i]);
            try {
                Vector data = ConOracle.Search(animal_id.getText(), input, datebegin.getText(), dateend.getText());
                DefaultTableModel model = new DefaultTableModel(data, this.columnNames);
                table.setModel(model);
                frame.repaint();
                this.data = data;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

    }
    void Tableint()
    {
        //初始化表头
        String[] columnNames = { "时间", "表名", "操作人", "备注","动物名"};
        for(String each:columnNames)
            this.columnNames.add(each);

        DefaultTableModel model = new DefaultTableModel(this.data, this.columnNames);
        table = new JTable();
        table.setModel(model);
        table.setPreferredScrollableViewportSize(new Dimension(800, 100));
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        scrollPane.setBounds(140,20,540,200);//定义位置

        table.setFont(new Font("楷体",Font.PLAIN,17) );
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(table.getSelectedRow());
                Vector row = (Vector)data.get(table.getSelectedRow());
                for(int i = 0;i<row.size();i++)
                    System.out.print(row.get(i));
                try{
                    message.setText("备注内容："+row.get(3));
                    animal_name.setText("动物名："+row.get(4));
                    people_name.setText("处理人："+row.get(2));
                }
                catch (Exception a)
                {
                    System.out.print(a);
                }
                //printDebugData(table);
            }
        });
    }
    void SetData()
    {
        Object[][] data = {
                { "Kathy", "Smith", "Snowboarding", new Boolean(false) },
                { "John", "Doe", "Rowing", new Integer(3) },
                { "Sue", "Black", "Knitting",new Boolean(false) },
                { "Jane", "White", "Speed reading", new Boolean(true) },
                { "Joe", "Brown", "Pool", new Integer(10)}};

        for(int i = 0;i<data.length;i++)
        {
            Vector row = new Vector();
            for(int j = 0;j<data[0].length;j++)
                row.add(data[i][j]);
            this.data.add(row);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new Search(false,"孙昊琳");
    }
}
