package edu.uta.sis.nagnomore.data.repository;

import edu.uta.sis.nagnomore.data.entities.LocationEntity;

/**
 * Created by Hannu Lohtander on 10.5.2016.
 */
public interface LocationRepository {

    public void save(LocationEntity locationEntity);
}
