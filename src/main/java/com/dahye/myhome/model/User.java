package com.dahye.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>(); //Set 도 List 대신 사용 가능

    //@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true) //board class 의 변수명
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY) //board class 의 변수명
    //LAZY 대신 EAGER 로 하면 JsonIgnore임에도 Board 를 가져오고 Left Outer Join이 된다.
    //@JsonIgnore
    private List<Board> boards = new ArrayList<>();
}
