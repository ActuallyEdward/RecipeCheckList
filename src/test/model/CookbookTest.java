package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class CookbookTest {
    Cookbook testNewCookbook;
    Recipe testRecipe;
    Recipe testRecipeName1;
    Recipe testRecipeName2;
    String name1 = "RicePudding";
    String name2 = "BreadPudding";

    @BeforeEach
    public void runBeforeEach() {
        testNewCookbook = new Cookbook();
        testRecipe = new Recipe();
        testRecipeName1 = new Recipe();
        testRecipeName2 = new Recipe();
    }

    //Add a recipe
    @Test
    public void testAddRecipe() {
        List<Recipe> testCookbook = new ArrayList<>();
        testCookbook.add(testRecipe);
        assertEquals(testNewCookbook.addRecipe(testRecipe),testCookbook);
    }

    @Test
    public void testAddRecipeAddedTwice() {
        List<Recipe> testCookbook = new ArrayList<>();
        testCookbook.add(testRecipe);
        assertEquals(testNewCookbook.addRecipe(testRecipe),testCookbook);
        testNewCookbook.addRecipe(testRecipe);
        assertEquals(testNewCookbook.addRecipe(testRecipe),testCookbook);
    }

    //Delete a recipe that the cookbook holds
    @Test
    public void testDeleteRecipeTrue() {
        List<Recipe> testCookbook = new ArrayList<>();
        testRecipe.addName(name1);
        testCookbook.add(testRecipe);
        assertEquals(testCookbook,testNewCookbook.addRecipe(testRecipe));
        testCookbook.remove(testRecipe);
        assertEquals(testCookbook,testNewCookbook.deleteRecipe(name1));
    }


    //Delete a recipe that the cookbook does not hold
    @Test
    public void testDeleteRecipeNone() {
        List<Recipe> testCookbook = new ArrayList<>();
        assertEquals(testCookbook,testNewCookbook.deleteRecipe(name1));

    }

    //View the name of all recipes in a cookbook

    @Test
    public void testViewNameRecipe() {
        testRecipeName1.addName(name1);
        testRecipeName2.addName(name2);
        List<Recipe> result = new ArrayList<>();
        result.add(testRecipeName1);
        result.add(testRecipeName2);
        testNewCookbook.addRecipe(testRecipeName1);
        testNewCookbook.addRecipe(testRecipeName2);
        assertEquals(testNewCookbook.viewNameRecipe(),result);

    }

    //Search for the recipes that has an assigned star num in the cookbook but the cookbook has none of the required
    //recipes
    @Test
    public void testSearchNumStarsRecipeNone() throws OutOfRangeException {
        List<Recipe> testCookbook = new ArrayList<>();
        testRecipe.rate(1);
        testRecipeName1.rate(2);
        testNewCookbook.addRecipe(testRecipe);
        testNewCookbook.addRecipe(testRecipeName1);
        try {
            assertEquals(testNewCookbook.searchNumStarRecipe(4),testCookbook);
        } catch (EmptyListException e) {
            fail("No exception expected, EmptyListException thrown");
        }
    }

    @Test
    public void testSearchNumStarFailed() {
        try {
            testNewCookbook.searchNumStarRecipe(4);
        } catch (EmptyListException e) {
        }
    }


    //Search for the recipes that has an assigned star num in the cookbook and the cookbook has the required kind.
    @Test
    public void testSearchNumStarRecipeTrue() throws OutOfRangeException {
        List<Recipe> testCookbook = new ArrayList<>();
        testRecipe.rate(3);
        testRecipeName1.rate(2);
        testCookbook.add(testRecipe);
        testNewCookbook.addRecipe(testRecipe);
        testNewCookbook.addRecipe(testRecipeName1);
        try {
            assertEquals(testNewCookbook.searchNumStarRecipe(3),testCookbook);
        } catch (EmptyListException e) {
            System.out.println("No exception expected, EmptyListException thrown");
        }
    }

    @Test
    public void testSearchNumStarRecipeFail() {
        try {
            testNewCookbook.searchNumStarRecipe(3);
        } catch (EmptyListException e) {

        }

    }

    //Search for recipes that contains a given ingredient but the cookbook has none.
    @Test
    public void testSearchIngredientsRecipeFalse() {
        List<Recipe> testCookbook = new ArrayList<>();
        testRecipe.addIngredient("Thyme");
        testRecipeName2.addIngredient("Nutmeg");
        testNewCookbook.addRecipe(testRecipe);
        testNewCookbook.addRecipe(testRecipeName2);
        try {
            assertEquals(testNewCookbook.searchIngredientsRecipe("Rosemary"),testCookbook);
        } catch (EmptyListException e) {
            fail("No exception expected");
        }
    }

    //Search for recipes that contains a given ingredient and find some.
    @Test
    public void testSearchIngredientsRecipeTrue() {
        List<Recipe> testCookbook = new ArrayList<>();
        testRecipe.addIngredient("Thyme");
        testRecipeName2.addIngredient("Nutmeg");
        testNewCookbook.addRecipe(testRecipe);
        testNewCookbook.addRecipe(testRecipeName2);
        testCookbook.add(testRecipe);
        try {
            assertEquals(testNewCookbook.searchIngredientsRecipe("Thyme"),testCookbook);
        } catch (EmptyListException e) {
            fail("No exception expected");
        }
    }

    @Test
    public void testSearchIngredientFail() {
        try {
            testNewCookbook.searchIngredientsRecipe("Thyme");
        } catch (EmptyListException e) {
            System.out.println("Success");
        }
    }

    //Search for recipes that contains a given style but find none.
    @Test
    public void testSearchStyleRecipeFalse() {
        List<Recipe> testCookbook = new ArrayList<>();
        try {
            testRecipe.assignStyle("Western");
        } catch (NotTheRightStyleException e) {
            e.printStackTrace();
        }
        try {
            testRecipeName2.assignStyle("Western");
        } catch (NotTheRightStyleException e) {
            e.printStackTrace();
        }
        testNewCookbook.addRecipe(testRecipe);
        testNewCookbook.addRecipe(testRecipeName2);
        try {
            assertEquals(testNewCookbook.searchStyleRecipe("Eastern"),testCookbook);
        } catch (EmptyListException e) {
            fail("No exception expected");
        }
    }

    //Search for recipes that contains a given style but find some.
    @Test
    public void testSearchStyleRecipesTrue() {
        List<Recipe> testCookbook = new ArrayList<>();
        try {
            testRecipe.assignStyle("Western");
        } catch (NotTheRightStyleException e) {
            e.printStackTrace();
        }
        try {
            testRecipeName2.assignStyle("Western");
        } catch (NotTheRightStyleException e) {
            e.printStackTrace();
        }
        testNewCookbook.addRecipe(testRecipe);
        testNewCookbook.addRecipe(testRecipeName2);
        testCookbook.add(testRecipe);
        testCookbook.add(testRecipeName2);
        try {
            assertEquals(testNewCookbook.searchStyleRecipe("Western"),testCookbook);
        } catch (EmptyListException e) {
            fail("No exception expected");
        }
    }

    @Test
    public void testSearchStyleRecipeFail() {
        try {
            testNewCookbook.searchStyleRecipe("Western");
        } catch (EmptyListException e) {
            System.out.println("Success");
        }
    }

    //Search for recipe that has the given name but finds none in the cookbook
    @Test
    public void testFindRecipeNone() {
        testRecipeName1.addName(name1);
        testRecipeName2.addName(name2);
        testNewCookbook.addRecipe(testRecipeName1);
        testNewCookbook.addRecipe(testRecipeName2);
        assertEquals(null,testNewCookbook.findRecipe("rice"));
    }

    //Search for recipe that has the given name and finds one in the cookbook
    @Test
    public void testFindRecipeTrue() {
        testRecipeName1.addName(name1);
        testRecipeName2.addName(name2);
        testNewCookbook.addRecipe(testRecipeName1);
        testNewCookbook.addRecipe(testRecipeName2);
        assertEquals(testRecipeName1,testNewCookbook.findRecipe(name1));
    }

    //See if the collection contains a recipe
    @Test
    public void testContainRecipeFalse() {
        testRecipeName1.addName(name1);
        assertFalse(testNewCookbook.containRecipe(testRecipeName1));
    }

    @Test
    public void testContainRecipeTrue() {
        testRecipeName1.addName(name1);
        testNewCookbook.addRecipe(testRecipeName1);
        assertTrue(testNewCookbook.containRecipe(testRecipeName1));
    }


}
