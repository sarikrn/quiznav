package com.informatics.research.quiznav.database.model;

import java.util.HashMap;

public class UserAnswer {
    private String wa, acc;
    private HashMap<String, String> answer = new HashMap<>();

    public UserAnswer() {
    }

    public UserAnswer(String wa, String acc, HashMap<String, String> answer) {
        this.wa = wa;
        this.acc = acc;
        this.answer = answer;
    }

    public String getWa() {
        return wa;
    }
    public void setWa(String wa) {
        this.wa = wa;
    }
    public String getAcc() {
        return acc;
    }
    public void setAcc(String acc) {
        this.acc = acc;
    }
    public HashMap<String, String> getAnswer() {
        return answer;
    }
    public void setAnswer(HashMap<String, String> answer) {
        this.answer = answer;
    }
}
