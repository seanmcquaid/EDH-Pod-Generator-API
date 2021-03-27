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
}
