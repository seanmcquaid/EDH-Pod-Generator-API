package com.edh.pod.generator.api.services;

import com.edh.pod.generator.api.models.Pod;
import com.edh.pod.generator.api.repositories.PodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PodsService {

    @Autowired
    private PodRepository podRepository;

    public List<Pod> addPodMember(Pod podInfo){
        podRepository.addPodMember(podInfo.getOwner(), podInfo.getMember(), podInfo.getMemberEmail(), podInfo.getSpellTableUrl(), podInfo.getName());
        return podRepository.getPod(podInfo.getOwner(), podInfo.getName());
    }

    public List<Pod> getPods(String owner, String name){
        return podRepository.getPod(owner, name);
    }
}
