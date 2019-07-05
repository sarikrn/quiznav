package com.informatics.research.quiznav.database.model;

import com.informatics.research.quiznav.database.model.Questions;

import java.io.Serializable;
import java.util.HashMap;

public class Quizes implements Serializable {
    private String score_average, title, key, create_at, due_to, passed_score, remidial_chance;
    private HashMap<String, Questions> questions = new HashMap<>();

    public Quizes() {
    }

    public Quizes(String score_average, String title, String key, String create_at, String due_to, String passed_score, String remidial_chance, HashMap<String, Questions> questions) {
        this.score_average = score_average;
        this.title = title;
        this.key = key;
        this.create_at = create_at;
        this.due_to = due_to;
        this.passed_score = passed_score;
        this.remidial_chance = remidial_chance;
        this.questions = questions;
    }

    public String getRemidial_chance() {
        return remidial_chance;
    }

    public void setRemidial_chance(String remidial_chance) {
        this.remidial_chance = remidial_chance;
    }

    public String getPassed_score() {
        return passed_score;
    }

    public void setPassed_score(String passed_score) {
        this.passed_score = passed_score;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore_average() {
        return score_average;
    }

    public void setScore_average(String score_average) {
        this.score_average = score_average;
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

    public HashMap<String, Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<String, Questions> questions) {
        this.questions = questions;
    }
}
