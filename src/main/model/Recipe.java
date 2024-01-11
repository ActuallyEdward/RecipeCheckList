package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe implements Writable {
    private List<String> ingredients;
    private String name;
    private String style;
    private int rating;

 //A eligible recipe contains a name, Ingredients, a style(Eastern/Western), Rating (how many stars)
    public Recipe() {
        name = "";
        style = "";
        rating = 0;
        ingredients = new ArrayList<>();

    }





    //EFFECTS:get the name of the recipe
    public String getName() {
        //stub
        return name;
    }


    //EFFECTS:get the style of the recipe
    public String getStyle() {
        //stub
        return style;
    }

    //EFFECTS:get the ingredients for the recipe.
    public List<String> getIngredients() {
        //stub
        return ingredients;
    }

    //EFFECTS:get the rating for the ingredient.
    public int getRating() {
        //stub
        return rating;
    }

    //MODIFIES: this
    //EFFECTS: update the name for the recipe
    public String addName(String name) {
        this.name = name;
        return name;

    }

    //MODIFIES: this
    //EFFECTS: add an ingredient to a list of ingredients.
    public List<String> addIngredient(String ind) {
        ingredients.add(ind);
        return ingredients;
    }


    //MODIFIES:this
    //EFFECTS: assign a style (Eastern/Western) to the recipe
    public void assignStyle(String style) throws NotTheRightStyleException {
        if (style.equals("Eastern")  || style.equals("Western")) {
            this.style = style;
        } else {
            throw new NotTheRightStyleException();
        }
    }

    //REQUIRES: 1<=i<=5
    //MODIFIES:this
    //EFFECTS: rate this recipe [1-5]
    public int rate(int s) throws OutOfRangeException {
        if (s >= 1 && s <= 5) {
            rating = s;
            return rating;
        } else {
            throw new OutOfRangeException();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("style", style);
        json.put("ingredients", ingredients);
        json.put("rating", rating);
        return json;
    }


}
