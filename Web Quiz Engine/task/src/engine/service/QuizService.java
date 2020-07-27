package engine.service;

import engine.model.AnswerArray;
import engine.model.Quiz;
import engine.model.QuizCompleted;
import engine.model.User;
import engine.repository.CompletedRepository;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private QuizRepository quizRepository;

    private UserRepository userRepository;

    private CompletedRepository completedRepository;


    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository, CompletedRepository completedRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.completedRepository = completedRepository;
    }

    public Page<Quiz> getAllQuiz(int pageStart) {

        Pageable paging = PageRequest.of(pageStart, 10);
        Page<Quiz> pagedResult = quizRepository.findAll(paging);
        return pagedResult;
    }

    public Page<Quiz> getAllQuizByTime(int pageStart) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User byEmail = userRepository.findByEmail(name);
        Long id = byEmail.getId();
        Pageable paging = PageRequest.of(pageStart, 10, Sort.by("completedAt"));
        Page<QuizCompleted> result = completedRepository.findAllByUser(id, paging);
        Page<Quiz> pagedResult = quizRepository.findAll(paging);
        return pagedResult;
    }

    public Quiz getQuizById(Long id) {
        Optional<Quiz> byId = quizRepository.findById(id);
        return byId.orElse(null);
    }

    public Quiz saveQuiz(Quiz quiz) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        quiz.setUser(name);
        quizRepository.save(quiz);
        return quiz;
    }

    public Boolean getAnswerById(Long id, AnswerArray answer) {
        List<Integer> answerById = quizRepository.getAnswerById(id);
        List<Integer> answerList = Arrays.asList(answer.getAnswer());
        return answerById.equals(answerList);
    }

    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }

}
