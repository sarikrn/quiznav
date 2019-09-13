package com.informatics.research.quiznav.database.model;

import java.io.Serializable;
import java.util.HashMap;

public class Questions implements Serializable{

    private String key, categorize, desc, question_code, point;
    private HashMap<String, String> answers = new HashMap<>();

    public Questions(){
    }

    public Questions(String key, String categorize, String desc, String question_code, String point, HashMap<String, String> answers) {
        this.key = key;
        this.categorize = categorize;
        this.desc = desc;
        this.question_code = question_code;
        this.point = point;
        this.answers = answers;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategorize() {
        return categorize;
    }

    public void setCategorize(String categorize) {
        this.categorize = categorize;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQuestion_code() {
        return question_code;
    }

    public void setQuestion_code(String question_code) {
        this.question_code = question_code;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public HashMap<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, String> answers) {
        this.answers = answers;
    }
}
