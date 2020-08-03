package com.selab.uidesignserver.respository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class TemplateDao {

    @Autowired
    PageDao pageDao;

    private List<String> templates;

    public void addTemplate(JSONObject component, String html) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306","root","");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use demo");
        PreparedStatement pps = connection.prepareStatement("insert into templates(id, selector,page,html) values(?, ?,?,?) on duplicate key update html=?");
        pps.setInt(1,component.getInt("id"));
        pps.setString(2,component.getString("selector"));
        pps.setString(3,pageDao.selector);
        pps.setString(4,html);
        pps.setString(5,html);
        ResultSet rs = pps.executeQuery();
        System.out.println( component.getString("selector") + " has been added to db");

    }
}
