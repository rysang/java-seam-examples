package org.nuts.bolts.scheduling;

import java.util.List;

import org.nuts.bolts.scheduling.policy.SchedulePolicy;
import org.nuts.bolts.scheduling.policy.exceptions.NotEnoughNodesForPolicy;
import org.nuts.bolts.scheduling.task.Task;

public interface Scheduler {
    /**
     * Returns configured node names.
     * 
     * @return
     */
    public List<String> getNodeNames();

    /**
     * Returns connected nodes.
     * 
     * @return
     */
    public List<String> getAvailableNodes();

    /**
     * Returns connected nodes based on a policy.
     * 
     * @param schedulePolicy
     * @return
     */
    public List<String> getAvailableNodes(SchedulePolicy schedulePolicy);

    /**
     * Schdules a task
     * 
     * @param task
     */
    public void schedule(Task task) throws NotEnoughNodesForPolicy;
}
