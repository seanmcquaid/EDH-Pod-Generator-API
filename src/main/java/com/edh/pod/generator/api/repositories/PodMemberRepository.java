package com.edh.pod.generator.api.repositories;

import com.edh.pod.generator.api.models.PodMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PodMemberRepository extends JpaRepository<PodMember, Integer>{

    @Query(value = "insert into pod_members(owner, member, member_email, name) values (:owner, :member, :member_email, :name) returning *", nativeQuery = true)
    List<PodMember> addPodMember(@Param("owner") String owner, @Param("member") String member, @Param("member_email") String member_email, @Param("name") String name);

    @Query(value = "select * from pod_members where owner = :owner", nativeQuery = true)
    List<PodMember> getPodMembers(@Param("owner") String owner);

    @Query(value = "select * from pod_members where owner = :owner and name = :name", nativeQuery = true)
    List<PodMember> getPodMembersByOwnerAndName(@Param("owner") String owner, @Param("name") String name);

    @Query(value = "delete * from pod_members where owner = :owner and name = :name", nativeQuery = true)
    List<PodMember> deletePod(@Param("owner") String owner, @Param("name") String name);

    @Query(value = "delete * from pod_members where owner = :owner and member = :member", nativeQuery = true)
    List<PodMember> deletePodMember(@Param("owner") String owner, @Param("member") String member);
}
