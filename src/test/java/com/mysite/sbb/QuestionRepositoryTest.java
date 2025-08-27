package com.mysite.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
    @DisplayName("답변(answer) 데이터 저장")
    void t8() {
        Question q = this.questionRepository.findById(2L).get();


        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
                            //DB적으로 보면 answer와 question이 관계를 가진다.

        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }


}
