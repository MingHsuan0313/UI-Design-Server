package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.GroupsTable;
import com.selab.uidesignserver.entity.uiComposition.UsersGroupsTable;
import com.selab.uidesignserver.entity.uiComposition.UsersTable;

public interface AuthenticationService {
    // Users
    UsersTable insertUser(UsersTable usersTable);
    Boolean deleteUser(String userID);
    UsersTable getUser(String userID);
    UsersTable getUserByUserName(String userName);
    Boolean authenticate(String userName, String password);
    void truncateUser();

    // Groups
    GroupsTable insertGroup(GroupsTable groupsTable);
    Boolean deleteGroup(String groupID);
    GroupsTable getGroup(String groupID);
    GroupsTable getGroupByName(String name);
    void truncateGroup();

    // User_Groups
    UsersGroupsTable insertUserGroupRelation(UsersGroupsTable usersGroupsTable);
    Boolean deleteUserGroupRelation(String userID, String groupID);
    List<UsersGroupsTable> getUsersByGroup(String groupID);
    List<UsersGroupsTable> getGroupsByUser(String userID);
    void truncateUsersGroupsTable();

}
