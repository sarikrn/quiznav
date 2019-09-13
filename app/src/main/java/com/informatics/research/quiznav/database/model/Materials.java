package com.informatics.research.quiznav.database.model;

import java.io.Serializable;

public class Materials implements Serializable {
    private String material_code, desc, title;

    public Materials(){
    }

    public Materials(String material_code, String desc, String title){
        this.material_code = material_code;
        this.desc = desc;
        this.title = title;
    }

    public String getMaterial_code() {
        return material_code;
    }

    public void setMaterial_code(String material_code) {
        this.material_code = material_code;
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
