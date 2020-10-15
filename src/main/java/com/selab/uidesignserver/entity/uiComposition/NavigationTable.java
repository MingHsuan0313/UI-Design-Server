package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "navigation")
public class NavigationTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "ndl")
    private String ndl;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNdl() {
		return this.ndl;
	}

	public void setNdl(String ndl) {
		this.ndl = ndl;
	}

    
}
