package org.flex.test.rest;

import java.util.Collection;
import java.util.HashSet;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusInfoBean {

    public String status = "Idle";
    public int tonerRemaining = 25;
    public final Collection<JobInfoBean> jobs = new HashSet<JobInfoBean>();
}

