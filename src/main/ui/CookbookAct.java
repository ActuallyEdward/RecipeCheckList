package ui;


import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.fail;

public class CookbookAct {
    private Scanner input;
    private Cookbook myCollection;
    private Recipe myRecipe;

    private static final String JSON_STORE = "./data/myFile.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS:run the cookbook application.
    public CookbookAct() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runCookbook();
    }

    //MODIFIES:this
    //EFFECTS:process user input.
    private void runCookbook() {
        boolean keepGoing = true;
        String command = null;

        myCookbook();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nSee you!");
    }


    private void myCookbook() {
        myCollection = new Cookbook();
        input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addARecipe();
        } else if (command.equals("d")) {
            deleteARecipe();
        } else if (command.equals("o")) {
            showAllRecipes();
        } else if (command.equals("i")) {
            searchByIngredients();
        } else if (command.equals("r")) {
            searchByRating();
        } else if (command.equals("s")) {
            searchByStyles();
        } else if (command.equals("f")) {
            saveCookbook();
        } else if (command.equals("h")) {
            loadCookbook();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a recipe");
        System.out.println("\td -> delete a recipe");
        System.out.println("\to -> show all recipes entered");
        System.out.println("\ti -> search recipes by ingredients");
        System.out.println("\tr -> search recipes by rating");
        System.out.println("\ts -> search recipes by styles");
        System.out.println("\tf -> save work room to file");
        System.out.println("\th -> load Cookbook from file");
        System.out.println("\tq -> quit");
    }

    //EFFECTS:add a recipe to my collection
    private void addARecipe() {
        setup();
        String style = input.next();
        try {
            myRecipe.assignStyle(style);
        } catch (NotTheRightStyleException e) {
            System.out.println("The entered Style is not Western or Eastern");
            fail();
        }
        System.out.print("Enter the Ingredient for the recipe");
        String ingredient = input.next();
        myRecipe.addIngredient(ingredient);
        System.out.print("Do you want add more ingredients? (enter yes or no)");
        process();
        System.out.print("Enter the rating for the recipe");
        try {
            myRecipe.rate(input.nextInt());
        } catch (OutOfRangeException e) {
            System.out.println("The entered value is not within the range of 1-5");
        }
        myCollection.addRecipe(myRecipe);
        System.out.print("Complete!");
    }

    //EFFECTS: delete a recipe from my collection
    private void deleteARecipe() {
        System.out.print("Enter a name of the recipe to delete");
        String toDelete = input.next();
        myCollection.deleteRecipe(toDelete);
        System.out.print("Deletion completed!");
    }

    //EFFECTS:show all the recipes entered
    private void showAllRecipes() {
        for (Recipe r: myCollection.viewNameRecipe()) {
            System.out.print(r.getName() + " ");
        }

    }


    //EFFECTS:search the recipes with the entered ingredient, and output those recipes
    private void searchByIngredients() {
        System.out.print("Enter the name of an ingredient");
        String ingredient = input.next();
        try {
            for (Recipe r: myCollection.searchIngredientsRecipe(ingredient)) {
                System.out.print(r.getName() + " ");
            }
        } catch (EmptyListException e) {
            System.out.println("There is no recipe in the collection!");
        }

    }

    //REQUIRES:input can be parsed to integer ranging from 1-5
    //EFFECTS: search the recipes in the collection with the input rating.
    private void searchByRating() {
        System.out.print("Enter a number [1-5] for rating");
        int num = input.nextInt();
        try {
            for (Recipe r : myCollection.searchNumStarRecipe(num)) {
                System.out.print(r.getName() + " ");
            }
        } catch (EmptyListException e) {
            System.out.println("There is no recipe in the collection!");
        }
    }

    //REQUIRES:input should either be Eastern or Western
    //EFFECTS: search and output the recipes with the input style
    private void searchByStyles() {
        System.out.print("Enter a Western/Eastern for style");
        String s = input.next();
        try {
            for (Recipe r : myCollection.searchStyleRecipe(s)) {
                System.out.print(r.getName() + " ");
            }
        } catch (EmptyListException e) {
            System.out.println("There is no recipe in the collection!");
        }
    }

    // EFFECTS: saves the Cookbook to file
    private void saveCookbook() {
        try {
            jsonWriter.open();
            jsonWriter.write(myCollection);
            jsonWriter.close();
            System.out.println("Saved " + "recipes" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Cookbook from file
    private void loadCookbook() {
        try {
            myCollection = jsonReader.read();
            System.out.println("Loaded " + "recipes" + " from " + JSON_STORE);
        } catch (IOException | OutOfRangeException | NotTheRightStyleException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Helper method for addARecipe
    private void setup() {
        myRecipe = new Recipe();
        System.out.print("Enter the name of the recipe");
        String name = input.next();
        myRecipe.addName(name);
        System.out.print("Enter the style of the recipe");

    }

    // Helper method for addARecipe
    private void process() {
        if (input.next().equals("yes")) {
            System.out.print("How many ingredients do you want to add");
            int i = input.nextInt();
            for (int a = 0; a < i; a++) {
                System.out.print("Enter the Ingredient for the recipe");
                myRecipe.addIngredient(input.next());
            }
        }
    }



}
