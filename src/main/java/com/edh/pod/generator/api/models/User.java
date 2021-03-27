package com.edh.pod.generator.api.models;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue()
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;
}
