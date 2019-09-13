package com.informatics.research.quiznav.database.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Subjects {
    private String subject_code, title, lecturer, year, card_colour;
    private ArrayList<String> students = new ArrayList<>();
    private HashMap<String, Materials> materials = new HashMap<>();

    public Subjects(){
    }

    public Subjects(String subject_code, String title, String lecturer, String year, String card_colour, ArrayList<String> students) {
        this.subject_code = subject_code;
        this.title = title;
        this.lecturer = lecturer;
        this.year = year;
        this.card_colour = card_colour;
        this.students = students;
    }

    public Subjects(String subject_code, String title, String lecturer, String year, String card_colour, ArrayList<String> students, HashMap<String, Materials> materials) {
        this.subject_code = subject_code;
        this.title = title;
        this.lecturer = lecturer;
        this.year = year;
        this.card_colour = card_colour;
        this.students = students;
        this.materials = materials;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCard_colour() {
        return card_colour;
    }

    public void setCard_colour(String card_colour) {
        this.card_colour = card_colour;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public int getStudentsCount(){
        return this.students.size();
    }

    public HashMap<String, Materials> getMaterials() {
        return materials;
    }

    public void setMaterials(HashMap<String, Materials> materials) {
        this.materials = materials;
    }
}
