package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NDLs")
public class NavigationsTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "navigationID")
	private String id;
    
    @Column(name = "ndl")
    private String ndl;
	
	@Column(name = "projectName")
	private String projectName;

	public NavigationsTable() {
		
	}
	
	public NavigationsTable(String ndl, String projectName) {
		this.ndl = ndl;
		this.projectName = projectName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNdl() {
		return this.ndl;
	}

	public void setNdl(String ndl) {
		this.ndl = ndl;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}