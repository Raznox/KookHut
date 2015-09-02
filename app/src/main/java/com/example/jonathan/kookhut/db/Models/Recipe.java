package com.example.jonathan.kookhut.db.Models;

import java.util.List;

/**
 * Created by jonat on 2/09/2015.
 */
public class Recipe {
    public Integer id;
    public String name;
    public String imageUrl;
    public String preperation;
    public List<String> ingrediënts;

    public Recipe(){}

    public Recipe(Integer id, String name, String imgUrl, String preperation, List<String> ingredients){
        this.id = id;
        this.name = name;
        this.imageUrl = imgUrl;
        this.preperation = preperation;
        this.ingrediënts = ingredients;
    }
}
