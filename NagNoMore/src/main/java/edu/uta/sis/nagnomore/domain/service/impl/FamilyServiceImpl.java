package edu.uta.sis.nagnomore.domain.service.impl;

import edu.uta.sis.nagnomore.data.entities.FamilyEntity;
import edu.uta.sis.nagnomore.data.repository.FamilyRepository;
import edu.uta.sis.nagnomore.domain.data.WwwFamily;
import edu.uta.sis.nagnomore.domain.service.FamilyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hanna on 8.6.2016.
 */
@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    FamilyRepository familyRepository;

    public void addFamily(WwwFamily f) {
        FamilyEntity fe = new FamilyEntity();
        BeanUtils.copyProperties(f,fe);
        familyRepository.addFamily(fe);
    }

    public void updateFamily(WwwFamily f) {
        FamilyEntity fe = familyRepository.findFamily(f.getId());
        BeanUtils.copyProperties(f,fe);
        familyRepository.updateFamily(fe);
    }
    public WwwFamily findFamily(Integer id) {
        WwwFamily wwwFamily =new WwwFamily();
        BeanUtils.copyProperties(familyRepository.findFamily(id),wwwFamily);
        return wwwFamily;
    }
    public void removeFamily(Integer id) {
        familyRepository.removeFamily(id);
    }
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
