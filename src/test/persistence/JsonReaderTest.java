package persistence;

import model.Cookbook;
import model.NotTheRightStyleException;
import model.OutOfRangeException;
import model.Recipe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    //trying to read a file that does not exist
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            Cookbook cb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (OutOfRangeException e) {
            e.printStackTrace();
        } catch (NotTheRightStyleException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReaderEmptyCookbook() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCookbook.json");
        try {
            Cookbook cb = reader.read();
            List<String> testName = new ArrayList<>();
            assertEquals(testName, cb.viewNameRecipe());

        } catch (IOException | OutOfRangeException | NotTheRightStyleException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCookbook() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCookbook.json");
        try {
            Cookbook cb = reader.read();
            List<String> testName = new ArrayList<>();
            testName.add("rice");
            testName.add("tea");
            assertEquals(2,cb.viewNameRecipe().size());
        } catch (IOException | OutOfRangeException | NotTheRightStyleException e) {
            fail("Couldn't read from file");
        }
    }
}
