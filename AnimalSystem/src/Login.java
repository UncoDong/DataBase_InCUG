
import oracle.ConOracle;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
public class Login {
    JFrame frame = new JFrame("登录");
    JButton bt[] = new JButton[3];//按钮数组
    JLabel label[] = new JLabel[3];//标签数组
    JTextField name = new JTextField("用户名1");//用户名
    JPasswordField pwd = new JPasswordField("mima1");//密码
    boolean Worker_or_Boss;//当前是员工还是老板登录 0为员工 1为老板
    ImageIcon Workericon,Bossicon;//背景图片
    Mypanel panel;//自定义Panel
    //初始化
    private void init()
    {
        Worker_or_Boss = false;
        //选取得到图片
        Workericon = new ImageIcon("pictures\\登录背景worker.png");
        Bossicon = new ImageIcon("pictures\\登录背景boss.png");
        panel = new Mypanel();
        panel.setimg(Workericon);
        bt[0] = new JButton("登录");
        bt[1] = new JButton("退出");
        bt[2] = new JButton("<html>管理员<br>&ensp登录</html>");//&emsp一个空白 &ensp半个空白

        label[0] = new JLabel("用户名");
        label[0].setFont( new Font("楷体",Font.BOLD,20) );
        label[1] = new JLabel("密码");
        label[1].setFont( new Font("楷体",Font.BOLD,20) );
        label[2] = new JLabel("动物管理系统");
        label[2].setFont( new Font("楷体",Font.BOLD,40) );

        panel.setLayout(null);
        //放入组件
        for(int i = 0;i<3;i++)
            panel.add(label[i]);
        panel.add(name);
        panel.add(pwd);
        //放入按钮
        for(int i = 0;i<3;i++)
            panel.add(bt[i]);
        //文字标签
        label[0].setBounds(200,80,100,50);
        label[1].setBounds(200,130,100,50);
        label[2].setBounds(160,20,500,50);

        name.setBounds(280,90,150,30);
        pwd.setBounds(280,140,150,30);

        bt[2].setBounds(440,90,90,80);
        bt[2].setFont( new Font("宋体",Font.PLAIN,16) );
        bt[0].setBounds(220,190,80,30);
        bt[1].setBounds(330,190,80,30);

        frame.add(panel);
        frame.setSize(650,320);
        frame.setVisible(true);//显示窗口
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Button_init();
    }
    void Button_init()
    {
        bt[0].addActionListener( new ActionListener()		//登录按钮
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    String[] return_pwd = ConOracle.LogIn(Worker_or_Boss,name.getText());
                    System.out.println(return_pwd[0]);
                    System.out.println(pwd.getText());
                    if(pwd.getText().equals(return_pwd[0]))
                    {
                        new Menu(Worker_or_Boss,return_pwd[1]);
                        frame.dispose();//关闭窗口
                    }
                    else if(return_pwd[0]==null)
                        JOptionPane.showMessageDialog(null, "没有这个用户！╮(╯▽╰)╭", "重新输入",JOptionPane.WARNING_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "密码输入错误！╮(╯▽╰)╭", "重新输入",JOptionPane.WARNING_MESSAGE);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "数据库连接异常~", "检查代码",JOptionPane.WARNING_MESSAGE);
                    ex.printStackTrace();
                }

            }

        });
        bt[1].addActionListener( new ActionListener()		//退出系统按钮
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(1);
            }

        });

        bt[2].addActionListener( new ActionListener()		//切换登录
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(Worker_or_Boss==false)
                {
                    Worker_or_Boss = true;
                    panel.setimg(Bossicon);
                    bt[2].setText("<html>&ensp上班<br>工作人</html>");
                    frame. repaint();
                }
                else
                {
                    Worker_or_Boss = false;
                    panel.setimg(Workericon);
                    bt[2].setText("<html>管理员<br>&ensp登录</html>");
                    frame.repaint();
                }

            }
        });
    }

    public Login(){
        init();
    }

    public static void main(String[] args) {
        new Login();
    }

}
