package com.selab.uidesignserver.dao.uiComposition;


import com.selab.uidesignserver.entity.uiComposition.GroupsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends JpaRepository<GroupsTable, String> {
    @Query(value = "SELECT * FROM Groups g WHERE g.groupID = ?1", nativeQuery = true)
    GroupsTable findGroupsTableByGroupID(String groupID);

    @Query(value = "SELECT * FROM Groups g WHERE g.groupName = ?1", nativeQuery = true)
    GroupsTable findGroupsTableByGroupName(String name);
}
