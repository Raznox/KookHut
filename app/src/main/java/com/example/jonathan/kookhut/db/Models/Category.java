package com.example.jonathan.kookhut.db.Models;

/**
 * Created by jonat on 2/09/2015.
 */
public class Category {
    public Integer id;
    public String name;
    public String imgUrl;

    public Category(){}

    public Category(Integer id, String name, String imgUrl){
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

}
