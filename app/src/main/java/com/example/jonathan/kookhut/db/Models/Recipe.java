package com.example.jonathan.kookhut.db.Models;

import java.util.List;

/**
 * Created by jonat on 2/09/2015.
 */
public class Recipe {
    public Integer id;
    public String name;
    public String imgUrl;
    public String preparation;
    public Integer people;
    public String time;
    public List<String> ingrediënts;

    public Recipe(){}

    public Recipe(Integer id, String name, String imgUrl, String preparation, Integer people, String time, List<String> ingredients){
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.preparation = preparation;
        this.people = people;
        this.time = time;
        this.ingrediënts = ingredients;
    }

    public String GetIngredients(){
        String formattedIngredients = "Ingredienten:\n";
        for (String ingredient:this.ingrediënts) {
            formattedIngredients += ingredient + "\n";
        }
        return formattedIngredients;
    }
}
