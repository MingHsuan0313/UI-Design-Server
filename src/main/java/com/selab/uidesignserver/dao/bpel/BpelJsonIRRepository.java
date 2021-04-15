package com.selab.uidesignserver.dao.bpel;

import com.selab.uidesignserver.entity.bpel.BpelJsonIR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BpelJsonIRRepository extends JpaRepository<BpelJsonIR, Integer> {
    @Query("select bj " +
            "from BpelJsonIR bj " +
            "inner join BpelProjectJsonIR bpj on bpj.bpelProject.id = ?1 " +
            "where bj.id = bpj.bpelJsonIR.id")
    List<BpelJsonIR> findAllByProjectId(int projectId);
}
