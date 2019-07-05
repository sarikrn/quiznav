package com.informatics.research.quiznav.database.model;

import java.io.Serializable;

public class Result implements Serializable {
    private String last_modified, subject, material, quiz_code, scores, quiz_status, result_status, trying_count;
    private UserAnswer data;

    public Result() {
    }

    public Result(String last_modified, String subject, String material, String quiz_code, String scores, String quiz_status, String result_status, String trying_count, UserAnswer data) {
        this.last_modified = last_modified;
        this.subject = subject;
        this.material = material;
        this.quiz_code = quiz_code;
        this.scores = scores;
        this.quiz_status = quiz_status;
        this.result_status = result_status;
        this.trying_count = trying_count;
        this.data = data;
    }

    public String getTrying_count() {
        return trying_count;
    }

    public void setTrying_count(String trying_count) {
        this.trying_count = trying_count;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getQuiz_status() {
        return quiz_status;
    }

    public void setQuiz_status(String quiz_status) {
        this.quiz_status = quiz_status;
    }

    public String getResult_status() {
        return result_status;
    }

    public void setResult_status(String result_status) {
        this.result_status = result_status;
    }

    public UserAnswer getData() {
        return data;
    }

    public void setData(UserAnswer data) {
        this.data = data;
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
}
