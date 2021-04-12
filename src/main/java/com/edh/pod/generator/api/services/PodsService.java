package com.edh.pod.generator.api.services;

import com.edh.pod.generator.api.models.PodMember;
import com.edh.pod.generator.api.repositories.PodMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PodsService {

    @Autowired
    private PodMemberRepository podMemberRepository;

    public List<PodMember> addPodMember(PodMember podMemberInfo, String ownerName){
        podMemberRepository.addPodMember(ownerName, podMemberInfo.getMember(), podMemberInfo.getMemberEmail(), podMemberInfo.getName());
        return podMemberRepository.getPodByOwnerAndName(ownerName, podMemberInfo.getName());
    }

    public List<PodMember> getPod(PodMember podMemberInfo){
        return podMemberRepository.getPodByOwnerAndName(podMemberInfo.getOwner(), podMemberInfo.getName());
    }

    public List<PodMember> getPods(String owner){
        return podMemberRepository.getPods(owner);
    }

    public List<List<PodMember>> sortIntoPods(List<PodMember> podMembers){
        List<String> names = new ArrayList<>();
        List<List<PodMember>> sortedPods = new ArrayList<>();
        podMembers.forEach(podMember -> {
            if(!names.contains(podMember.getName())){
                names.add(podMember.getName());
            }
        });
        names.forEach(name -> {
            List<PodMember> podMemberInfoForName = podMembers.stream().filter(podMember -> podMember.getName().equals(name)).collect(Collectors.toList());
            sortedPods.add(podMemberInfoForName);
        });
        return sortedPods;
    }
}
