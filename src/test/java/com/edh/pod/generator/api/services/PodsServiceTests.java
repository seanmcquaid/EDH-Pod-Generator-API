package com.edh.pod.generator.api.services;

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

    }
}
