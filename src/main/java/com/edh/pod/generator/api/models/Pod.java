package com.edh.pod.generator.api.models;

import javax.persistence.*;

@Entity
@Table(name = "pod")
public class Pod {
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
    private String spellTableUrl;

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

    public String getSpellTableUrl() {
        return spellTableUrl;
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

    public void setSpellTableUrl(String spellTableUrl) {
        this.spellTableUrl = spellTableUrl;
    }
}
