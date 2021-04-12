package com.edh.pod.generator.api.models;

import javax.persistence.*;

@Entity
@Table(name = "pod_members")
public class PodMember {
    @Id
    @GeneratedValue()
    private Integer id;

    @Column
    private String owner;

    @Column
    private String member;

    @Column
    private String memberEmail;

    @Column
    private String name;

    public Integer getId() {
        return id;
    }

    public String getMember() {
        return member;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
