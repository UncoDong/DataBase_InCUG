import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu {
    boolean Worker_or_Boss;
    JFrame frame = new JFrame("主菜单");
    JButton bt[] = new JButton[4];
    Mypanel panel;
    JLabel userlabel = new JLabel("<html>&emsp工作人员<br>动物管理系统</html>");
    String username = "董安宁";
    SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
    JLabel time = new JLabel("今天是 "+today.format(new Date()));
    ImageIcon workericon,bossicon;
    int comtinue_day = 10;
    JLabel Jcomtinue_day = new JLabel("持续天数："+comtinue_day+" 天");
    JLabel welcome = new JLabel("<html>欢迎回来！<br>" + username + "</html>");
    private void init()
    {
        workericon = new ImageIcon("pictures\\主界面.jpg");
        bossicon = new ImageIcon("pictures\\主界面.jpg");
        panel = new Mypanel();
        panel.setimg(workericon);
        bt[0] = new JButton("动物管理");
        bt[1] = new JButton("报表申请");
        bt[2] = new JButton("信息查询");
        bt[3] = new JButton("退出系统");

        panel.setLayout(null);
        panel.add(bt[0]);
        panel.add(bt[1]);
        panel.add(bt[2]);
        panel.add(bt[3]);
        panel.add(userlabel);
        panel.add(time);
        panel.add(Jcomtinue_day);
        panel.add(welcome);

        bt[0].setBounds(40, 250, 120, 50);	//预订按钮
        bt[1].setBounds(190, 250, 120, 50);	//退票按钮
        bt[2].setBounds(340,250,120,50);
        bt[3].setBounds(490, 250, 120, 50);
        bt[0].setFont( new Font("楷体",Font.PLAIN,20) );
        bt[1].setFont( new Font("楷体",Font.PLAIN,20) );
        bt[2].setFont( new Font("楷体",Font.PLAIN,20) );
        bt[3].setFont( new Font("楷体",Font.PLAIN,20) );
        userlabel.setFont( new Font("楷体",Font.BOLD,38) );
        userlabel.setBounds(200, 20,250, 100);
        welcome.setFont( new Font("宋体",Font.CENTER_BASELINE,26));
        welcome.setBounds(50, 70,250, 100);
        Jcomtinue_day.setFont(new Font("楷体",Font.CENTER_BASELINE,22));
        Jcomtinue_day.setBounds(230, 150,250, 100);
        time.setFont(new Font("楷体",Font.CENTER_BASELINE,20));
        time.setBounds(230, 100,250, 100);

        Button_init();

        frame.add(panel);
        frame.setSize(650,380);
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
                // TODO Auto-generated method stub
                frame.dispose();//关闭窗口
                new Manager(Worker_or_Boss,username);

            }
        } );
        bt[1].addActionListener( new ActionListener()	//报表申请 or 报表审批
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                //frame.setVisible(false);
            }
        });

        bt[2].addActionListener( new ActionListener()		//信息查询
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO Auto-generated method stub
                try {
                    new Search(Worker_or_Boss,username);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                frame.dispose();//关闭窗口
                // System.exit(1);
            }
        });

        bt[3].addActionListener( new ActionListener()	//退出系统
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(1);
            }

        });
    }

    public Menu(boolean Worker_or_Boss,String name)
    {
        this.username = name;
        this.Worker_or_Boss = Worker_or_Boss;
        welcome.setText("<html>欢迎回来！<br>" + username + "</html>");
        init();
        if(Worker_or_Boss==false)
        {
            userlabel.setText("<html>&emsp工作人员<br>动物管理系统</html>");
            bt[0].setText("动物调动");
            bt[1].setText("报表申请");
        }
        else {
            userlabel.setText("<html>&emsp管理人员<br>动物管理系统</html>");
            bt[0].setText("人员调动");
            bt[1].setText("报表审批");
        }

    }

    public static void main(String[] args) {

        new Menu(false,"工具人1");
    }
}

