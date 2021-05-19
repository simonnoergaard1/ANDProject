package com.example.andproject.models;

public class Recipe {

    public String recipe, recipeID;

    public Recipe(){

    }

    public Recipe(String recipeID, String recipe){
        this.recipeID = recipeID;
        this.recipe = recipe;

    }

    public String getRecipeID() {
        return recipeID;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }


}
