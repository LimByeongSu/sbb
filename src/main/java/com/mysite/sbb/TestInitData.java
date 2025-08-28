package com.mysite.sbb;


import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;


//@Profile("test")    //이건 테스트할때 test설정을 쓰는거고 테스트가 끝나면 사라지게된다.
//그래서 실제 개발을 할때는 @Profile("test")를 업애주면된다.
@Configuration
@RequiredArgsConstructor
public class TestInitData {

    @Autowired
    @Lazy
    private TestInitData self;
    private final QuestionRepository questionRepository;

    @Bean
    ApplicationRunner initDataRunner() {
        return args -> {

            if(this.questionRepository.count() > 0) {
                //System.out.println("test data is 충분");
                return;
            }

            //System.out.println("test data is not 충분");

            Question q1 = new Question();
            q1.setSubject("sbb가 무엇인가요?");
            q1.setContent("sbb에 대해서 알고 싶습니다.");
            q1.setCreateDate(LocalDateTime.now());
            this.questionRepository.save(q1);  // 첫번째 질문 저장

            Question q2 = new Question();
            q2.setSubject("스프링부트 모델 질문입니다.");
            q2.setContent("id는 자동으로 생성되나요?");
            q2.setCreateDate(LocalDateTime.now());
            q2.addAnswer("This is automatic answer");
            this.questionRepository.save(q2);  // 두번째 질문 저장
        };
    }
}