package com.selab.uidesignserver.repositoryService;

import java.util.ArrayList;
import java.util.List;

import com.selab.uidesignserver.dao.uiComposition.NavigationRepository;
import com.selab.uidesignserver.dao.uiComposition.PagesRepository;
import com.selab.uidesignserver.dao.uiComposition.SumdlsRepository;
import com.selab.uidesignserver.dao.uiComposition.ThemesRepository;
import com.selab.uidesignserver.entity.uiComposition.NavigationsTable;
import com.selab.uidesignserver.entity.uiComposition.PagesTable;
import com.selab.uidesignserver.entity.uiComposition.SumdlsTable;
import com.selab.uidesignserver.entity.uiComposition.ThemesTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InternalRepresentationServiceImp implements InternalRepresentationService {
    
    @Autowired
    PagesRepository pageRepository;

    @Autowired
    NavigationRepository navigationRepository;

    @Autowired
    ThemesRepository themeRepository;

    @Autowired
    SumdlsRepository sumdlRepository;

    // page
	@Override
	public PagesTable insertPage(PagesTable pagesTable) {
        return pageRepository.save(pagesTable);
	}
    
    @Override
    public List<PagesTable> getPages(String projectName) {
        List<PagesTable> allPages = pageRepository.findAll();
        List<PagesTable> wantedPages = new ArrayList<PagesTable>();
        for(int index = 0;index < allPages.size(); index++) {
            if(allPages.get(index).getProjectName().equals(projectName)) {
                wantedPages.add(allPages.get(index));
            }
        }
        return wantedPages;
    }

    @Override
    public boolean deletePages(String projectName) {
        boolean flag = false;
        List<PagesTable> pages = pageRepository.findAll();
        for(int index = 0;index < pages.size();index++) {
            if(pages.get(index).getProjectName().equals(projectName)) {
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
    public NavigationsTable getNavigation(String projectName) {
        List<NavigationsTable> allNavigations = navigationRepository.findAll();
        NavigationsTable wantedNavigation = new NavigationsTable();
        for(int index = 0;index < allNavigations.size(); index++) {
            if(allNavigations.get(index).getProjectName().equals(projectName)) {
                wantedNavigation = allNavigations.get(index);
                break;
            }
        }
        return wantedNavigation;
    }

    @Override
    public boolean deleteNavigation(String projectName) {
        boolean flag = false;
        List<NavigationsTable> navigations = navigationRepository.findAll();
        for(int index = 0;index < navigations.size();index++) {
            if(navigations.get(index).getProjectName().equals(projectName)) {
                navigationRepository.delete(navigations.get(index));
                flag = true;
                break;
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
    public SumdlsTable getSumdl(String projectName) {
        List<SumdlsTable> allSumdls = sumdlRepository.findAll();
        SumdlsTable wantedSumdl = new SumdlsTable();
        for(int index = 0;index < allSumdls.size(); index++) {
            if(allSumdls.get(index).getProjectName().equals(projectName)) {
                wantedSumdl = allSumdls.get(index);
                break;
            }
        }
        return wantedSumdl;
    }

    @Override
    public boolean deleteSumdl(String projectName) {
        boolean flag = false;
        List<SumdlsTable> sumdls = sumdlRepository.findAll();
        for(int index = 0;index < sumdls.size();index++) {
            if(sumdls.get(index).getProjectName().equals(projectName)) {
                sumdlRepository.delete(sumdls.get(index));
                flag = true;
                break;
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
    public List<ThemesTable> getThemes(String projectName) {
        List<ThemesTable> allThemes = themeRepository.findAll();
        List<ThemesTable> wantedThemes = new ArrayList<ThemesTable>();
        for(int index = 0;index < allThemes.size(); index++) {
            if(allThemes.get(index).getProjectName().equals(projectName)) {
                wantedThemes.add(allThemes.get(index));
            }
        }
        return wantedThemes;
    }

    @Override
    public boolean deleteThemes(String projectName) {
        boolean flag = false;
        List<ThemesTable> themes = themeRepository.findAll();
        for(int index = 0;index < themes.size();index++) {
            if(themes.get(index).getProjectName().equals(projectName)) {
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