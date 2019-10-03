package com.informatics.research.quiznav.database.model;

import java.io.Serializable;
import java.util.ArrayList;

// SQL means Students Quizes List
public class Sql implements Serializable {
    ArrayList<String> quiz_code = new ArrayList();

    public Sql(){

    }

    public Sql(ArrayList quiz_code) {
        this.quiz_code = quiz_code;
    }

    public ArrayList getQuiz_code() {
        return quiz_code;
    }

    public void setQuiz_code(ArrayList quiz_code) {
        this.quiz_code = quiz_code;
    }
}
