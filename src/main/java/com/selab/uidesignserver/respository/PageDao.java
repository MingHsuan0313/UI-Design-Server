package com.selab.uidesignserver.respository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PageDao {

    String selector;

    public void addPage(String data) throws SQLException {
        JSONObject pdl = new JSONObject(data);
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306","root","");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use demo");
//        stmt.executeUpdate("truncate table templates");
        PreparedStatement pps = connection.prepareStatement("insert into pages values(?,?,?,?)  on duplicate key update layout=?, pdl=?");


        selector = pdl.getString("selector");
        pps.setInt(1, Integer.parseInt(pdl.getString("id")));
        pps.setString(2, pdl.getString("selector"));
        pps.setString(3, pdl.getJSONObject("componentList").getString("selector"));
        pps.setString(4, pdl.toString());
        pps.setString(5, pdl.getJSONObject("componentList").getString("selector"));
        pps.setString(6, pdl.toString());
        pps.executeQuery();
        connection.close();
    }

    public String getPages() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306","root","");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use demo");
        String sql = "select * from pages";
        ResultSet rs = stmt.executeQuery(sql);

        List<JSONObject> pages = new ArrayList<JSONObject>();
        while(rs.next()){
            JSONObject page = new JSONObject();
            page.put("id",rs.getInt("id"));
            page.put("selector",rs.getString("selector"));
            page.put("pdl", rs.getString("pdl"));
            pages.add(page);
        }

        connection.close();
        return pages.toString();
    }
}
