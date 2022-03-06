package com.dahye.myhome.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2, max=30, message = "2> And <30")
    private String title;
    private String content;

    @ManyToOne //,referencedColumnName = "id" 는 User 테이블에 PK 라서 생략 가능
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
