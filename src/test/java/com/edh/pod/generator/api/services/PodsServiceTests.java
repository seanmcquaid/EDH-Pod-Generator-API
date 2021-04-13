package com.edh.pod.generator.api.services;

import com.edh.pod.generator.api.models.PlayGroup;
import com.edh.pod.generator.api.models.Pod;
import com.edh.pod.generator.api.models.PodMember;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
public class PodsServiceTests {
    private PodsService podsService;

    public PodsServiceTests() {
        this.podsService = new PodsService();
    }

    @Test
    public void sortIntoPodsTest(){
        List<PodMember> podMembers = new ArrayList<>();

        PodMember podMemberOneMember = new PodMember();
        podMemberOneMember.setId(1);
        podMemberOneMember.setOwner("sean");
        podMemberOneMember.setMember("terrell");
        podMemberOneMember.setMemberEmail("memberemail@gmail.com");
        podMemberOneMember.setName("name1");

        PodMember podMemberTwoMember = new PodMember();
        podMemberTwoMember.setId(1);
        podMemberTwoMember.setOwner("sean");
        podMemberTwoMember.setMember("victor");
        podMemberTwoMember.setMemberEmail("memberemail@gmail.com");
        podMemberTwoMember.setName("name2");

        podMembers.add(podMemberOneMember);
        podMembers.add(podMemberTwoMember);

        assertEquals(podsService.sortIntoPods(podMembers).size(), 2);
    }

    @Test
    public void getPodByNameTest(){
        List<Pod> pods = new ArrayList<>();
        List<PodMember> podMembers = new ArrayList<>();

        PodMember podMemberOneMember = new PodMember();
        podMemberOneMember.setId(1);
        podMemberOneMember.setOwner("sean");
        podMemberOneMember.setMember("terrell");
        podMemberOneMember.setMemberEmail("memberemail@gmail.com");
        podMemberOneMember.setName("name1");

        podMembers.add(podMemberOneMember);
        Pod pod = new Pod(podMembers, "name1");
        pods.add(pod);

        assertNotEquals(podsService.getPodByName(pods, "name1"), null);
    }

    @Test
    public void sortIntoPlayGroupsTest(){
        Pod pod = createPod(10);

        List<PlayGroup> playGroups = podsService.sortIntoPlayGroups(pod);

        assertEquals(podsService.sortIntoPlayGroups(pod).size(), 3);
        assertEquals(podsService.sortIntoPlayGroups(pod).get(0).getPlayGroupMembers().size(), 4);
        assertEquals(podsService.sortIntoPlayGroups(pod).get(1).getPlayGroupMembers().size(), 4);
        assertEquals(podsService.sortIntoPlayGroups(pod).get(2).getPlayGroupMembers().size(), 2);
    }

    private Pod createPod(int numberOfMembers){
        List<PodMember> podMembers = new ArrayList<>();
        for(int i = 0; i < numberOfMembers; i++){
            PodMember podMember = new PodMember();
            podMember.setMember("Pod Member " + (i + 1));
            podMembers.add(podMember);
        }
        return new Pod(podMembers, "Pod1");
    }
}
