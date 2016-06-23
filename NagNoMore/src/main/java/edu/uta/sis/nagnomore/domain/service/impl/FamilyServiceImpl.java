package edu.uta.sis.nagnomore.domain.service.impl;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.service.FamilyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hanna on 8.6.2016.
 */
@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    FamilyRepository familyRepository;

    @Transactional(readOnly = false)
    public void addFamily(WwwFamily f) {
        FamilyEntity fe = new FamilyEntity();
        BeanUtils.copyProperties(f,fe);
        familyRepository.addFamily(fe);
    }

    @Transactional(readOnly = false)
    public void updateFamily(WwwFamily f) {
        FamilyEntity fe = familyRepository.findFamily(f.getId());
        BeanUtils.copyProperties(f,fe);
        familyRepository.updateFamily(fe);
    }
    @Transactional(readOnly = true)
    public WwwFamily findFamily(Integer id) {
        WwwFamily wwwFamily =new WwwFamily();
        BeanUtils.copyProperties(familyRepository.findFamily(id),wwwFamily);
        return wwwFamily;
    }
    @Transactional(readOnly = true)
    public WwwFamily findFamilyByName(String name){
        WwwFamily wf = new WwwFamily();
        BeanUtils.copyProperties(familyRepository.findFamilyByName(name),wf);
        return wf;
    }

    @Transactional(readOnly = false)
    public void removeFamily(Integer id) {
        familyRepository.removeFamily(id);
    }
    @Transactional(readOnly = true)
    public List<WwwFamily> listAllFamilies() {
        List<FamilyEntity> listEntities = familyRepository.listAllFamilies();
        ArrayList<WwwFamily> listWww = new ArrayList<WwwFamily>(listEntities.size());
        for(FamilyEntity feListIndex: listEntities) {
            WwwFamily wfNew = new WwwFamily();
            BeanUtils.copyProperties(feListIndex , wfNew);
            listWww.add(wfNew);
        }
        return listWww;
    }
}
