package com.selab.uidesignserver.entity.bpel;

import com.selab.uidesignserver.config.BpelDBConfig;

import javax.persistence.*;

@Entity
@Table(name = BpelDBConfig.Constant.PROJECT_BPEL_JSON_IR_TABLE)
public class BpelProjectJsonIR {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private BpelProject bpelProject;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bpel_json_ir_id")
    private BpelJsonIR bpelJsonIR;

    public BpelProjectJsonIR() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BpelProject getBpelProject() {
        return bpelProject;
    }

    public void setBpelProject(BpelProject bpelProject) {
        this.bpelProject = bpelProject;
    }

    public BpelJsonIR getBpelJsonIR() {
        return bpelJsonIR;
    }

    public void setBpelJsonIR(BpelJsonIR bpelJsonIR) {
        this.bpelJsonIR = bpelJsonIR;
    }
}
