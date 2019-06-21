package com.informatics.research.quiznav.quizes.model;

import java.util.HashMap;

public class Result {
    private String start, finish, subject, material, quiz_code, scores, status;
    private HashMap<String, UserAnswer> data = new HashMap<>();

    public Result() {
    }

    public Result(String start, String finish, String subject, String material, String quiz_code, String scores, String status, HashMap<String, UserAnswer> data) {
        this.start = start;
        this.finish = finish;
        this.subject = subject;
        this.material = material;
        this.quiz_code = quiz_code;
        this.scores = scores;
        this.status = status;
        this.data = data;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getQuiz_code() {
        return quiz_code;
    }

    public void setQuiz_code(String quiz_code) {
        this.quiz_code = quiz_code;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, UserAnswer> data() {
        return data;
    }

    public void setUserAnswerHashMap(HashMap<String, UserAnswer> data) {
        this.data = data;
    }
}
