package oracle;

import oracle.jdbc.OracleTypes;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ConOracle {


    //数据库的链接
    public static Connection getConn() {
        Connection conn=null;
        try {
            //加载驱动
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "username", "123456");
            System.out.println("链接数据库成功！");
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败！");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("链接数据库失败！");
            e.printStackTrace();
        }
        return conn;
    }

    //多条表查询
    public List<Object> queryAll(String sql, Object[] values) throws Exception {
        RowmapperAll ra = new RowmapperAll();
        Connection conn=getConn();
        PreparedStatement ps=null;
        List<Object> list=new ArrayList<Object>();
        try {
            //执行语句
            ps=conn.prepareStatement(sql);
            for(int i=0;i<values.length;i++) {
                ps.setObject(i+1, values[i]);
            }
            ResultSet rs=ps.executeQuery();
            //接收查询出的结果
            list=ra.rowMapperAll(rs);
        } catch (SQLException e) {
            System.out.println("多条查询中创建PrepareStatement失败");
            e.printStackTrace();
        }finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        conn.close();
        return list;
    }

    //游标查询
    public void Test() throws SQLException {
        Connection conn=getConn();
        //执行语句
        CallableStatement proc = conn.prepareCall("call Demo_123(?)");
        proc.registerOutParameter(1, OracleTypes.CURSOR);
        proc.execute();
        ResultSet rs = (ResultSet)proc.getObject(1); //获得第一个参数是一个游标,转化成ResultSet类型
        while(rs.next()) //获得数据
        {
            System.out.println("<tr><td>" + rs.getString(1) + "</td><td>"+rs.getString(2)+"</td></tr>");
        }
        conn.close();
    }

    //寻找相应动物信息
    public static Vector Search (String animal_id, Vector table, String begin_date, String end_date)throws SQLException
    {
        Connection conn=getConn();
        ResultSet[] results = new ResultSet[table.size()];
        for(int i = 0;i<table.size();i++)
        {
            String name = (String)table.get(i);
            CallableStatement proc = conn.prepareCall("call Select_Table(?,?,?,?,?)");
            proc.setString(1,animal_id);//放入动物ID
            proc.setString(2,name);//放入姓名
            proc.setString(3,begin_date);//放入开始日期
            proc.setString(4,end_date);//放入结束日期
            proc.registerOutParameter(5, OracleTypes.CURSOR);//得到返回游标
            proc.execute();
            ResultSet rs = (ResultSet)proc.getObject(5); //获得第一个参数是一个游标,转化成ResultSet类型
            results[i] = rs;
        }
        Vector data = new Vector();
        //时间rs.getString(3)
        //名字rs.getString(8)
        //操作人rs.getString(2)
        //备注rs.getString(5)
        //表名rs.getString(5));
        for(ResultSet rs:results)
        {
            while(rs.next()) //获得数据
            {
                Vector row = new Vector();
                row.add(rs.getString(3));
                row.add(rs.getString(5));
                row.add(rs.getString(2));
                row.add(rs.getString(4));
                row.add(rs.getString(8));
                data.add(row);
            }
        }
        return data;
    }

    //登录查询
    public static String[] LogIn(boolean Worker_or_Boss, String username) throws SQLException{
        String ans[] = new String[2];
        Connection conn=getConn();
        String password = null;
        String mingzi = null;
        if(Worker_or_Boss == false)//根据工作人员or管理人员，查找对应的登录信息
        {
            CallableStatement proc = conn.prepareCall("call Worker_Login(?,?,?)");
            proc.setString(1,username);
            proc.registerOutParameter(2,Types.VARCHAR);
            proc.registerOutParameter(3,Types.VARCHAR);
            proc.execute();
            password = proc.getString(2);//获得输出参数
            mingzi = proc.getString(3);
        }
        else
        {
            CallableStatement proc = conn.prepareCall("call Boss_Login(?,?,?)");
            proc.setString(1,username);
            proc.registerOutParameter(2,Types.VARCHAR);
            proc.registerOutParameter(3,Types.VARCHAR);
            proc.execute();
            password = proc.getString(2);//获得输出参数
            mingzi = proc.getString(3);
        }
        ans[0] = password;
        ans[1] = mingzi;
        return ans;
    }

    //查询所有动物的信息
    public static Vector GetAllAnimal() throws SQLException {
        Vector ans = new Vector();
        Connection conn=getConn();
        CallableStatement proc = conn.prepareCall("call Select_All_Animal(?)");
        proc.registerOutParameter(1, OracleTypes.CURSOR);
        proc.execute();
        ResultSet rs = (ResultSet)proc.getObject(1); //获得第一个参数是一个游标,转化成ResultSet类型

        while(rs.next()) //获得数据
        {
            Vector row = new Vector();
            for(int i = 1;i<=7;i++)
                row.add(rs.getString(i));
            ans.add(row);
        }
        conn.close();
        return ans;

    }
    public static Vector GetAllPeople() throws SQLException {
        Vector ans = new Vector();
        Connection conn=getConn();
        CallableStatement proc = conn.prepareCall("call Select_All_People(?)");
        proc.registerOutParameter(1, OracleTypes.CURSOR);
        proc.execute();
        ResultSet rs = (ResultSet)proc.getObject(1); //获得第一个参数是一个游标,转化成ResultSet类型

        while(rs.next()) //获得数据
        {
            Vector row = new Vector();
            for(int i = 1;i<=6;i++)
                row.add(rs.getString(i));
            ans.add(row);
        }
        conn.close();
        return ans;

    }

    //在主函数进行输出测试
    public static void main(String[] args) throws SQLException {
        Vector a = GetAllPeople();
        for(int i = 0;i<a.size();i++)
        {
            Vector b = (Vector)a.get(i);
            for(Object c:b)
            {
                System.out.print(c+" ");
            }
            System.out.println("");
        }
    }
}

