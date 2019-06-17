package com.informatics.research.quiznav.quizes.model;

import java.io.Serializable;
import java.util.HashMap;

public class Quizes implements Serializable {
    private String score_average, title, key, create_at, due_to;
    private HashMap<String, Questions> questions = new HashMap<>();

    public Quizes() {
    }

//    public Quizes(String score_average, String title, String key, String create_at, String due_to, ArrayList<Questions> questions) {
//        this.score_average = score_average;
//        this.title = title;
//        this.key = key;
//        this.create_at = create_at;
//        this.due_to = due_to;
//        this.questions = questions;
//    }


    public Quizes(String score_average, String title, String key, String create_at, String due_to, HashMap<String, Questions> questions) {
        this.score_average = score_average;
        this.title = title;
        this.key = key;
        this.create_at = create_at;
        this.due_to = due_to;
        this.questions = questions;
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
//
//    public ArrayList<Questions> getQuestions() {
//        return questions;
//    }
//
//    public void setQuestions(ArrayList<Questions> questions) {
//        this.questions = questions;
//    }

    public HashMap<String, Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<String, Questions> questions) {
        this.questions = questions;
    }
}
