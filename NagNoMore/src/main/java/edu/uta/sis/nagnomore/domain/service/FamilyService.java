package edu.uta.sis.nagnomore.domain.service;

import edu.uta.sis.nagnomore.domain.data.WwwFamily;

import java.util.List;

/**
 * Created by Hanna on 8.6.2016.
 */
public interface FamilyService {
    public void addFamily(WwwFamily f);
    public void updateFamily(WwwFamily f);
    public WwwFamily findFamily(Integer id);
    public void removeFamily(Integer id);

    public List<WwwFamily> listAllFamilies();
}
