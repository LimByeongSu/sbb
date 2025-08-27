package com.mysite.sbb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany
    //하나의 컬럼에는 하나의 값만 들어가야한다. answers는 리스트로 여러값이니 컬럼으로 생성되지않는다.
    //그래도 DB에는 answers가 따로 테이블로 생긴다.
    //JPA는 어떻게든 DB를 사용할수 있게 만들어주기때문
    //OneToMany같은 실제 DB관계가 아니라 객체사이의 개념적인 관계로 생각하자
    private List<Answer> answers;   //1대N관계라서 answer가 여러개 일수있음

}
