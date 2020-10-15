package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.dao.uiComposition.NavigationRepository;
import com.selab.uidesignserver.dao.uiComposition.PagesRepository;
import com.selab.uidesignserver.entity.uiComposition.NavigationTable;
import com.selab.uidesignserver.entity.uiComposition.PagesTable;

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

	@Override
	public PagesTable insertPage(PagesTable pagesTable) {
        System.out.println("start insert");
		// TODO Auto-generated method stub
        return pageRepository.save(pagesTable);

	}
    
    @Override
    public List<PagesTable> getTables() {
        return pageRepository.getTables();
    }

    @Override
    public void truncateTables() {
        pageRepository.deleteAll();
    }

	@Override
	public NavigationTable insertNaivigation(NavigationTable navigationTable) {
        // List<NavigationTable> navigationTables = navigationRepository.findAll();
        // navigationTables.add(navigationTable);
        return navigationRepository.save(navigationTable);
	}

	@Override
	public List<NavigationTable> getNavigations() {
		// TODO Auto-generated method stub
		return navigationRepository.findAll();
	}

	@Override
	public void truncateNavigations() {
		// TODO Auto-generated method stub
        navigationRepository.deleteAll();
		
    }
    
}
