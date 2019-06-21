package com.informatics.research.quiznav.quizes.model;

import java.util.ArrayList;

public class UserAnswer {
    private String wa, acc;
    private ArrayList<String> answer = new ArrayList<>();

    public UserAnswer() {
    }

    public UserAnswer(String wa, String acc, ArrayList<String> answer) {
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

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }
}
