package com.informatics.research.quiznav.quizes.model;

import java.io.Serializable;
import java.util.HashMap;

public class Questions implements Serializable{
    private String key, categorize, desc;
    private HashMap<String, String> answers = new HashMap<>();

    public Questions(){
    }

    public Questions(String key, String categorize, String desc, HashMap<String, String> answers) {
        this.key = key;
        this.categorize = categorize;
        this.desc = desc;
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

    public HashMap<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, String> answers) {
        this.answers = answers;
    }
}
