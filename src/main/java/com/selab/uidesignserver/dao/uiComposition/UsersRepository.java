package com.selab.uidesignserver.dao.uiComposition;


import com.selab.uidesignserver.entity.uiComposition.UsersTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersTable, String> {
    @Query(value = "SELECT * FROM Users u WHERE u.userID = ?1", nativeQuery = true)
    UsersTable findUsersTableByUserID(String userID);

    @Query(value = "SELECT * FROM Users u WHERE u.userName = ?1", nativeQuery = true)
    UsersTable findUsersTableByUserName(String userName);
}
