package com.selab.uidesignserver.respository;
import org.json.JSONObject;


import java.sql.*;

public class PageDao {

    public void addPage(JSONObject pdl) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306","root","");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use demo");
        stmt.executeUpdate("truncate table templates");
        PreparedStatement pps = connection.prepareStatement("insert into pages values(?,?,?,?) on duplicate key update layout=? pdl=?");

        pps.setString(1, pdl.getString("id"));
        pps.setString(2, pdl.getString("selector"));
        pps.setString(3, pdl.getJSONObject("componentList").getString("selector"));
        pps.setString(4, pdl.toString());
        pps.setString(5, pdl.getJSONObject("componentList").getString("selector"));
        pps.setString(6, pdl.toString());
        pps.executeQuery();
    }
}
