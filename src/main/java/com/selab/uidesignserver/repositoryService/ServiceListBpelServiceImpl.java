package com.selab.uidesignserver.repositoryService;

import com.selab.uidesignserver.dao.uiComposition.ServiceListBpelJsonIRRepository;
import com.selab.uidesignserver.entity.uiComposition.ServiceListBpelJsonIR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ServiceListBpelServiceImpl implements ServiceListBpelService {
    @Autowired
    private ServiceListBpelJsonIRRepository serviceListBpelJsonIRRepository;

    @Autowired
    private InternalRepresentationService internalRepresentationService;

    @Override
    public ServiceListBpelJsonIR getServiceListBpelJsonIR(String projectName, String themeId, String pageId, String selector) {
        return serviceListBpelJsonIRRepository.findByScopeSelector(projectName, themeId, pageId, selector);
    }

    @Override
    public ServiceListBpelJsonIR createServiceListBpelJsonIR(String projectName, String themeId, String pageId, ServiceListBpelJsonIR serviceListBpelJsonIR) {
        serviceListBpelJsonIR.setProjectsTable(internalRepresentationService.getProjectByProjectName(projectName));
        serviceListBpelJsonIR.setThemesTable(internalRepresentationService.getThemeById(themeId));
        serviceListBpelJsonIR.setPagesTable(internalRepresentationService.getPageByPageID(pageId));

        LocalDateTime now = LocalDateTime.now();
        serviceListBpelJsonIR.setCreatedAt(now);
        serviceListBpelJsonIR.setUpdatedAt(now);

        return serviceListBpelJsonIRRepository.save(serviceListBpelJsonIR);
    }

    @Override
    public ServiceListBpelJsonIR updateServiceListBpelJsonIR(String id, ServiceListBpelJsonIR serviceListBpelJsonIR) {
        ServiceListBpelJsonIR slbj = Objects.requireNonNull(serviceListBpelJsonIRRepository.findById(Integer.valueOf(id)).orElse(null));

        slbj.setSelector(serviceListBpelJsonIR.getSelector());
        slbj.setContent(serviceListBpelJsonIR.getContent());
        slbj.setUpdatedAt(LocalDateTime.now());

        return serviceListBpelJsonIRRepository.save(slbj);
    }

    @Override
    public void deleteServiceListBpelJsonIR(String id) {
        serviceListBpelJsonIRRepository.deleteById(Integer.valueOf(id));
    }

    @Override
    public void deleteServiceListBpelJsonIRByProject(String projectName) {
        List<ServiceListBpelJsonIR> serviceListBpelJsonIRList = serviceListBpelJsonIRRepository.findAll();
        for (ServiceListBpelJsonIR slbj : serviceListBpelJsonIRList) {
            if (slbj.getProjectsTable().getProjectName().equals(projectName)) {
                serviceListBpelJsonIRRepository.delete(slbj);
            }
        }
    }

    @Override
    public void deleteServiceListBpelJsonIRByTheme(String themeId) {
        List<ServiceListBpelJsonIR> serviceListBpelJsonIRList = serviceListBpelJsonIRRepository.findAll();
        for (ServiceListBpelJsonIR slbj : serviceListBpelJsonIRList) {
            if (slbj.getThemesTable().getId().equals(themeId)) {
                serviceListBpelJsonIRRepository.delete(slbj);
            }
        }
    }

    @Override
    public void deleteServiceListBpelJsonIRByPage(String pageId) {
        List<ServiceListBpelJsonIR> serviceListBpelJsonIRList = serviceListBpelJsonIRRepository.findAll();
        for (ServiceListBpelJsonIR slbj : serviceListBpelJsonIRList) {
            if (slbj.getPagesTable().getId().equals(pageId)) {
                serviceListBpelJsonIRRepository.delete(slbj);
            }
        }
    }

    @Override
    public List<ServiceListBpelJsonIR> getServiceBpelJsonIRUnderTheme(String themeId) {
        return serviceListBpelJsonIRRepository.findByTheme(themeId);
    }

    @Override
    public ServiceListBpelJsonIR createServiceBpelJsonIRByProjectId(String projectId, String themeId, String pageId, ServiceListBpelJsonIR serviceListBpelJsonIR) {
        serviceListBpelJsonIR.setProjectsTable(internalRepresentationService.getProject(projectId));
        serviceListBpelJsonIR.setThemesTable(internalRepresentationService.getThemeById(themeId));
        serviceListBpelJsonIR.setPagesTable(internalRepresentationService.getPageByPageID(pageId));

        LocalDateTime now = LocalDateTime.now();
        serviceListBpelJsonIR.setCreatedAt(now);
        serviceListBpelJsonIR.setUpdatedAt(now);

        return serviceListBpelJsonIRRepository.save(serviceListBpelJsonIR);
    }
}
