package com.selab.uidesignserver.respository;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class NavigationDao {
    public void store(String data) throws SQLException {
        JSONObject pdl = new JSONObject(data);
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306","root","");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use demo");
//        stmt.executeUpdate("truncate table templates");
        PreparedStatement pps = connection.prepareStatement("insert into navigation values(?,?)  on duplicate key update ndl=?");

        // only one ndl
        pps.setInt(1, 1);
        pps.setString(2, data);
        pps.setString(3,data);

        pps.executeQuery();
        connection.close();

        System.out.println("ndl has been added to DB");
    }
}
