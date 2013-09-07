package org.nuts.bolts.scheduling.policy;

import java.util.List;

public interface PolicyManager {
    public boolean acceptTask(SchedulePolicy policy);

    public List<SchedulePolicy> getPolicies();
}
