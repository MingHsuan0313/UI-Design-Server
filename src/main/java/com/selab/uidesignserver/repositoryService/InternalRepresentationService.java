package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.*;

// A project has
// 1. single ndl
// 2. single sumdl
// 3. multiple pageUICDLs
public interface InternalRepresentationService {
    // project controller
    public ProjectsTable insertProject(ProjectsTable projectsTable);
    public ProjectsTable getProject(String projectID);
    public ProjectsTable getProjectByProjectName(String projectName);
    public boolean deleteProject(String projectID);
    public void truncateProjects();

    // page controller
    public PagesTable insertPage(PagesTable pagesTable);
    public List<PagesTable> getPages(String projectID);
    public PagesTable getPageByPageID(String pageID);
    public boolean deletePages(String projectID);
    public void truncatePages();
    
    // navigation controller
    public NavigationsTable insertNaivigation(NavigationsTable navigationTable);
    public NavigationsTable getNavigation(String projectID);
    public boolean deleteNavigation(String projectID);
    public void truncateNavigations();

    // sumdl controller
    public SumdlsTable insertSumdl(SumdlsTable sumdlTable);
    public SumdlsTable getSumdl(String projectID);
    public boolean deleteSumdl(String projectID);
    public void truncateSumdls();

    // themes controller
    public ThemesTable insertTheme(ThemesTable themeTable);
    public List<ThemesTable> getThemes(String projectID);
    public ThemesTable getThemeById(String ThemeId);
    public boolean deleteThemes(String projectID);
    public void truncateThemes();
}
