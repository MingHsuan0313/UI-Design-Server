package com.selab.uidesignserver.dao.serviceComponent;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.selab.uidesignserver.entity.serviceComponent.*;

@Repository
public interface ModifiedRecordRepository extends JpaRepository<ModifiedRecordTable, Integer>{
	
}
