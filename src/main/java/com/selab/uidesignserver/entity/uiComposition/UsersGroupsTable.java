package com.selab.uidesignserver.entity.uiComposition;


import javax.persistence.*;

@Entity
@Table(name = "Users_Group")
public class UsersGroupsTable {

    @Id
    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UsersTable usersTable;

    @Id
    @OneToOne
    @JoinColumn(name = "groupID", referencedColumnName = "groupID")
    private GroupsTable groupsTable;


    public UsersGroupsTable(){}

    public UsersGroupsTable(UsersTable usersTable, GroupsTable groupsTable){
        this.usersTable = usersTable;
        this.groupsTable = groupsTable;
    }


    public void setGroupsTable(GroupsTable groupsTable) {
        this.groupsTable = groupsTable;
    }


    public void setUsersTable(UsersTable usersTable) {
        this.usersTable = usersTable;
    }

    public GroupsTable getGroupsTable() {
        return groupsTable;
    }


    public UsersTable getUsersTable() {
        return usersTable;
    }
}
