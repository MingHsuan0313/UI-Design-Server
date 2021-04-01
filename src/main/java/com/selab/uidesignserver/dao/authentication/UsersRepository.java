package com.selab.uidesignserver.dao.authentication;


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

public interface UsersRepository extends JpaRepository<UsersTable, String> {
    @Query(value = "SELECT u FROM Users u WHERE u.userID = ?1", nativeQuery = true)
    UsersTable findUsersTableByUserID(String userID);

    @Query(value = "SELECT u FROM Users u WHERE u.userName = ?1", nativeQuery = true)
    UsersTable findUsersTableByUserName(String userName);
}
