package edu.uta.sis.nagnomore.domain.service;

import edu.uta.sis.nagnomore.data.entities.FileMetaDataEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Hannu Lohtander on 3.4.2016.
 */
public interface FileDownloadService {

    public HashMap<String,String> getFileList();

    public List<FileMetaDataEntity> getFileMetadata();

}
