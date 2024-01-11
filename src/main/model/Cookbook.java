package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.List;

public class Cookbook implements Writable {

    private List<Recipe> cookbook;
    private List<String> names;


    //Create a new CookBook that contains a list of Recipes
    public Cookbook() {
        cookbook = new ArrayList<>(20);


    }

    //MODIFIES:this
    //EFFECTS:take a name of the recipe, and delete the recipe with that given name; do nothing if the cookbook does not
    // have the recipe with that given name
    public List<Recipe> deleteRecipe(String n) {
        cookbook.removeIf(r -> r.getName().equals(n));
        return cookbook;
    }



    //MODIFIES:this
    //EFFECTS:Add a new recipe to the cookbook.
    public List<Recipe> addRecipe(Recipe r) {
        if (cookbook.contains(r)) {
            return cookbook;
        } else {
            cookbook.add(r);
            return cookbook;
        }
    }



    //EFFECTS: take a number of star[1-5], output a list of recipes with the given number of stars, if none in the book
    // contains the given number then output an empty list.
    public List<Recipe> searchNumStarRecipe(int num) throws EmptyListException {
        if (this.cookbook.size() != 0) {
            List<Recipe> result = new ArrayList<>();
            for (Recipe r : cookbook) {
                if (r.getRating() == num) {
                    result.add(r);
                }
            }

            return result;
        } else {
            throw new EmptyListException();
        }

    }


    //EFFECTS: take a Cookbook, output a list of the recipes inside this cookbook.
    public List<Recipe> viewNameRecipe() {
        List<Recipe> listRecipe = new ArrayList<>();
        for (Recipe r : cookbook) {
            listRecipe.add(r);

        }
        return listRecipe;
    }

    //REQUIRES:a non-empty CookBook.
    //EFFECTS: input the name of a recipe, output the recipe r if the recipe with given name is in the list.
    public Recipe findRecipe(String n) {
        for (Recipe r: cookbook) {
            if (r.getName().equals(n)) {
                return r;
            }
        }
        return null;
    }




    //EFFECTS:take a cookbook, and output a list of the recipes which uses a specific ingredient in the recipe,
    //if no recipe in the book uses that ingredient then output an empty list.
    public List<Recipe> searchIngredientsRecipe(String ingredient) throws EmptyListException {
        if (cookbook.size() != 0) {
            List<Recipe> result = new ArrayList<>();
            for (Recipe r : cookbook) {
                if (r.getIngredients().contains(ingredient)) {
                    result.add(r);

                }

            }
            return result;
        } else {
            throw new EmptyListException();
        }
    }


    //EFFECTS:take a cookbook, and output a list of recipes which has the same style.
    public List<Recipe> searchStyleRecipe(String style) throws EmptyListException {
        if (cookbook.size() != 0) {
            List<Recipe> result = new ArrayList<>();
            for (Recipe r : cookbook) {
                if (r.getStyle().equalsIgnoreCase(style)) {
                    result.add(r);
                }
            }

            return result;
        } else {
            throw new EmptyListException();
        }
    }

    //REQUIRES: a recipe
    //EFFECTS: see if the recipe is in my collection, if it's in return true, false if not
    public boolean containRecipe(Recipe r) {
        if (cookbook.contains(r)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Recipes", recipesToJson());
        return json;
    }

    // EFFECTS: returns Recipes in this Cookbook as a JSON array
    private JSONArray recipesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Recipe t : cookbook) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}




