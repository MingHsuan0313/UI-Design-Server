package com.selab.uidesignserver.repositoryService;

import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

import com.selab.uidesignserver.dao.uiComposition.*;
import com.selab.uidesignserver.entity.uiComposition.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InternalRepresentationServiceImp implements InternalRepresentationService {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    PagesRepository pageRepository;

    @Autowired
    NavigationRepository navigationRepository;

    @Autowired
    ThemesRepository themeRepository;

    @Autowired
    SumdlsRepository sumdlRepository;

    @Autowired
    ProjectsRepository projectRepository;

    // project
    @Override
    public ProjectsTable insertProject(ProjectsTable projectsTable) {
        return projectsRepository.save(projectsTable);
    }

    @Override
    public ProjectsTable getProject(String projectID) {
        ProjectsTable projectsTable = projectsRepository.findProjectsTableByProjectID(projectID);
        return projectsTable;
    }

    @Override
    public List<ProjectsTable> getProjectsByGroupID(String groupID) {
        List<ProjectsTable> projects = new ArrayList<ProjectsTable>();
        projects = this.projectRepository.findProjectsTablesByGroupID(groupID);
        return projects;
    }

    @Override
    public ProjectsTable getProjectByProjectName(String projectName) {
        return projectsRepository.findProjectsTableByProjectName(projectName);
    }

    @Override
    public List<ThemesTable> getThemesByProjectID(String projectID) {
        return themeRepository.findThemesTableByProjectID(projectID);
    }

    @Override
    public boolean deleteProject(String projectID) {
        ProjectsTable projectsTable = projectsRepository.findProjectsTableByProjectID(projectID);
        if (projectsTable != null) {
            projectsRepository.delete(projectsTable);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void truncateProjects() {
        projectsRepository.deleteAll();
    }

    // page
    @Override
    public PagesTable insertPage(PagesTable pagesTable) {
        return pageRepository.save(pagesTable);
    }

    @Override
    public List<PagesTable> getPages(String projectID) {
        List<PagesTable> allPages = pageRepository.findAll();
        List<PagesTable> wantedPages = new ArrayList<PagesTable>();
        for (int index = 0; index < allPages.size(); index++) {
            if (allPages.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                wantedPages.add(allPages.get(index));
            }
        }
        return wantedPages;
    }

    @Override
    public List<PagesTable> getPagesByProjectID(String projectID) {
        return pageRepository.findPDLsTablesByProjectID(projectID);
    }

    @Override
    public PagesTable getPageByPageID(String pageID) {
        return pageRepository.findPagesTableByPageID(pageID);
    }

    @Override
    public List<PagesTable> getPagesByThemeID(String themeID) {
        return pageRepository.findPDLsTablesByThemeID(themeID);
    }

    @Override
    public boolean deletePages(String projectID) {
        boolean flag = false;
        List<PagesTable> pages = pageRepository.findAll();
        for (int index = 0; index < pages.size(); index++) {
            if (pages.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                System.out.println("delete page");
                flag = true;
                pageRepository.delete(pages.get(index));
            }
        }
        return flag;
    }

    @Override
    public boolean deletePagesByTheme(String themeID) {
        boolean flag = false;
        List<PagesTable> pages = pageRepository.findAll();
        for (int index = 0; index < pages.size(); index++) {
            if (pages.get(index).getThemeTable().getId().equals(themeID)) {
                System.out.println("delete page");
                flag = true;
                pageRepository.delete(pages.get(index));
            }
        }
        return flag;
    }

    @Override
    public void truncatePages() {
        pageRepository.deleteAll();
    }

    // navigation
    @Override
    public NavigationsTable insertNaivigation(NavigationsTable navigationTable) {
        return navigationRepository.save(navigationTable);
    }

    @Override
    public NavigationsTable getNavigation(String projectID) {
        List<NavigationsTable> allNavigations = navigationRepository.findAll();
        NavigationsTable wantedNavigation = new NavigationsTable();
        for (int index = 0; index < allNavigations.size(); index++) {
            if (allNavigations.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                wantedNavigation = allNavigations.get(index);
                break;
            }
        }
        return wantedNavigation;
    }

    @Override
    public boolean deleteNavigation(String projectID) {
        boolean flag = false;
        List<NavigationsTable> navigations = navigationRepository.findAll();
        for (int index = 0; index < navigations.size(); index++) {
            if (navigations.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                navigationRepository.delete(navigations.get(index));
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean deleteNavigationByTheme(String themeID) {
        boolean flag = false;
        List<NavigationsTable> navigations = navigationRepository.findAll();
        for (int index = 0; index < navigations.size(); index++) {
            if (navigations.get(index).getThemesTable().getId().equals(themeID)) {
                navigationRepository.delete(navigations.get(index));
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public List<NavigationsTable> getNDLsByProjectID(String projectID) {
        return navigationRepository.findNDLsTablesByProjectID(projectID);
    }

    @Override
    public NavigationsTable getNavigationByPageID(String pageID) {
        return navigationRepository.findNDLsTablesByPageID(pageID);
    }

    @Override
    public void truncateNavigations() {
        navigationRepository.deleteAll();
    }

    // sumdl
    @Override
    public SumdlsTable insertSumdl(SumdlsTable sumdlTable) {
        return sumdlRepository.save(sumdlTable);
    }

    @Override
    public SumdlsTable getSumdl(String projectID) {
        List<SumdlsTable> allSumdls = sumdlRepository.findAll();
        SumdlsTable wantedSumdl = new SumdlsTable();
        for (int index = 0; index < allSumdls.size(); index++) {
            if (allSumdls.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                wantedSumdl = allSumdls.get(index);
                break;
            }
        }
        return wantedSumdl;
    }

    @Override
    public List<SumdlsTable> getSUMDLsByProjectID(String projectID) {
        return sumdlRepository.findSUMDLTablesByProjectID(projectID);
    }

    @Override
    public SumdlsTable getSUMDLsByPageID(String pageID) {
        return sumdlRepository.findSUMDLTablesByPageID(pageID);
    }

    @Override
    public boolean deleteSumdl(String projectID) {
        boolean flag = false;
        List<SumdlsTable> sumdls = sumdlRepository.findAll();
        for (int index = 0; index < sumdls.size(); index++) {
            if (sumdls.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                sumdlRepository.delete(sumdls.get(index));
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean deleteSumdlByTheme(String themeID) {
        boolean flag = false;
        List<SumdlsTable> sumdls = sumdlRepository.findAll();
        for (int index = 0; index < sumdls.size(); index++) {
            if (sumdls.get(index).getThemesTable().getId().equals(themeID)) {
                sumdlRepository.delete(sumdls.get(index));
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public void truncateSumdls() {
        sumdlRepository.deleteAll();
    }

    // themes
    @Override
    public ThemesTable insertTheme(ThemesTable themeTable) {
        return themeRepository.save(themeTable);
    }

    @Override
    public ThemesTable getThemeById(String themeID) {
        return themeRepository.findById(themeID).get();
    }

    @Override
    public List<ThemesTable> getThemes(String projectID) {
        List<ThemesTable> allThemes = themeRepository.findAll();
        List<ThemesTable> wantedThemes = new ArrayList<ThemesTable>();
        for (int index = 0; index < allThemes.size(); index++) {
            if (allThemes.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                wantedThemes.add(allThemes.get(index));
            }
        }
        return wantedThemes;
    }

    @Override
    public boolean deleteThemesByIds(String[] themeIds) {
        boolean flag = false;
        List<ThemesTable> themes = new ArrayList<ThemesTable>();
        for(int index = 0; index < themeIds.length; index++) {
            String themeId = themeIds[index];
            if(themeRepository.findThemesTableByID(themeId) != null)
                themes.add(themeRepository.findThemesTableByID(themeId));
        }

        for (int index = 0; index < themes.size(); index++) {
            String themeID = themes.get(index).getId();
            // 2. delete NDL tables
            List<NavigationsTable> ndls = this.navigationRepository.findNDLsTableByThemeID(themeID);
            for(int j = 0; j < ndls.size(); j++) {
                navigationRepository.delete(ndls.get(j));
            }
            // 3. delete sumdl tables
            List<SumdlsTable> sumdls = this.sumdlRepository.findSUMDLTableByThemeID(themeID);
            for(int j = 0; j < sumdls.size(); j++) {
                sumdlRepository.delete(sumdls.get(j));
            }
            // 1. delete pageUICDL tables
            List<PagesTable> pages = this.pageRepository.findPDLsTablesByThemeID(themeID);
            for(int j = 0; j < pages.size(); j++) {
                pageRepository.delete(pages.get(j));
            }

            // 4. delete theme table
            themeRepository.delete(themes.get(index));
            System.out.println("delete theme");
            flag = true;
        }
        return flag;
    }
    
    @Override
    public boolean deleteProjectsByIds(String[] projectIds) {
        List<ProjectsTable> projects = new ArrayList<ProjectsTable>();

        for(int index = 0; index < projectIds.length; index++) {
            String projectId = projectIds[index];
            projects.add(projectRepository.findProjectsTableByProjectID(projectId));
            for(int j = 0; j < projects.size(); j++) {
                projectRepository.delete(projects.get(j));
                this.deleteThemesByProjectId(projects.get(j).getProjectID());
            }
        }

        return false;
    }

    @Override
    public boolean deleteThemesByProjectId(String projectID) {
        boolean flag = false;
        List<ThemesTable> themes = this.themeRepository.findThemesTableByProjectID(projectID);
        for (int index = 0; index < themes.size(); index++) {
            String themeID = themes.get(index).getId();
            // 2. delete NDL tables
            List<NavigationsTable> ndls = this.navigationRepository.findNDLsTableByThemeID(themeID);
            for(int j = 0; j < ndls.size(); j++) {
                navigationRepository.delete(ndls.get(j));
            }
            // 3. delete sumdl tables
            List<SumdlsTable> sumdls = this.sumdlRepository.findSUMDLTableByThemeID(themeID);
            for(int j = 0; j < sumdls.size(); j++) {
                sumdlRepository.delete(sumdls.get(j));
            }
            // 1. delete pageUICDL tables
            List<PagesTable> pages = this.pageRepository.findPDLsTablesByThemeID(themeID);
            for(int j = 0; j < pages.size(); j++) {
                pageRepository.delete(pages.get(j));
            }

            // 4. delete theme table
            themeRepository.delete(themes.get(index));
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean deleteThemeByID(String themeID) {
        ThemesTable deleteTheme = themeRepository.findThemesTableByID(themeID);
        if (deleteTheme != null) {
            themeRepository.delete(deleteTheme);
            return true;
        }
        return false;
    }

    @Override
    public boolean setThemeToUnused(String themeID) {
        ThemesTable themesTable = themeRepository.findById(themeID).get();
        if (themesTable != null && themesTable.getUsed() == true) {
            themesTable.setUsed(false);
            themeRepository.save(themesTable);
            return true;
        }
        return false;
    }

    @Override
    public void truncateThemes() {
        themeRepository.deleteAll();
    }

    @Override
    public void refreshAllThemes() {
        themeRepository.refreshUsed();
    }

    @Override
    public boolean modifyProject(ProjectsTable project) {
        this.projectsRepository.save(project);
        return false;
    }

    @Override
    public boolean setLayout(String projectName, String layout) {
        ProjectsTable project = this.projectRepository.findProjectsTableByProjectName(projectName);
        if(project != null) {
            project.setLayout(layout);
            this.projectRepository.save(project);
            return true;
        }
        return false;
    }
}
