package com.selab.uidesignserver.entity.bpel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selab.uidesignserver.config.BpelDBConfig;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = BpelDBConfig.Constant.PROJECT_TABLE)
public class BpelProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "bpelProject", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<BpelProjectJsonIR> projectJsonIRSet;

    public BpelProject() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BpelProjectJsonIR> getProjectJsonIRSet() {
        return projectJsonIRSet;
    }

    public void setProjectJsonIRSet(Set<BpelProjectJsonIR> projectJsonIRSet) {
        this.projectJsonIRSet = projectJsonIRSet;
    }

    public void initProjectJsonIRSet() {
        projectJsonIRSet = new HashSet<>();
    }

    public void addProjectJsonIR(BpelProjectJsonIR projectJsonIR) {
        projectJsonIRSet.add(projectJsonIR);
    }
}
