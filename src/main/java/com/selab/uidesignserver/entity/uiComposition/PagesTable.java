package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.*;

@Entity
@Table(name = "pages")
public class PagesTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "selector")
    private String selector;
    
    @Column(name = "layout")
    private String layout;
    
    @Column(name = "pdl")
    private String pdl;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSelector() {
		return this.selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public String getLayout() {
		return this.layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getPdl() {
		return this.pdl;
	}

	public void setPdl(String pdl) {
		this.pdl = pdl;
	}

}
