package com.example.anneh.trivia;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionItem implements Serializable {
    public String question, answer;
    public ArrayList<String> allAnswers;

    // Constructor
    public QuestionItem (String question, String answer, ArrayList <String> allAnswers) {
        this.question = question;
        this.answer = answer;
        this.allAnswers = allAnswers;
    }

    // Getters
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public ArrayList<String> getAllAnswers() {
        return allAnswers;
    }

    // Setters
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAllAnswers(ArrayList<String> wrongAnswers) {
        this.allAnswers = wrongAnswers;
    }
}
