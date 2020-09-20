package com.selab.uidesignserver.respository;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

@Repository
public class ServiceComponentDao {
    public String getFrameworkTypes() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "timhsieh",
                "ji3yjo4dj4x87");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use TEST_DB");
        String sql = "select * from FrameworkType";
        ResultSet rs = stmt.executeQuery(sql);

        List<JSONObject> frameworkTypes = new ArrayList<JSONObject>();
        while (rs.next()) {
            JSONObject frameworkType = new JSONObject();
            frameworkType.put("frameworkTypeID", rs.getInt("frameworkTypeID"));
            frameworkType.put("type", rs.getInt("type"));
            frameworkTypes.add(frameworkType);
        }
        connection.close();
        return frameworkTypes.toString();
    }

    // Service : Web Client -> BPEL
    public String getInputServices(String uiCategory, String parameters, String isMatchmaking) throws SQLException {
        int parameterCount = Integer.parseInt(parameters);

        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "timhsieh",
                "ji3yjo4dj4x87");
        Connection connection2 = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "timhsieh",
                "ji3yjo4dj4x87");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use TEST_DB");
        String sql = "";
        if (uiCategory.equals("input")) {
            System.out.println("I am in");
            if (parameterCount == 0) {

                return "parameter count 0";
            } else
                sql = "select * from ServiceComponent where argumentcount = " + parameters;
        } else if (uiCategory.equals("informative")) {
            return "informative";
        }
        System.out.println("sql here " + sql);
        ResultSet rs = stmt.executeQuery(sql);
        List<JSONObject> serviceComponents = new ArrayList<JSONObject>();
        while (rs.next()) {
            JSONObject serviceComponent = new JSONObject();
            String classID = rs.getString("classID");
            System.out.println("class id = " + classID);
            String sql2 = "select className from Class where classID = " + classID;
            Statement stmt2 = connection2.createStatement();
            stmt2.executeUpdate("use TEST_DB");
            ResultSet rs2 = stmt2.executeQuery(sql2);
            rs2.next();

            serviceComponent.put("code", rs.getString("code"));
            serviceComponent.put("name", rs.getString("name"));
            serviceComponent.put("serviceID", rs.getString("serviceID"));
            serviceComponent.put("className", rs2.getString("className"));
            serviceComponents.add(serviceComponent);
        }
        connection.close();
        connection2.close();
        return serviceComponents.toString();
        // String sql = "select * from "
    }

    public String getArguments(String serviceID) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "timhsieh",
                "ji3yjo4dj4x87");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use TEST_DB");
        String sql = "select * from Argument where serviceID = " + serviceID;
        // String sql = "select * from Argument where serviceID = 2";
        ResultSet rs = stmt.executeQuery(sql);

        List<JSONObject> arguments = new ArrayList<JSONObject>();
        while (rs.next()) {
            JSONObject argument = new JSONObject();
            argument.put("name", rs.getString("name"));
            argument.put("nth", rs.getInt("nth"));
            arguments.add(argument);
        }
        connection.close();
        return arguments.toString();
    }

    // Service : BPEL to Web Client
    public String getOutputServices(String isMatchmaking) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "timhsieh",
                "ji3yjo4dj4x87");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use TEST_DB");
        String sql = "select * from ServiceComponent where name like 'get%'";
        ResultSet rs = stmt.executeQuery(sql);

        List<JSONObject> serviceComponents = new ArrayList<JSONObject>();
        while (rs.next()) {
            JSONObject serviceComponent = new JSONObject();
            serviceComponent.put("code", rs.getString("code"));
            serviceComponent.put("name", rs.getString("name"));
            serviceComponent.put("serviceID", rs.getString("serviceID"));
            serviceComponents.add(serviceComponent);
        }
        connection.close();
        return serviceComponents.toString();
    }
    
    // get Code by Service id
    public String getCodeByServiceID(String serviceID) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "timhsieh",
                "ji3yjo4dj4x87");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("use TEST_DB");
        String sql = "select code from ServiceComponent where serviceID = " + serviceID;
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();

        JSONObject code = new JSONObject();
        code.put("code", rs.getString("code"));
        connection.close();
        return code.toString();
    }
}
