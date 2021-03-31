package com.edh.pod.generator.api.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue()
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
