package com.selab.uidesignserver.dao.authentication;


import com.selab.uidesignserver.entity.uiComposition.GroupsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupsRepository extends JpaRepository<GroupsTable, String> {
    @Query(value = "SELECT g FROM Group g WHERE g.userID = ?1", nativeQuery = true)
    GroupsTable findGroupsTableByGroupID(String groupID);
}
