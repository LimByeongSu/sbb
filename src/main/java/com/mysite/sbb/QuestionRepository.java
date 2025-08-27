package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    //제목은 중복이 가능하므로 return값이 여러개 일수 있어서 List로 반환값을 해주는게 맞다.
    //하지만 책에서는 제목이 중복되지 않는다는 가정을하므로 Question을 반환하는걸로 진행한다.
    //SELECT * FROM question WHERE question.subject = subject(입력받은거)
    Optional<Question> findBySubject(String subject);


    //SELECT * FROM question WHERE  question.subject=subject1 and question.content=subject2
    Optional<Question> findBySubjectAndContent(String subject1, String subject2);



    List<Question> findBySubjectLike(String subject);
}
