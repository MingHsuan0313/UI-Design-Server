package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.NavigationsTable;
import com.selab.uidesignserver.entity.uiComposition.PagesTable;
import com.selab.uidesignserver.entity.uiComposition.SumdlsTable;
import com.selab.uidesignserver.entity.uiComposition.ThemesTable;

// A project has
// 1. single ndl
// 2. single sumdl
// 3. multiple pageUICDLs
public interface InternalRepresentationService {
    // page controller
    public PagesTable insertPage(PagesTable pagesTable);
    public List<PagesTable> getPages(String projectName);
    public void deletePages(String projectName);
    public void truncatePages();
    
    // navigation controller
    public NavigationsTable insertNaivigation(NavigationsTable navigationTable);
    public NavigationsTable getNavigation(String projectName);
    public void deleteNavigation(String projectName);
    public void truncateNavigations();

    // sumdl controller
    public SumdlsTable insertSumdl(SumdlsTable sumdlTable);
    public SumdlsTable getSumdl(String projectName);
    public void deleteSumdl(String projectName);
    public void truncateSumdls();

    // themes controller
    public ThemesTable insertTheme(ThemesTable themeTable);
    public List<ThemesTable> getThemes(String projectName);
    public ThemesTable getThemeById(String ThemeId);
    public void deleteThemes(String projectName);
    public void truncateThemes();
}
