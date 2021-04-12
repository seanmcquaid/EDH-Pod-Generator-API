package com.edh.pod.generator.api.models;

import java.util.List;

public class PlayGroup {
    private List<PodMember> playGroupMembers;


    public PlayGroup(List<PodMember> playGroupMembers) {
        this.playGroupMembers = playGroupMembers;
    }

    public List<PodMember> getPlayGroupMembers() {
        return playGroupMembers;
    }

    public void setPlayGroupMembers(List<PodMember> playGroupMembers) {
        this.playGroupMembers = playGroupMembers;
    }
}
