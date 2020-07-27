/*
package engine.repository;

import engine.model.AnswerArray;
import engine.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class QuizRepositoryImpl implements QuizRepository<Quiz, Integer> {

    private HashMap<Integer, Quiz> quizHashMap = new HashMap<>();
    private static int id = 1;


    @Override
    public Quiz getQuizById(Integer id) {
        return quizHashMap.get(id);
    }

    @Override
    public List<Quiz> getAllQuiz() {
        List<Quiz> quizList = new ArrayList<>(quizHashMap.values());
        return quizList;
    }

    @Override
    public void addQuiz(Quiz quiz) {
        quiz.setId(id);
        quizHashMap.put(id, quiz);
        id++;
    }

    @Override
    public Boolean getAnswer(int id, AnswerArray answer) {
        Quiz result = quizHashMap.get(id);
        int[] answerArray = result.getAnswer();
        return Arrays.equals(answer.getAnswer(), answerArray);

    }

}
*/
