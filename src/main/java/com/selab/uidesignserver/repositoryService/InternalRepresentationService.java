package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.NavigationsTable;
import com.selab.uidesignserver.entity.uiComposition.PagesTable;

public interface InternalRepresentationService {
    public PagesTable insertPage(PagesTable pagesTable);
    public List<PagesTable> getTables();
    public void truncateTables();
    
    public NavigationsTable insertNaivigation(NavigationsTable navigationTable);
    public List<NavigationsTable> getNavigations();
    public void truncateNavigations();
}
