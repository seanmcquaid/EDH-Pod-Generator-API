package com.edh.pod.generator.api.services;

import com.edh.pod.generator.api.models.Pod;
import com.edh.pod.generator.api.repositories.PodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PodsService {

    @Autowired
    private PodRepository podRepository;

    public List<Pod> addPodMember(Pod podInfo){
        podRepository.addPodMember(podInfo.getOwner(), podInfo.getMember(), podInfo.getMemberEmail(), podInfo.getSpellTableUrl(), podInfo.getName());
        return podRepository.getPodByOwnerAndName(podInfo.getOwner(), podInfo.getName());
    }

    public List<Pod> getPod(Pod podInfo){
        return podRepository.getPodByOwnerAndName(podInfo.getOwner(), podInfo.getName());
    }

    public List<Pod> getPods(String owner){
        return podRepository.getPods(owner);
    }

    public List<List<Pod>> sortIntoPods(List<Pod> pods){
        List<String> names = new ArrayList<>();
        List<List<Pod>> sortedPods = new ArrayList<>();
        pods.forEach(pod -> {
            if(!names.contains(pod.getName())){
                names.add(pod.getName());
            }
        });
        names.forEach(name -> {
            List<Pod> podInfoForName = pods.stream().filter(pod -> pod.getName().equals(name)).collect(Collectors.toList());
            sortedPods.add(podInfoForName);
        });
        return sortedPods;
    }
}
