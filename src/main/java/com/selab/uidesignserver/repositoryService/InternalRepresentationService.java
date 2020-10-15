package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.PagesTable;

public interface InternalRepresentationService {
    public PagesTable insertPage(int id,String selector,String layout,String pdl);
    public List<PagesTable> getTables();
    
}
