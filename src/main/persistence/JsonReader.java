package persistence;

import model.Cookbook;
import model.NotTheRightStyleException;
import model.OutOfRangeException;
import model.Recipe;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

//// Represents a reader that reads Cookbook from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Cookbook from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Cookbook read() throws IOException, OutOfRangeException, NotTheRightStyleException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCookbook(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Cookbook from JSON object and returns it
    private Cookbook parseCookbook(JSONObject jsonObject) throws OutOfRangeException, NotTheRightStyleException {
        Cookbook cb = new Cookbook();
        addRecipes(cb, jsonObject);
        return cb;
    }


    // MODIFIES: cb
    // EFFECTS: parses Recipes from JSON object and adds them to Cookbook
    private void addRecipes(Cookbook cb, JSONObject jsonObject) throws OutOfRangeException, NotTheRightStyleException {
        JSONArray jsonArray = jsonObject.getJSONArray("Recipes");
        for (Object json : jsonArray) {
            JSONObject nextRecipe = (JSONObject) json;
            addRecipe(cb, nextRecipe);
        }
    }

    // MODIFIES: cb
    // EFFECTS: parses Recipe from JSON object and adds it to workroom
    private void addRecipe(Cookbook cb, JSONObject jsonObject) throws OutOfRangeException, NotTheRightStyleException {
        String name = jsonObject.getString("name");
        String style = jsonObject.getString("style");
        JSONArray ingredients = jsonObject.getJSONArray("ingredients");
        int rating = jsonObject.getInt("rating");
        Recipe recipe =  new Recipe();
        recipe.rate(rating);
        recipe.addName(name);
        recipe.assignStyle(style);

        for (Object i : ingredients) {
            recipe.addIngredient(i.toString());
        }
        cb.addRecipe(recipe);
    }


}
