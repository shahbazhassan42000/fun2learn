package shahbaz4311.fun2learn;

import java.io.Serializable;
import java.util.List;

public class Key implements Serializable {
    private final String question;
    private final List<String> options;
    private String userAnswer;
    private String correctAnswer;

    public Key(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    public void setUserAnswer(String userAnswer){
        this.userAnswer = userAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

}
