package com.selab.uidesignserver.dao.authentication;


import com.selab.uidesignserver.entity.authentication.GroupsTable;
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

public interface GroupsRepository extends JpaRepository<GroupsTable, String> {
    @Query(value = "SELECT g FROM Group g WHERE g.userID = ?1", nativeQuery = true)
    List<GroupsTable> findGroupsTableByGroupID(String groupID);
}
