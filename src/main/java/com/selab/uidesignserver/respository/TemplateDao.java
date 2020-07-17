package com.selab.uidesignserver.respository;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class TemplateDao {
    private List<String> templates;

    public void addTemplate(JSONObject component, String html) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306","root","");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use demo");
        PreparedStatement pps = connection.prepareStatement("insert into templates values(?,?)");
        pps.setString(1,component.getString("selector"));
        pps.setString(2,html);
        ResultSet rs = pps.executeQuery();
        System.out.println( component.getString("selector") + " has been added to db");

    }
}
