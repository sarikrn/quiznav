package com.informatics.research.quiznav.database.model;

import com.informatics.research.quiznav.database.model.Questions;

import java.io.Serializable;
import java.util.HashMap;

public class Quizes implements Serializable {
    private String score_average, title, quiz_code, create_at, due_to, chance;
    private HashMap<String, Questions> questions = new HashMap<>();

    public Quizes() {
    }

    public Quizes(String score_average, String title, String quiz_code, String create_at, String due_to, String chance, HashMap<String, Questions> questions) {
        this.score_average = score_average;
        this.title = title;
        this.quiz_code = quiz_code;
        this.create_at = create_at;
        this.due_to = due_to;
        this.chance = chance;
        this.questions = questions;
    }

    public String getScore_average() {
        return score_average;
    }

    public void setScore_average(String score_average) {
        this.score_average = score_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuiz_code() {
        return quiz_code;
    }

    public void setQuiz_code(String quiz_code) {
        this.quiz_code = quiz_code;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getDue_to() {
        return due_to;
    }

    public void setDue_to(String due_to) {
        this.due_to = due_to;
    }

    public String getChance() {
        return chance;
    }

    public void setChance(String chance) {
        this.chance = chance;
    }

    public HashMap<String, Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<String, Questions> questions) {
        this.questions = questions;
    }
}
