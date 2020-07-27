package engine.repository;

import engine.model.QuizCompleted;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedRepository extends PagingAndSortingRepository<QuizCompleted, Long> {

    @Query("select q from QuizCompleted q where user_id=?1")
    Page<QuizCompleted> findAllByUser(long userId, Pageable page);



}
