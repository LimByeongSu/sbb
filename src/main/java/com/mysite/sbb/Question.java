package com.mysite.sbb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    //하나의 컬럼에는 하나의 값만 들어가야한다. answers는 리스트로 여러값이니 컬럼으로 생성되지않는다.
    //그래도 DB에는 answers가 따로 테이블로 생긴다.
    //JPA는 어떻게든 DB를 사용할수 있게 만들어주기때문
    //OneToMany같은 실제 DB관계가 아니라 객체사이의 개념적인 관계로 생각하자
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    //mappedBy를 사용하면 위에서 만들어진 의미없는 추가 테이블 생성을 막을수있따.
    //Answer의 question과 연결된다는 느낌이다
    // cascade옵션은 부모(질문글)가 삭제되어 고아가 된 자식(댓글)을 부모가 삭제될때 같이 삭제되는 옵션이다.
    private List<Answer> answers = new ArrayList<>();   //1대N관계라서 answer가 여러개 일수있음

    //OneToMany는 필수는 아니다. Question은 Answer이 필수가 아니기 때문이다.
    //반대로 Answer은 Question이 필수라 ManyToOne은 필수이다.
    //다만 OneToMany를 안쓰면 객체지향적으로 코드를 작성하긴 힘들고 DB지향적으로 작성할수밖에없다.


    public void addAnswer(String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(this);
        answers.add(answer);
    }
}
