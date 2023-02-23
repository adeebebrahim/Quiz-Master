package com.example.quizmaster;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Question implements Serializable {
    private Map<String, String> options;
    public String correct_answer;
    private String category;
    public String question_text;

    public Question() {
        // Default constructor required for calls to DataSnapshot.getValue(Question.class)
    }

    public Question(String category, String question_text, String correct_answer, Map<String, String> options) {
        this.category = category;
        this.question_text = question_text;
        this.correct_answer = correct_answer;
        this.options = options;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestionText() {
        return question_text;
    }

    public void setQuestionText(String question_text) {
        this.question_text = question_text;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }

    public void setCorrectAnswer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public String getOption1() {
        return options.get("option1");
    }

    public String getOption2() {
        return options.get("option2");
    }

    public String getOption3() {
        return options.get("option3");
    }

    public String getOption4() {
        return options.get("option4");
    }

    public String getAnswer() {
        return correct_answer;
    }
}
