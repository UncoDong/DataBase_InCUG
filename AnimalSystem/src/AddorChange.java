import oracle.ConOracle;
import oracle.jdbc.OracleTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class AddorChange {
    String username;
    JFrame frame = new JFrame("数据操作");
    JButton bt[]= new JButton[2];
    JTable table = null;
    ImageIcon animalicon,peopleicon;
    Mypanel panel = new Mypanel();
    JLabel title = new JLabel("动物信息添加");
    //创建表头
    Vector columnNames = new Vector();
    //创建数据
    Vector data = new Vector();

    boolean Animal_or_People = false;//判断是动物还是人，false是动物，true是人
    boolean Add_or_Change = false;//判断是添加还是修改
    AddorChange(boolean Add_or_Change,boolean Animal_or_People,String name)
    {
        this.username = name;
        this.Add_or_Change = Add_or_Change;
        this.Animal_or_People = Animal_or_People;
        init();
        Button_init();
        String s = null;
        if(Animal_or_People==false)
        {
            s = "动物信息";
            panel.setimg(animalicon);
        }
        else
        {
            s = "员工信息";
            panel.setimg(peopleicon);
        }

        if(Add_or_Change == false)
        {
            s = s+"添加";
            bt[0].setText("添加信息");
        }
        else
        {
            s = s+"修改";
            bt[0].setText("提交修改");
        }
        title.setText(s);
    }

    void init(){
        Tableint();
        animalicon = new ImageIcon("pictures\\修改animal.png");
        peopleicon = new ImageIcon("pictures\\修改people.png");
        panel.setimg(animalicon);
        panel.setLayout(null);
        bt[0] = new JButton("添加信息");
        bt[1] = new JButton("返回");
        bt[0].setFont( new Font("楷体",Font.PLAIN,20) );
        bt[1].setFont( new Font("楷体",Font.PLAIN,20) );

        panel.add(bt[0]);
        bt[0].setBounds(80,400,120,50);
        panel.add(bt[1]);
        bt[1].setBounds(300,400,120,50);
        title.setFont( new Font("楷体",Font.PLAIN,40) );
        panel.add(title);
        title.setBounds(150,10,300,50);

        frame.add(panel);
        frame.setSize(550,550);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    void Button_init()
    {
        bt[0].addActionListener( new ActionListener()		//动物管理 or 人员调动
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //添加信息
                if(Add_or_Change==false)
                {
                    for(int i = 0; i < 3;i++)
                    {
                        Vector row = (Vector)data.get(i);
                        for(int j = 0;j<row.size();j++)
                            System.out.print(row.get(j));
                        System.out.println(" ");
                    }
                }
                //修改信息
                else{

                }

            }
        } );
        bt[1].addActionListener( new ActionListener()	//报表申请 or 报表审批
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();//关闭窗口
                new Manager(Animal_or_People,username);
            }
        });


    }

    void Tableint()
    {
        String[] columnNames;
        if(Animal_or_People==false)
            columnNames = new String[]{"动物ID", "年龄", "动物名", "图像", "性别", "收容所ID", "种类"};
        else
            columnNames = new String[]{"姓名", "年龄", "收容所ID", "手机号", "用户名", "密码"};

        for(String each:columnNames)
            this.columnNames.add(each);
        if(Add_or_Change==false)
        {
            for(int i = 0;i<10;i++)
            {
                Vector row = new Vector();
                for(int j = 0;j<7;j++)
                    row.add(" ");
                data.add(row);

            }
        }
        else
        {
            try {
                if(Animal_or_People==false)
                    data = ConOracle.GetAllAnimal();
                else
                    data = ConOracle.GetAllPeople();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        table = new JTable(data, this.columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(800, 100));
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        scrollPane.setBounds(30,60,450,300);
        table.setFont(new Font("楷体",Font.PLAIN,17) );
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(table.getSelectedRow());
                //printDebugData(table);
            }
        });
    }

    public static void main(String[] args) {
        new AddorChange(false,true,"工具人1");
    }

}
