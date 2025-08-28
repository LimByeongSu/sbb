package com.mysite.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class QuestionRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("findAll")
    void t1() {
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }


    @Test
    @DisplayName("findById")
    void t2() {
        Optional<Question> op = this.questionRepository.findById(1L);

        if(op.isPresent()) {
            Question q = op.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }

    }


    @Test
    @DisplayName("findBySubject")
    void t3() {
        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?").get();
        assertEquals(1, q.getId());

    }


    @Test
    @DisplayName("findBySubjectAndContent")
    void t4() {
        Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.").get();
        assertEquals(1, q.getId());

    }


    @Test
    @DisplayName("findBySubjectLike")
    void t5() {
        //%sbb(앞에 뭐가있던 끝이 sbb여야함)도 가능하고 %sbb%(앞뒤에 뭐가 있고 사이에 sbb가 있어야함)도 가능
        List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());

    }

    @Test
    @DisplayName("데이터 수정")
    void t6() {
        Optional<Question> oq = this.questionRepository.findById(1L);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        this.questionRepository.save(q);

        Question q1 = this.questionRepository.findById(1L).get();
        assertEquals("수정된 제목", q1.getSubject());
    }

    @Test
    @DisplayName("데이터 삭제")
    void t7() {
        assertEquals(2, this.questionRepository.count());   //기본 데이터2개를 넣어놓은걸 확인하는 중
        Optional<Question> oq = this.questionRepository.findById(1L);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        this.questionRepository.delete(q);  //1개 삭제
        assertEquals(1, this.questionRepository.count());
    }


    @Test
    @DisplayName("답변(answer) 데이터 생성 - repository버전")
    void t8() {
        Question q = this.questionRepository.findById(2L).get();


        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
                            //DB적으로 보면 answer와 question이 관계를 가진다.

        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }

    @Test
    @DisplayName("답변(answer) 데이터 생성 - OneToMany버전")
    @Transactional  //왜 이 테스트에만 @Transactional 넣었는가 
                    // ->
    @Rollback(false)   //JPA는 트랜잭션일 경우 @Rollback을 사용하면 결과를 DB에 반영을 시키지 않는다.
                        //그래서 insert문이 나가지 않았던 것이고 이 테스트케이스에선 false로 해야한다.
    void t9() {
        Question question5 = questionRepository.findById(2L).get();

        question5.addAnswer("네 자동으로 생성됩니다.");
        //insert가 일어나지 않음 -> repository로 저장하지 않았기 때문
        //클래스의 일반 속성은 변경이 일어나면 자동으로 반영을 한다.
        //하지만 리스트는 add, remove를 해도 리스트(주소값)는 그대로이기 때문에 더티체킹이 안되는 것이다.
        //리스트의 내부 값의 변화를 인지하고 더티체킹을 하길 원한다면 OneToMany옵션에 CascadeType.PERSIST를 추가해야함
    }
}
