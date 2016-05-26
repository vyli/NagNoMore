package edu.uta.sis.nagnomore.data.repository;

import edu.uta.sis.nagnomore.data.entities.FileMetaDataEntity;
import edu.uta.sis.nagnomore.data.entities.UserEntity;

import java.util.List;

/**
 * Created by Hannu Lohtander on 4.4.2016.
 */
public interface FileMetaRepository {

    public FileMetaDataEntity createMetaData(String oldName, String newName, long size, UserEntity owner);

    public List<FileMetaDataEntity> getList();

}
