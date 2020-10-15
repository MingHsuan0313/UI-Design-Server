package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.dao.uiComposition.PagesRepository;
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

	@Override
	public PagesTable insertPage(int id, String selector, String layout, String pdl) {
        System.out.println("start insert");
		// TODO Auto-generated method stub
        return pageRepository.saveAndFlush(new PagesTable(id,selector,layout,pdl));

	}
    
    @Override
    public List<PagesTable> getTables() {
        return pageRepository.getTables();
    }
    
}
