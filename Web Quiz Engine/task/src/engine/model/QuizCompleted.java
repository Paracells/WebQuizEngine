package engine.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QuizCompleted {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "completedAt")
    private LocalDateTime dt;

    public QuizCompleted() {
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public LocalDateTime getDt() {
        return dt;
    }

    public void setDt(LocalDateTime dt) {
        this.dt = LocalDateTime.now();
    }
}
