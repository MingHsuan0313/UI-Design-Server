package com.selab.uidesignserver.repositoryService;

import com.selab.uidesignserver.dao.uiComposition.*;
import com.selab.uidesignserver.entity.uiComposition.GroupsTable;
import com.selab.uidesignserver.entity.uiComposition.ThemesTable;
import com.selab.uidesignserver.entity.uiComposition.UsersGroupsTable;
import com.selab.uidesignserver.entity.uiComposition.UsersTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    GroupsRepository groupsRepository;

    @Autowired
    UserGroupRelationshipRepository userGroupRelationshipRepository;

    @Autowired
    ThemesRepository themesRepository;

    // Users
    @Override
    public UsersTable insertUser(UsersTable usersTable){
        return usersRepository.save(usersTable);
    }
    @Override
    public Boolean deleteUser(String userID){
        UsersTable usersTable = usersRepository.findUsersTableByUserID(userID);
        boolean flag = false;
        if(usersTable!=null){
            usersRepository.delete(usersTable);
            flag = true;
        }
        return flag;
    }

    @Override
    public UsersTable getUser(String userID){
        return usersRepository.findUsersTableByUserID(userID);
    }

    @Override
    public UsersTable getUserByUserName(String userName) {
        return usersRepository.findUsersTableByUserName(userName);
    }

    @Override
    public Boolean authenticate(String userName, String password){
        System.out.println("Hello authenticate");
        UsersTable usersTable = usersRepository.findUsersTableByUserName(userName);
        if(usersTable == null){
            return false;
        }
        if( usersTable.getPassword().equals(password)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean logout(String userID, List<String> themeIDs) {
        UsersTable usersTable = usersRepository.findUsersTableByUserID(userID);
        if(usersTable!=null){
            for(String themeID: themeIDs){
                ThemesTable themesTable = themesRepository.findThemesTableByID(themeID);
                themesTable.setUsed(false);
                themesRepository.save(themesTable);
            }
            return true;
        }
        return false;

    }

    @Override
    public void truncateUser(){
        usersRepository.deleteAll();
    }

    // Groups
    @Override
    public GroupsTable insertGroup(GroupsTable groupsTable){
        return groupsRepository.save(groupsTable);
    }

    @Override
    public Boolean deleteGroup(String groupID){
        GroupsTable groupsTable = groupsRepository.findGroupsTableByGroupID(groupID);
        boolean flag = false;
        if(groupsTable!=null){
            groupsRepository.delete(groupsTable);
            flag = true;
        }
        return flag;
    }

    @Override
    public GroupsTable getGroup(String groupID){
        return groupsRepository.findGroupsTableByGroupID(groupID);
    }

    @Override
    public GroupsTable getGroupByName(String groupName) {
        return groupsRepository.findGroupsTableByGroupName(groupName);
    }

    @Override
    public void truncateGroup(){
        groupsRepository.deleteAll();
    }

    // User_Groups
    @Override
    public UsersGroupsTable insertUserGroupRelation(UsersGroupsTable usersGroupsTable){
        return userGroupRelationshipRepository.save(usersGroupsTable);
    }

    @Override
    public Boolean deleteUserGroupRelation(String userID, String groupID){
        UsersGroupsTable usersGroupsTable = userGroupRelationshipRepository.findRelationTableByUserIDAndGroupID(userID, groupID);
        boolean flag = false;
        if(usersGroupsTable != null){
            userGroupRelationshipRepository.delete(usersGroupsTable);
            flag = true;
        }
        return flag;
    }

    @Override
    public List<UsersGroupsTable> getUsersByGroup(String groupID){
        return userGroupRelationshipRepository.findRelationTableByGroupID(groupID);
    }

    @Override
    public List<UsersGroupsTable> getGroupsByUser(String userID){
        return userGroupRelationshipRepository.findRelationTableByUserID(userID);
    }
    @Override
    public void truncateUsersGroupsTable(){
        userGroupRelationshipRepository.deleteAll();
    }
}
