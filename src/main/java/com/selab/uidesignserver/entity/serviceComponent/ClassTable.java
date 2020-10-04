package com.selab.uidesignserver.entity.serviceComponent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"Class\"")
public class ClassTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classID")
    private int classID;    
    
    @Column(name = "className")
    private String className;
    
    @Column(name = "docString")
    private String docString;

	public int getClassID() {
		return this.classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDocString() {
		return this.docString;
	}

	public void setDocString(String docString) {
		this.docString = docString;
	}
}