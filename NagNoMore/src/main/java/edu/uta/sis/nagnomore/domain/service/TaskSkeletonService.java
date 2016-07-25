package edu.uta.sis.nagnomore.domain.service;

import edu.uta.sis.nagnomore.domain.data.Task;
import edu.uta.sis.nagnomore.domain.data.TaskSkeleton;

/**
 * Created by mare on 25.7.2016.
 */
public interface TaskSkeletonService {

    public Task convertToTask(TaskSkeleton ts);

}
