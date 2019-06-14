package com.informatics.research.quiznav.home.model;

import java.util.ArrayList;

public class SubjectModel {
    private String key, title, lecturer, year;
    private ArrayList<String> students = new ArrayList<>();
    private ArrayList<MaterialModel> materials = new ArrayList<>();

    public SubjectModel(){
    }

    public SubjectModel(String key, String title, String lecturer, String year, ArrayList<String> students) {
        this.key = key;
        this.title = title;
        this.lecturer = lecturer;
        this.year = year;
        this.students = students;
    }

    public SubjectModel(String key, String title, String lecturer, String year, ArrayList<String> students, ArrayList<MaterialModel> materials) {
        this.key = key;
        this.title = title;
        this.lecturer = lecturer;
        this.year = year;
        this.students = students;
        this.materials = materials;
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

    public ArrayList<MaterialModel> getMaterialModel() {
        return materials;
    }

    public void setMaterialModel(ArrayList<MaterialModel> materials) {
        this.materials = materials;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public Integer getStudentsCount(){
        return 1;
    }
}
