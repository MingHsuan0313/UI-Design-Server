package com.selab.uidesignserver.repositoryService;

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

    // project
    @Override
    public ProjectsTable insertProject(ProjectsTable projectsTable){
        return projectsRepository.save(projectsTable);
    }

    @Override
    public ProjectsTable getProject(String projectID){
        ProjectsTable projectsTable = projectsRepository.findProjectsTableByProjectID(projectID);
        return projectsTable;
    }

    @Override
    public ProjectsTable getProjectByProjectName(String projectName){
        return projectsRepository.findProjectsTableByProjectName(projectName);
    }


    @Override
    public boolean deleteProject(String projectID){
        ProjectsTable projectsTable = projectsRepository.findProjectsTableByProjectID(projectID);
        if(projectsTable != null){
            projectsRepository.delete(projectsTable);
            return true;
        }else{
            return false;
        }
    }


    @Override
    public void truncateProjects(){
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
        for(int index = 0;index < allPages.size(); index++) {
            if(allPages.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                wantedPages.add(allPages.get(index));
            }
        }
        return wantedPages;
    }

    @Override
    public PagesTable getPageByPageID(String pageID){
        return pageRepository.findPagesTableByPageID(pageID);
    }

    @Override
    public boolean deletePages(String projectID) {
        boolean flag = false;
        List<PagesTable> pages = pageRepository.findAll();
        for(int index = 0; index < pages.size(); index++) {
            if(pages.get(index).getProjectsTable().getProjectID().equals(projectID)) {
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
        for(int index = 0;index < allNavigations.size(); index++) {
            if(allNavigations.get(index).getProjectsTable().getProjectID().equals(projectID)) {
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
        for(int index = 0;index < navigations.size();index++) {
            if(navigations.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                navigationRepository.delete(navigations.get(index));
                flag = true;
            }
        }
        return flag;
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
        for(int index = 0;index < allSumdls.size(); index++) {
            if(allSumdls.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                wantedSumdl = allSumdls.get(index);
                break;
            }
        }
        return wantedSumdl;
    }

    @Override
    public boolean deleteSumdl(String projectID) {
        boolean flag = false;
        List<SumdlsTable> sumdls = sumdlRepository.findAll();
        for(int index = 0;index < sumdls.size();index++) {
            if(sumdls.get(index).getProjectsTable().getProjectID().equals(projectID)) {
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
    public ThemesTable getThemeById(String ThemeId) {
        return themeRepository.findById(ThemeId).get();
    }

    @Override
    public List<ThemesTable> getThemes(String projectID) {
        List<ThemesTable> allThemes = themeRepository.findAll();
        List<ThemesTable> wantedThemes = new ArrayList<ThemesTable>();
        for(int index = 0;index < allThemes.size(); index++) {
            if(allThemes.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                wantedThemes.add(allThemes.get(index));
            }
        }
        return wantedThemes;
    }

    @Override
    public boolean deleteThemes(String projectID) {
        boolean flag = false;
        List<ThemesTable> themes = themeRepository.findAll();
        for(int index = 0;index < themes.size();index++) {
            if(themes.get(index).getProjectsTable().getProjectID().equals(projectID)) {
                themeRepository.delete(themes.get(index));
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public void truncateThemes() {
        themeRepository.deleteAll();
    }
}