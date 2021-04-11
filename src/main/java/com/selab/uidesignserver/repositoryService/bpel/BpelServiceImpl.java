package com.selab.uidesignserver.repositoryService.bpel;

import com.selab.uidesignserver.dao.bpel.BpelJsonIRRepository;
import com.selab.uidesignserver.dao.bpel.BpelProjectRepository;
import com.selab.uidesignserver.entity.bpel.BpelJsonIR;
import com.selab.uidesignserver.entity.bpel.BpelProject;
import com.selab.uidesignserver.entity.bpel.BpelProjectJsonIR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BpelServiceImpl implements BpelService {
    @Autowired
    BpelProjectRepository bpelProjectRepository;
    @Autowired
    BpelJsonIRRepository bpelJsonIRRepository;

    @Override
    public List<BpelProject> getProjectList() {
        return bpelProjectRepository.findAll();
    }

    @Override
    public BpelProject createProject(BpelProject project) {
        project.initProjectJsonIRSet();
        return bpelProjectRepository.save(project);
    }

    @Override
    public BpelProject getProject(String id) {
        return Objects.requireNonNull(bpelProjectRepository.findById(Integer.valueOf(id)).orElse(null));
    }

    @Override
    public BpelProject updateProject(String id, BpelProject project) {
        BpelProject bp = Objects.requireNonNull(bpelProjectRepository.findById(Integer.valueOf(id)).orElse(null));

        bp.setName(project.getName());

        return bpelProjectRepository.save(bp);
    }

    @Override
    public void deleteProject(String id) {
        bpelProjectRepository.deleteById(Integer.valueOf(id));
    }

    @Override
    public List<BpelJsonIR> getBpelJsonIRList(String projectId) {
        BpelProject bp = Objects.requireNonNull(
                bpelProjectRepository.findById(Integer.valueOf(projectId)).orElse(null));
        return bpelJsonIRRepository.findAllByProjectId(bp.getId());
    }

    @Override
    public BpelJsonIR createBpelJsonIR(String projectId, BpelJsonIR bpelJsonIR) {
        BpelProject bp = Objects.requireNonNull(
                bpelProjectRepository.findById(Integer.valueOf(projectId)).orElse(null));

        LocalDateTime now = LocalDateTime.now();
        bpelJsonIR.setCreatedAt(now);
        bpelJsonIR.setUpdatedAt(now);

        BpelProjectJsonIR bpj = new BpelProjectJsonIR();
        bpj.setBpelProject(bp);
        bpj.setBpelJsonIR(bpelJsonIR);

        bp.addProjectJsonIR(bpj);
        bpelJsonIR.initProjectJsonIRSet();
        bpelJsonIR.addProjectJsonIR(bpj);

        return bpelJsonIRRepository.save(bpelJsonIR);
    }

    @Override
    public BpelJsonIR getBpelJsonIR(String id) {
        return Objects.requireNonNull(bpelJsonIRRepository.findById(Integer.valueOf(id)).orElse(null));
    }

    @Override
    public BpelJsonIR updateBpelJsonIR(String id, BpelJsonIR bpelJsonIR) {
        BpelJsonIR bj = Objects.requireNonNull(bpelJsonIRRepository.findById(Integer.valueOf(id)).orElse(null));

        bj.setName(bpelJsonIR.getName());
        bj.setContent(bpelJsonIR.getContent());
        bj.setUpdatedAt(LocalDateTime.now());

        return bpelJsonIRRepository.save(bj);
    }

    @Override
    public void deleteBpelJsonIR(String id) {
        bpelJsonIRRepository.deleteById(Integer.valueOf(id));
    }
}
