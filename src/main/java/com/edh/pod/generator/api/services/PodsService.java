package com.edh.pod.generator.api.services;

import com.edh.pod.generator.api.models.PlayGroup;
import com.edh.pod.generator.api.models.Pod;
import com.edh.pod.generator.api.models.PodMember;
import com.edh.pod.generator.api.repositories.PodMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PodsService {

    @Autowired
    private PodMemberRepository podMemberRepository;

    public List<PodMember> addPodMember(PodMember podMemberInfo, String ownerName){
        podMemberRepository.addPodMember(ownerName, podMemberInfo.getMember(), podMemberInfo.getMemberEmail(), podMemberInfo.getName());
        return podMemberRepository.getPodMembersByOwnerAndName(ownerName, podMemberInfo.getName());
    }

    public List<PodMember> deletePod(String ownerName, String podName){
        podMemberRepository.deletePod(ownerName, podName);
        return podMemberRepository.getPodMembersByOwnerAndName(ownerName, podName);
    }

    public List<PodMember> deletePodMember(String ownerName, String memberName, String podName){
        podMemberRepository.deletePodMember(ownerName, memberName, podName);
        return podMemberRepository.getPodMembersByOwnerAndName(ownerName, podName);
    }

    public List<PodMember> getPodMembers(String owner){
        return podMemberRepository.getPodMembers(owner);
    }

    public List<Pod> sortIntoPods(List<PodMember> podMembers){
        List<String> names = new ArrayList<>();
        List<Pod> sortedPods = new ArrayList<>();
        podMembers.forEach(podMember -> {
            if(!names.contains(podMember.getName())){
                names.add(podMember.getName());
            }
        });
        names.forEach(name -> {
            List<PodMember> podMemberInfoForName = podMembers.stream().filter(podMember -> podMember.getName().equals(name)).collect(Collectors.toList());
            Pod podInfo = new Pod(podMemberInfoForName, name);
            sortedPods.add(podInfo);
        });
        return sortedPods;
    }

    public Pod getPodByName(List<Pod> pods, String podName){
        return pods.stream().filter(pod -> pod.getPodName().equals(podName)).findFirst().orElse(null);
    }

    public List<PlayGroup> sortIntoPlayGroups(Pod pod){
        List<PlayGroup> playGroups = new ArrayList<>();
        double maxPlayGroupSize = 4;
        double amountOfPlayGroups =  Math.ceil(pod.getPodMembers().size() / maxPlayGroupSize);
        double maxPodSize = Math.ceil(pod.getPodMembers().size() / amountOfPlayGroups);
        List<PodMember> podMembers = pod.getPodMembers();
        Collections.shuffle(podMembers);
        int startingIndex = 0;
        int endingIndex = (int) (maxPodSize);

        while(playGroups.size() != amountOfPlayGroups){
            List<PodMember> podMemberList = new ArrayList<>(podMembers.subList(startingIndex, endingIndex));
            playGroups.add(new PlayGroup(podMemberList));
            startingIndex = endingIndex;
            endingIndex = (int) Math.min(startingIndex + maxPodSize, podMembers.size());
        }

        return playGroups;
    }
}
