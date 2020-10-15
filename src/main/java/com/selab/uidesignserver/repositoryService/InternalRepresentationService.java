package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.NavigationTable;
import com.selab.uidesignserver.entity.uiComposition.PagesTable;

public interface InternalRepresentationService {
    public PagesTable insertPage(PagesTable pagesTable);
    public List<PagesTable> getTables();
    public void truncateTables();
    
    public NavigationTable insertNaivigation(NavigationTable navigationTable);
    public List<NavigationTable> getNavigations();
    public void truncateNavigations();
}
