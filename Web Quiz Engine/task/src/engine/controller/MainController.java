package engine.controller;

import engine.model.Answer;
import engine.model.AnswerArray;
import engine.model.Quiz;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping(value = "/api")
public class MainController {

    private Answer answer;

    private QuizService quizService;


    @Autowired
    public MainController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable("id") Long id) {
        Quiz quizById = quizService.getQuizById(id);
        if (quizById == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(quizById);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<Page<Quiz>> getAllQuiz(@RequestParam(value = "page", required = false) int page) {
        Page<Quiz> allQuiz = quizService.getAllQuiz(page);
        return ResponseEntity.ok().body(allQuiz);
    }

    @GetMapping("/quizzes/completed")
    public ResponseEntity<Page<Quiz>> getAllQuizByTime(@RequestParam(value = "page", required = false) int page) {
        Page<Quiz> allQuizByTime = quizService.getAllQuizByTime(page);
        return ResponseEntity.ok().body(allQuizByTime);
    }

    @PostMapping("/quizzes")
    public ResponseEntity<Quiz> createQuiz(@RequestBody @Valid Quiz quiz) {
        quizService.saveQuiz(quiz);
        return ResponseEntity.ok().body(quiz);
    }


    @PostMapping("/quizzes/{id}/solve")
    public ResponseEntity getAnswer(@PathVariable("id") Long id, @RequestBody(required = false) AnswerArray answer) {
        ResponseEntity<Quiz> quizById = getQuizById(id);
        if (answer == null) {
            return ResponseEntity.ok().body(new Answer(true, "Congratulations, you're right!"));

        } else if (quizById.getStatusCode().is2xxSuccessful()) {
            Boolean result = quizService.getAnswerById(id, answer);
            if (result) {
                return ResponseEntity.ok().body(new Answer(true, "Congratulations, you're right!"));
            } else
                return ResponseEntity.ok().body(new Answer(false, "Wrong answer! Please, try again."));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable("id") Long id) {

        String currentLoggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Quiz currentQuiz = quizService.getQuizById(id);
        if (currentQuiz != null) {
            String currentUserFromDb = currentQuiz.getUser();

            if (currentLoggedUser.equals(currentUserFromDb)) {
                quizService.deleteQuizById(id);
                return ResponseEntity.noContent().build();
            } else
                return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }
}
