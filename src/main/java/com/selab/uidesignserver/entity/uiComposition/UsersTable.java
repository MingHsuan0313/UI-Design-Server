package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class UsersTable {
    @Id
    @Column(name="userID", nullable = false)
    private String userID;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="userName", nullable = false)
    private String userName;

    public UsersTable(){}

    public UsersTable(String userID, String userName, String password){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    public String getUserID(){
        return this.userID;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}