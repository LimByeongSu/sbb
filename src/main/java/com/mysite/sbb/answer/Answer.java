package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne      //답글(N)을 기준으로 ManyToONe이다.
    private Question question;  //DB관점으로 보면 하나이상의 정보를 가진 객체(@Entity)는 속성(외래키)으로 쓸 수 없다.
                            //자바 객체지향적인 관점에선 객체로 속성을 보는게 문제가 없다.
                            //이런 관점 차이를 JPA가 맞춰줄 것이다. 대신 두 객체간의 관계를 설정해 주어야 한다.
                            //하나의 질문(1)에 여러 답글(N)이 달릴수있다.
}

