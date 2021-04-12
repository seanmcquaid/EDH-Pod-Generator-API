package com.edh.pod.generator.api.models;

import java.util.List;

public class Pod {
    private List<PodMember> podMembers;
    private String podName;

    public Pod(List<PodMember> podMembers, String podName) {
        this.podMembers = podMembers;
        this.podName = podName;
    }

    public String getPodName() {
        return podName;
    }

    public List<PodMember> getPodMembers() {
        return podMembers;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public void setPodMembers(List<PodMember> podMembers) {
        this.podMembers = podMembers;
    }
}
