import oracle.ConOracle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Vector;

//人员和动物管理
public class Manager {
    boolean Animal_or_People;
    JFrame frame = new JFrame("管理");
    JButton bt[]= new JButton[3];
    JTable table = null;
    String username;
    Vector columnNames = new Vector();
    Vector data = new Vector();


    ImageIcon animalicon,peopleicon;
    Mypanel panel = new Mypanel();
    JLabel title = new JLabel("动物信息管理");
    //JLabel text = new JLabel("好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼");

    void init() throws InterruptedException, SQLException {
        animalicon = new ImageIcon("pictures\\管理animal.png");
        peopleicon = new ImageIcon("pictures\\管理people.png");
        panel.setimg(animalicon);
        panel.setLayout(null);

        bt[0] = new JButton("添加信息");
        bt[1] = new JButton("修改信息");
        bt[2] = new JButton("返回");
        bt[0].setFont( new Font("楷体",Font.PLAIN,20) );
        bt[1].setFont( new Font("楷体",Font.PLAIN,20) );
        bt[2].setFont( new Font("楷体",Font.PLAIN,20) );
        for(int i = 0;i<3;i++)
            panel.add(bt[i]);

        Tableint();
        panel.add(title);


        //JlabelSetText(text,"好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼好多鱼");

        title.setFont( new Font("楷体",Font.PLAIN,40) );
        title.setBounds(250,10,300,50);
        bt[0].setBounds(620,60,120,50);
        bt[1].setBounds(620,140,120,50);
        bt[2].setBounds(620,220,120,50);

        frame.add(panel);
        frame.setSize(800,500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Button_init();
    }
    void Button_init()
    {
        bt[0].addActionListener( new ActionListener()		//添加
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                new AddorChange(false,Animal_or_People,username);
            }
        } );
        bt[1].addActionListener( new ActionListener()	//修改
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                frame.dispose();
                new AddorChange(true,Animal_or_People,username);
            }
        });

        bt[2].addActionListener( new ActionListener()		//返回上一步
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO Auto-generated method stub
                frame.dispose();//关闭窗口
                new Menu(Animal_or_People,username);
            }
        });
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();
        System.out.println("Value of data: ");
        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    void Tableint() throws SQLException {
        //创建表头
        String[] columnNames;
        if(Animal_or_People==false)
            columnNames = new String[]{"动物ID", "年龄", "动物名", "图像", "性别", "收容所ID", "种类"};
        else
            columnNames = new String[]{"姓名", "年龄", "收容所ID", "手机号", "用户名", "密码"};
        for(String each:columnNames)
            this.columnNames.add(each);
        if(Animal_or_People==false)
            data = ConOracle.GetAllAnimal();
        else
            data = ConOracle.GetAllPeople();

        table = new JTable(data, this.columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(800, 100));
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        scrollPane.setBounds(10,60,600,300);
        table.setFont(new Font("楷体",Font.PLAIN,17) );
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(table.getSelectedRow());
                //printDebugData(table);
            }
        });
    }

    void JlabelSetText(JLabel jLabel, String longString)
            throws InterruptedException {
        StringBuilder builder = new StringBuilder("<html>");
        char[] chars = longString.toCharArray();
        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len++;
                if (start + len > longString.length())break;
                if (fontMetrics.charsWidth(chars, start, len)
                        > jLabel.getWidth()) {
                    break;
                }
            }
            builder.append(chars, start, len-1).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length()-start);
        builder.append("</html>");
        jLabel.setText(builder.toString());
    }


    //构造函数
    Manager(boolean Animal_or_People,String username)
    {
        this.Animal_or_People = Animal_or_People;
        this.username = username;
        try{
            init();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.Animal_or_People = Animal_or_People;
        if(Animal_or_People == false)
        {
            title.setText("动物信息管理");
            panel.setimg(animalicon);

        }
        else
        {
            title.setText("人员信息管理");
            panel.setimg(peopleicon);
        }

    }

    public static void main(String[] args) {
        new Manager(false,"董安宁");
    }
}
