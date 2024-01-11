package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    //provide tests for recipes in this class

    Recipe testRecipe;

    @BeforeEach
    public void runBeforeEachOne() {
        testRecipe = new Recipe();
    }

    @Test
    public void testGetName() {
        assertEquals(testRecipe.getName(),"");
    }

    @Test
    public void testGetRating() {
        assertEquals(testRecipe.getRating(),0);
    }

    @Test
    public void testGetStyle() {
        assertEquals(testRecipe.getStyle(),"");
    }

    @Test
    public void testGetIngredients() {
        List<String> testIngredients = new ArrayList<>();
        assertEquals(testRecipe.getIngredients(),testIngredients);
    }


    @Test
    public void testAddName() {
        assertEquals(testRecipe.getName(),"");
        testRecipe.addName("RicePudding");
        assertEquals(testRecipe.getName(),"RicePudding");
    }

    @Test
    public void testAssignStyle() {
        assertEquals(testRecipe.getStyle(),"");
        try {
            testRecipe.assignStyle("Western");
        } catch (NotTheRightStyleException e) {
            fail("No exception expected, NotTheRightStyleException thrown");
        }
        assertEquals(testRecipe.getStyle(),"Western");

    }

    @Test
    public void testAssignStyleFail() {
        assertEquals("",testRecipe.getStyle());
        try {
            testRecipe.assignStyle("western");
        } catch (NotTheRightStyleException e) {

        }
        assertEquals("",testRecipe.getStyle());
    }

    @Test
    public void testAddIngredients() {
        List<String> testIngredients = new ArrayList<>();
        assertEquals(testRecipe.getIngredients(),testIngredients);
        testRecipe.addIngredient("Rosemary");
        testIngredients.add("Rosemary");
        assertEquals(testRecipe.getIngredients(),testIngredients);
    }


    @Test
    public void testRate() {
        assertEquals(testRecipe.getRating(),0);
        try {
            testRecipe.rate(5);
        } catch (OutOfRangeException e) {
            fail("No exception expected, OutOfRangeException thrown");
        }
        assertEquals(testRecipe.getRating(),5);
    }

    @Test
    public void testRateFail() {
        assertEquals(0,testRecipe.getRating());
        try {
            testRecipe.rate(6);
        } catch (OutOfRangeException e) {


        }
        assertEquals(0,testRecipe.getRating());

    }

    @Test
    public void testRateFail2() {
        assertEquals(0,testRecipe.getRating());
        try {
            testRecipe.rate(0);
        } catch (OutOfRangeException e) {


        }
        assertEquals(0,testRecipe.getRating());

    }
}
