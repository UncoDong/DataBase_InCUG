package oracle;

public class Demo{

    public static void main(String[] args)  {
        ConOracle con = new ConOracle();
        //String sql = "SELECT * FROM 工作人员";
        //con.queryAll(sql);
        try{
            con.Test();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
