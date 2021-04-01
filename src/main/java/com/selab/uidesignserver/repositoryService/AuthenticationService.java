package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.authentication.GroupsTable;
import com.selab.uidesignserver.entity.authentication.UsersGroupsTable;
import com.selab.uidesignserver.entity.authentication.UsersTable;

public interface AuthenticationService {
    // Users
    UsersTable insertUser(UsersTable usersTable);
    Boolean deleteUser(String userID);
    UsersTable getUser(String userID);
    Boolean authenticate(String userName, String password);
    void truncateUser();

    // Groups
    UsersTable insertGroup(GroupsTable groupsTable);
    Boolean deleteGroup(String groupID);
    GroupsTable getGroup(String groupID);
    void truncateGroup();

    // User_Groups
    UsersGroupsTable insertUserGroupRelation(UsersGroupsTable usersGroupsTable);
    Boolean deleteUserGroupRelation(String userID, String groupID);
    List<UsersTable> getUsersByGroup(String groupID);
    List<GroupsTable> getGroupsByUser(String userID);
    void truncateUsersGroupsTable();

}
