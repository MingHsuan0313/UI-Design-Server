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
    public List<ProjectsTable> getProjectsByGroupID(String groupID);
    public ProjectsTable getProjectByProjectName(String projectName);
    public boolean deleteProject(String projectID);
    public void truncateProjects();
    public List<ThemesTable> getThemesByProjectID(String projectID);
    public boolean modifyProject(ProjectsTable project);

    // page controller
    public PagesTable insertPage(PagesTable pagesTable);
    public List<PagesTable> getPages(String projectID);
    public PagesTable getPageByPageID(String pageID);
    public List<PagesTable> getPagesByProjectID(String projectID);
    public List<PagesTable> getPagesByThemeID(String themeID);
    public boolean deletePages(String projectID);
    public boolean deletePagesByTheme(String themeID);
    public void truncatePages();
    
    // navigation controller
    public NavigationsTable insertNaivigation(NavigationsTable navigationTable);
    public List<NavigationsTable> getNDLsByProjectID(String projectID);
    public NavigationsTable getNavigation(String projectID);
    public NavigationsTable getNavigationByPageID(String pageID);
    public boolean deleteNavigation(String projectID);
    public boolean deleteNavigationByTheme(String themeID);
    public void truncateNavigations();

    // sumdl controller
    public SumdlsTable insertSumdl(SumdlsTable sumdlTable);
    public SumdlsTable getSumdl(String projectID);
    public List<SumdlsTable> getSUMDLsByProjectID(String projectID);
    public SumdlsTable getSUMDLsByPageID(String pageID);
    public boolean deleteSumdl(String projectID);
    public boolean deleteSumdlByTheme(String themeID);
    public void truncateSumdls();

    // themes controller
    public ThemesTable insertTheme(ThemesTable themeTable);
    public List<ThemesTable> getThemes(String projectID);
    public ThemesTable getThemeById(String themeID);
    public boolean setThemeToUnused(String themeID);
    public boolean deleteThemes(String projectID);
    public boolean deleteThemeByID(String themeID);
    public void truncateThemes();

}
