package persistence;

import model.Cookbook;
import model.NotTheRightStyleException;
import model.OutOfRangeException;
import model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    Recipe tea;
    Recipe rice;

    @BeforeEach
    void constants() throws OutOfRangeException, NotTheRightStyleException {
        tea = new Recipe();
        tea.addName("tea");
        tea.addIngredient("tea");
        tea.addIngredient("water");
        tea.assignStyle("Eastern");
        tea.rate(4);
        rice = new Recipe();
        rice.rate(4);
        rice.assignStyle("Eastern");
        rice.addIngredient("rice");
        rice.addIngredient("water");
        rice.addName("rice");

    }

    @Test
    //test the case where there is no such file.
    void testWriterInvalidFile() {
        try {
            Cookbook cb = new Cookbook();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCookbook() {
        try {
            Cookbook cb = new Cookbook();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCookbook.json");
            writer.open();
            writer.write(cb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCookbook.json");
            cb = reader.read();
            List<String> testName = new ArrayList<>();
            assertEquals(testName,cb.viewNameRecipe());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (OutOfRangeException e) {
            e.printStackTrace();
        } catch (NotTheRightStyleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Cookbook cb = new Cookbook();
            cb.addRecipe(rice);
            cb.addRecipe(tea);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCookbook.json");
            writer.open();
            writer.write(cb);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralCookbook.json");
            cb = reader.read();
            List<String> testName = new ArrayList<>();
            testName.add("rice");
            testName.add("tea");
            assertEquals(testName,cb.viewNameRecipe().toString());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (OutOfRangeException e) {
            e.printStackTrace();
        } catch (NotTheRightStyleException e) {
            e.printStackTrace();
        }
    }
}
