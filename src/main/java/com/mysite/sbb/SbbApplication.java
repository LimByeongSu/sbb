package com.mysite.sbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbbApplication {

	public static void main(String[] args) {
		//Answer a = new Answer();

        //Question question = a.getQuestion();
        //question.getContent();

        //5번 질문글을 조회
        //Question q5 = questionRepository.findById(5).get();

        //5번 글에 대한 답글 조회
        //answerRepository.findAll()  -> X
         //answerRepository.findById(5) -> X -> 이건 id가 5번인 답글을 가져오는거지 5번 글에 대한 답글을 가져오는게 아님
         //answerRepository.findQuestion(q5) => O
        //List<Answer> answers = answerRepository.findQuestionId(5) => O
        //위 방법들은 DB적이고 객체적인 방법들도 있다.

        //객체적인 방법은
        //List<Answer> answers =  q5.getAnswers();


        //질문을 조회하는 방법

        SpringApplication.run(SbbApplication.class, args);
	}

}
