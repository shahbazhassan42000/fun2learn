package shahbaz4311.fun2learn.models;

import androidx.annotation.NonNull;

import java.io.DataInput;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Question implements Serializable {
    private final String question;
    private final List<String> options;
    private String correctAnswer;
    private String userAnswer="";

    private Date date;

    public Question(String question, List<String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    //constructor with all parameters
    public Question(String question, List<String> options, String correctAnswer, String userAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
    }

    public Question(String question, List<String> options, String answer, String user_answer, Date date) {
        this.question = question;
        this.options = options;
        this.correctAnswer = answer;
        this.userAnswer = user_answer;
        this.date = date;
    }

    public void setUserAnswer(String userAnswer) {
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

    //to string
    @NonNull
    @Override
    public String toString() {
        return question + "\t" +correctAnswer + "\t" + userAnswer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}