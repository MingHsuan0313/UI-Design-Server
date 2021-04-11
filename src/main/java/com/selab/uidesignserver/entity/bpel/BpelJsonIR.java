package com.selab.uidesignserver.entity.bpel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selab.uidesignserver.config.BpelDBConfig;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = BpelDBConfig.Constant.BPEL_JSON_IR_TABLE)
public class BpelJsonIR {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "bpelJsonIR", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<BpelProjectJsonIR> projectJsonIRSet;

    public BpelJsonIR() {}

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
