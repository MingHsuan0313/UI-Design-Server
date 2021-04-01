package com.selab.uidesignserver.repositoryService;

import com.selab.uidesignserver.dao.authentication.GroupsRepository;
import com.selab.uidesignserver.dao.authentication.UserGroupRelationshipRepository;
import com.selab.uidesignserver.dao.authentication.UsersRepository;
import com.selab.uidesignserver.entity.authentication.GroupsTable;
import com.selab.uidesignserver.entity.authentication.UsersGroupsTable;
import com.selab.uidesignserver.entity.authentication.UsersTable;
import com.selab.uidesignserver.entity.uiComposition.NavigationsTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    GroupsRepository groupsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserGroupRelationshipRepository userGroupRelationshipRepository;

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
    public Boolean authenticate(String userName, String password){
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
    public void truncateUser(){
        usersRepository.deleteAll();
    }

    // Groups
    @Override
    public UsersTable insertGroup(GroupsTable groupsTable){

    }
    @Override
    public Boolean deleteGroup(String groupID){

    }
    @Override
    public GroupsTable getGroup(String groupID){

    }
    @Override
    void truncateGroup(){

    }

    // User_Groups
    @Override
    public UsersGroupsTable insertUserGroupRelation(UsersGroupsTable usersGroupsTable){

    }
    @Override
    public Boolean deleteUserGroupRelation(String userID, String groupID){}

    @Override
    public List<UsersTable> getUsersByGroup(String groupID){

    }
    @Override
    public List<GroupsTable> getGroupsByUser(String userID){

    }
    @Override
    public void truncateUsersGroupsTable(){

    }
}
