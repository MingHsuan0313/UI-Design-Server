package com.selab.uidesignserver.dao.authentication;

import com.selab.uidesignserver.entity.authentication.GroupsTable;
import com.selab.uidesignserver.entity.authentication.UsersGroupsTable;
import com.selab.uidesignserver.entity.authentication.UsersTable;
import com.selab.uidesignserver.entity.uiComposition.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserGroupRelationshipRepository extends JpaRepository<UsersGroupsTable, String> {
    @Query(value = "SELECT ug FROM Users_Groups ug WHERE ug.userID = ?1 AND ug.groupID = ?2", nativeQuery = true)
    List<UsersGroupsTable> findRelationTableByUserID(String userID, String groupID);
}
