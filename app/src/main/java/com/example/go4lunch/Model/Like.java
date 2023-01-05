package com.example.go4lunch.Model;

import java.io.Serializable;

public class Like implements Serializable {
    String workmate_id;
    Boolean like;

    public Like(String workmate_id, Boolean like) {
        this.workmate_id = workmate_id;
        this.like = like;
    }

    public String getWorkmate_id() {
        return workmate_id;
    }

    public void setWorkmate_id(String workmate_id) {
        this.workmate_id = workmate_id;
    }

    public Boolean getLike() {return like;}

    public void setLike(Boolean like) {
        this.like = like;
    }
}
