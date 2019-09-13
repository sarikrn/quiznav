package com.informatics.research.quiznav.database.model;

import java.io.Serializable;

public class Result implements Serializable {
    private String last_modified, subject_code, material_code, quiz_code, score, quiz_status, result_status, trying_count, remidial_status;
    private UserAnswer data;

    public Result() {
    }

    public Result(String last_modified, String subject_code, String material_code, String quiz_code, String score, String quiz_status, String result_status, String trying_count, String remidial_status, UserAnswer data) {
        this.last_modified = last_modified;
        this.subject_code = subject_code;
        this.material_code = material_code;
        this.quiz_code = quiz_code;
        this.score = score;
        this.quiz_status = quiz_status;
        this.result_status = result_status;
        this.trying_count = trying_count;
        this.remidial_status = remidial_status;
        this.data = data;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getMaterial_code() {
        return material_code;
    }

    public void setMaterial_code(String material_code) {
        this.material_code = material_code;
    }

    public String getQuiz_code() {
        return quiz_code;
    }

    public void setQuiz_code(String quiz_code) {
        this.quiz_code = quiz_code;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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

    public String getTrying_count() {
        return trying_count;
    }

    public void setTrying_count(String trying_count) {
        this.trying_count = trying_count;
    }

    public String getRemidial_status() {
        return remidial_status;
    }

    public void setRemidial_status(String remidial_status) {
        this.remidial_status = remidial_status;
    }

    public UserAnswer getData() {
        return data;
    }

    public void setData(UserAnswer data) {
        this.data = data;
    }
}
