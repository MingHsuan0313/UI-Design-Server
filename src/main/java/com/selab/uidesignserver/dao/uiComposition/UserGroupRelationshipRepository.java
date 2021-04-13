package com.selab.uidesignserver.dao.uiComposition;

import com.selab.uidesignserver.entity.uiComposition.UsersGroupsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRelationshipRepository extends JpaRepository<UsersGroupsTable, String> {
    @Query(value = "SELECT * FROM Users_Groups ug WHERE ug.userID = ?1 AND ug.groupID = ?2", nativeQuery = true)
    UsersGroupsTable findRelationTableByUserIDAndGroupID(String userID, String groupID);

    @Query(value = "SELECT * FROM Users_Groups ug WHERE ug.userID = ?1", nativeQuery = true)
    List<UsersGroupsTable> findRelationTableByUserID(String userID);

    @Query(value = "SELECT * FROM Users_Groups ug WHERE ug.groupID = ?1", nativeQuery = true)
    List<UsersGroupsTable> findRelationTableByGroupID(String groupID);

    @Query(value = "SELECT * FROM Users_Groups ug WHERE ug.groupID = ?1 and ug.userID = ?2", nativeQuery = true)
    List<UsersGroupsTable> findRelationTableByGroupIDAndUserID(String groupID, String userID);
}
