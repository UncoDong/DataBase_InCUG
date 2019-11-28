package oracle;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RowmapperAll {
    public List<Object> rowMapperAll(ResultSet rs) {
        List<Object> list= new ArrayList<Object>();
        try {
            while(rs.next()) {
                System.out.println(rs.getString("姓名"));
                System.out.println(rs.getString("年龄"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
