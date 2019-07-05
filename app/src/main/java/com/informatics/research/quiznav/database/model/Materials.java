package com.informatics.research.quiznav.database.model;

import java.io.Serializable;

public class Materials implements Serializable {
    private String key, desc, title;

    public Materials(){
    }

    public Materials(String key, String desc, String title){
        this.key = key;
        this.desc = desc;
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
