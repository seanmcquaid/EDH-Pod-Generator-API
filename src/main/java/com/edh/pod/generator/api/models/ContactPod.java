package com.edh.pod.generator.api.models;

import java.util.List;

public class ContactPod {
    private List<String> spellTableUrls;
    private List<PlayGroup> playGroups;

    public ContactPod(List<String> spellTableUrls, List<PlayGroup> playGroups) {
        this.spellTableUrls = spellTableUrls;
        this.playGroups = playGroups;
    }

    public List<String> getSpellTableUrls() {
        return spellTableUrls;
    }

    public void setSpellTableUrls(List<String> spellTableUrls) {
        this.spellTableUrls = spellTableUrls;
    }

    public List<PlayGroup> getPlayGroups() {
        return playGroups;
    }

    public void setPlayGroups(List<PlayGroup> playGroups) {
        this.playGroups = playGroups;
    }
}
