package com.selab.uidesignserver.entity.uiComposition;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "NDLs")
public class NavigationsTable {

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "navigationID", nullable = false)
	private int id;
    
    @Column(name = "ndl", nullable = false)
    private String ndl;

	@ManyToOne
	@JoinColumn(name = "projectID", referencedColumnName = "projectID")
	private ProjectsTable projectsTable;

	@ManyToOne
	@JoinColumn(name = "themeID", referencedColumnName = "themeID")
	private ThemesTable themesTable;

	@ManyToOne
	@JoinColumn(name = "pageID", referencedColumnName = "pageID")
	private PagesTable pagesTable;

	public NavigationsTable() {
		
	}
	
	public NavigationsTable(String ndl, ProjectsTable projectsTable, ThemesTable themesTable, PagesTable pagesTable) {
		this.ndl = ndl;
		this.projectsTable = projectsTable;
		this.themesTable = themesTable;
		this.pagesTable = pagesTable;
	}

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

	public ProjectsTable getProjectsTable() {
		return projectsTable;
	}

	public void setProjectsTable(ProjectsTable projectsTable) {
		this.projectsTable = projectsTable;
	}

	public PagesTable getPagesTable() {
		return pagesTable;
	}

	public void setPagesTable(PagesTable pagesTable) {
		this.pagesTable = pagesTable;
	}

	public ThemesTable getThemesTable() {
		return themesTable;
	}

	public void setThemesTable(ThemesTable themesTable) {
		this.themesTable = themesTable;
	}
}