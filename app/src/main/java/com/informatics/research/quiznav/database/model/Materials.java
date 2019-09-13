package com.informatics.research.quiznav.database.model;

import java.io.Serializable;

public class Materials implements Serializable {
    private String materials_code, desc, title;

    public Materials(){
    }

    public Materials(String materials_code, String desc, String title){
        this.materials_code = materials_code;
        this.desc = desc;
        this.title = title;
    }

    public String getMaterials_code() {
        return materials_code;
    }

    public void setMaterials_code(String materials_code) {
        this.materials_code = materials_code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
