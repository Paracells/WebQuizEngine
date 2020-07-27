package engine.repository;

import engine.model.Quiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {

    @Query(value = "select answer from quiz_answer a inner join quiz q on q.id=a.quiz_id where q.id=?1", nativeQuery = true)
    List<Integer> getAnswerById(Long id);



}