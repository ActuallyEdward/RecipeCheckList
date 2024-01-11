package ui;

import com.sun.media.jfxmedia.Media;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.util.ArrayList;
import java.util.Scanner;


public class CookBookControllerUI extends JPanel implements ActionListener {
    private JTextArea textArea;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private GridBagConstraints constraints;
    private GridBagLayout gridBag;
    private Cookbook myCollection;
    final  String newline = "\n";
    protected JTextField textField;
    private JComboBox recipesStored;
    private Recipe steak;
    private Recipe rice;
    private Recipe tea;
    private Recipe ricePudding;
    private String text;
    private static final String JSON_STORE = "./data/myRecipe.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private Recipe steak1 = new Recipe();
    private Recipe tea1 = new Recipe();
    private Recipe rice1 = new Recipe();
    private Recipe ricePudding1 = new Recipe();



    public CookBookControllerUI() throws OutOfRangeException, NotTheRightStyleException {
        super(new GridBagLayout());
        myCollection = new Cookbook();
        getStoredRecipes();
        getSteakRecipe();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        getTextArea();
        setup();
        createButtons();
        createTheBottomTextField();
    }

    //A helper to setup updated recipes
    private void setup() throws OutOfRangeException, NotTheRightStyleException {
        steak1.addIngredient("Steak");
        steak1.addIngredient("pepper");
        steak1.addIngredient("thyme");
        steak1.assignStyle("Western");
        tea1.rate(4);
        tea1.assignStyle("Eastern");
        tea1.addIngredient("water");
        tea1.addIngredient("tea");
        rice1.addIngredient("rice");
        rice1.addIngredient("water");
        rice1.assignStyle("Eastern");
        rice1.rate(3);
        ricePudding1.addIngredient("water");
        ricePudding1.addIngredient("rice");
        ricePudding1.addIngredient("sugar");
        ricePudding1.assignStyle("Western");
        ricePudding1.rate(4);
    }

    //EFFECTS: a helper method to create a textarea
    //MODIFIES: this
    private JTextArea getTextArea() {
        gridBag = (GridBagLayout)getLayout();
        constraints = new GridBagConstraints();
        JLabel l;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        l = new JLabel("Recipes Added:");
        gridBag.setConstraints(l, constraints);
        add(l);
        createComboBox();
        constraints.weighty = 1.0;
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        Dimension preferredSize = new Dimension(200,75);
        scrollPane.setPreferredSize(preferredSize);
        gridBag.setConstraints(scrollPane, constraints);
        add(scrollPane);
        return textArea;
    }

    //EFFECTS:Create a menu for save and load
    //MODIFIES: this
    private JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        //create a menu bar
        menuBar = new JMenuBar();
        //build the save/load menu
        menu = new JMenu("save & load");
        menuBar.add(menu);
        //menu items
        menuItem1 = new JMenuItem("save");
        menu.add(menuItem1);
        menuItem2 = new JMenuItem("load");
        menu.add(menuItem2);
        menuItem1.addActionListener(this);
        menuItem2.addActionListener(this);
        return menuBar;
    }

    //EFFECTS: a helper method to create buttons for addRecipe and deleteRecipe.
    private void createButtons() {
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(10, 10, 0, 10);
        button1 = new JButton("addRecipe");
        gridBag.setConstraints(button1, constraints);
        add(button1);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        button2 = new JButton("searchByIngredients");
        gridBag.setConstraints(button2, constraints);
        add(button2);
        button3 = new JButton("SearchByRating");
        gridBag.setConstraints(button2, constraints);
        add(button3);
        button4 = new JButton("AddIngredientToARecipe");
        gridBag.setConstraints(button2, constraints);
        add(button4);
        button4.addActionListener(this);
        button3.addActionListener(this);
        button1.addActionListener(this);
        button2.addActionListener(this);
    }

    //EFFECTS: create a text field to entre the value needed for addARecipe method.
    private void createTheBottomTextField() {
        textField = new JTextField(20);
        textField.addActionListener(this);
        textField.setOpaque(true);
        textField.setEditable(true);
        textField.setVisible(true);
        add(textField);

    }

    //EFFECTS:create a combo box that contains recipe to select from
    private void createComboBox() {
        String[] recipes = { "Steak", "RicePudding", "Rice", "Tea" };

        //Create the combo box, select the item at index 3.
        //Indices start at 0, so 3 specifies  tea.
        recipesStored = new JComboBox(recipes);
        recipesStored.setSelectedIndex(3);
        recipesStored.addActionListener(this);
        add(recipesStored);
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() throws OutOfRangeException, NotTheRightStyleException {
        //Create and set up the window.
        JFrame frame = new JFrame("CookBookControllerUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        CookBookControllerUI cookBookControllerUI = new CookBookControllerUI();

        frame.setJMenuBar(cookBookControllerUI.createMenuBar());

        cookBookControllerUI.setOpaque(true); //content panes must be opaque
        frame.setContentPane(cookBookControllerUI);


        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                try {
                    createAndShowGUI();
                } catch (OutOfRangeException | NotTheRightStyleException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            helperButton1();
        }
        if (e.getSource() == button2) {
            searchByIngredients();
        }
        if (e.getSource() == button3) {
            searchByRating();
        }
        if (e.getSource() == menuItem1) {
            saveCookbook();
        }
        if (e.getSource() == menuItem2) {
            loadCookbook();
        }
        if (e.getSource() == button4) {
            try {
                helperButton4();
            } catch (OutOfRangeException outOfRangeException) {
                outOfRangeException.printStackTrace();
            }
        }

    }

    //Helper for button4
    private void helperButton4() throws OutOfRangeException {
        try {
            addIngredientSelection();
        } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
            unsupportedAudioFileException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (LineUnavailableException lineUnavailableException) {
            lineUnavailableException.printStackTrace();
        }
    }

    //Helper for button1
    private void helperButton1() {
        try {
            addRecipeSelection();
        } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
            unsupportedAudioFileException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (LineUnavailableException lineUnavailableException) {
            lineUnavailableException.printStackTrace();
        }
    }

    //EFFECTS: a helper method for AddRecipe button
    private void addRecipeSelection() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String recipe = recipesStored.getSelectedItem().toString();
        if (recipe.equals(steak.getName())) {
            addSteakToMyCollection();
        }
        if (recipe.equals(tea.getName())) {
            addTeaToMyCollection();
        }
        if (recipe.equals(ricePudding.getName())) {
            addRicePuddingToMyCollection();
        }
        if (recipe.equals(rice.getName())) {
            addRiceToMyCollection();
        }
    }

    //EFFECTS: a helper method to set up a recipe for steak
    private void getSteakRecipe() throws OutOfRangeException, NotTheRightStyleException {
        steak = new Recipe();
        steak.addName("Steak");
        steak.addIngredient("Steak");
        steak.addIngredient("pepper");
        steak.addIngredient("thyme");
        steak.assignStyle("Western");
        steak.rate(4);

    }

    //Create Recipes in the combo box
    private void getStoredRecipes() throws OutOfRangeException, NotTheRightStyleException {
        ricePudding = new Recipe();
        ricePudding.addName("RicePudding");
        ricePudding.addIngredient("water");
        ricePudding.addIngredient("rice");
        ricePudding.addIngredient("sugar");
        ricePudding.assignStyle("Western");
        ricePudding.rate(4);
        tea = new Recipe();
        tea.addName("Tea");
        tea.rate(4);
        tea.assignStyle("Eastern");
        tea.addIngredient("water");
        tea.addIngredient("tea");
        rice = new Recipe();
        rice.addName("Rice");
        rice.addIngredient("rice");
        rice.addIngredient("water");
        rice.assignStyle("Eastern");
        rice.rate(3);
    }


    //EFFECTS: a helper method to add steak to the collection
    private void addSteakToMyCollection() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if  (!myCollection.containRecipe(steak)) {
            textArea.append("Name:" + steak.getName() + newline);
            textArea.append("Ingredients:" + steak.getIngredients().toString() + newline);
            textArea.append("Style:" + steak.getStyle() + newline);
            textArea.append("Rating:" + steak.getRating() + newline);
            textArea.append(newline);
            myCollection.addRecipe(steak);
        } else if (steak1.getName().equals("Steak Updated") && !myCollection.containRecipe(steak1)) {
            textArea.append("Name:" + steak1.getName() + newline);
            textArea.append("Ingredients:" + steak1.getIngredients().toString() + newline);
            textArea.append("Style:" + steak1.getStyle() + newline);
            textArea.append("Rating:" + steak1.getRating() + newline);
            textArea.append(newline);
            myCollection.addRecipe(steak1);
        } else {

            URL url = new URL("https://www.wavsource.com/snds_2020-10-01_3728627494378403/sfx/bicycle_bell.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

        }

    }


    //EFFECTS: a helper method to add tea to the collection
    private void addTeaToMyCollection() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (!myCollection.containRecipe(tea)) {
            myCollection.addRecipe(tea);
            textArea.append("Name:" + tea.getName() + newline);
            textArea.append("Ingredients:" + tea.getIngredients().toString() + newline);
            textArea.append("Style:" + tea.getStyle() + newline);
            textArea.append("Rating:" + tea.getRating() + newline);
            textArea.append(newline);
        } else if (tea1.getName().equals("Tea Updated") && !myCollection.containRecipe(tea1)) {
            textArea.append("Name:" + tea1.getName() + newline);
            textArea.append("Ingredients:" + tea1.getIngredients().toString() + newline);
            textArea.append("Style:" + tea1.getStyle() + newline);
            textArea.append("Rating:" + tea1.getRating() + newline);
            textArea.append(newline);
            myCollection.addRecipe(tea1);
        } else {
            URL url = new URL("https://www.wavsource.com/snds_2020-10-01_3728627494378403/sfx/bicycle_bell.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
    }

    //EFFECTS: a helper method to add RicePudding to the collection
    private void addRicePuddingToMyCollection() throws IOException, UnsupportedAudioFileException,
            LineUnavailableException {
        if (!myCollection.containRecipe(ricePudding)) {
            myCollection.addRecipe(ricePudding);
            textArea.append("Name:" + ricePudding.getName() + newline);
            textArea.append("Ingredients:" + ricePudding.getIngredients().toString() + newline);
            textArea.append("Style:" + ricePudding.getStyle() + newline);
            textArea.append("Rating:" + ricePudding.getRating() + newline);
            textArea.append(newline);
        } else if (ricePudding1.getName().equals("RicePudding Updated") && !myCollection.containRecipe(ricePudding1)) {
            textArea.append("Name:" + ricePudding1.getName() + newline);
            textArea.append("Ingredients:" + ricePudding1.getIngredients().toString() + newline);
            textArea.append("Style:" + ricePudding1.getStyle() + newline);
            textArea.append("Rating:" + ricePudding1.getRating() + newline);
            textArea.append(newline);
            myCollection.addRecipe(ricePudding1);
        } else {
            URL url = new URL("https://www.wavsource.com/snds_2020-10-01_3728627494378403/sfx/bicycle_bell.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }

    }


    //EFFECTS: a helper method to add rice to the collection
    private void addRiceToMyCollection() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (!myCollection.containRecipe(rice)) {
            myCollection.addRecipe(rice);
            textArea.append("Name:" + rice.getName() + newline);
            textArea.append("Ingredients:" + rice.getIngredients().toString() + newline);
            textArea.append("Style:" + rice.getStyle() + newline);
            textArea.append("Rating:" + rice.getRating() + newline);
            textArea.append(newline);
        } else if (rice1.getName().equals("Rice Updated") && !myCollection.containRecipe(rice1)) {
            textArea.append("Name:" + rice1.getName() + newline);
            textArea.append("Ingredients:" + rice1.getIngredients().toString() + newline);
            textArea.append("Style:" + rice1.getStyle() + newline);
            textArea.append("Rating:" + rice1.getRating() + newline);
            textArea.append(newline);
            myCollection.addRecipe(rice1);
        } else {
            URL url = new URL("https://www.wavsource.com/snds_2020-10-01_3728627494378403/sfx/bicycle_bell.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }

    }

    //EFFECTS: search Recipe by Ingredient
    private void searchByIngredients() {
        text = textField.getText();
        String ingredient = text;
        ArrayList<String> result = new ArrayList<String>();
        try {
            for (Recipe r: myCollection.searchIngredientsRecipe(ingredient)) {
                result.add(r.getName());
            }
        } catch (EmptyListException e) {
            System.out.println("There is no recipe in the collection!");
        }
        textArea.append(result.toString());
        textArea.append(newline);
    }

    //REQUIRES:input should be a string that can be parsed to integer, the input should be within 1-5
    //EFFECTS:search recipes with the input rating
    private void searchByRating()  {
        String rating = textField.getText();
        int num = Integer.parseInt(rating);
        ArrayList<String> result = new ArrayList<String>();
        try {
            for (Recipe r : myCollection.searchNumStarRecipe(num)) {
                result.add(r.getName());
            }
        } catch (EmptyListException e) {
            System.out.println("There is no recipe in the collection!");
        }
        textArea.append(result.toString());
        textArea.append(newline);
    }

    // EFFECTS: saves the Cookbook to file
    private void saveCookbook() {
        try {
            jsonWriter.open();
            jsonWriter.write(myCollection);
            jsonWriter.close();
            textArea.append("Saved " + "recipes" + " to " + JSON_STORE + newline);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Cookbook from file
    private void loadCookbook() {
        try {
            myCollection = jsonReader.read();
            textArea.append("Loaded " + "recipes" + " from " + JSON_STORE + newline);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (OutOfRangeException e) {
            e.printStackTrace();
        } catch (NotTheRightStyleException e) {
            e.printStackTrace();
        }
    }

    //EFFECTS:a helper method to add ingredient to the selected recipe
    private void addIngredientSelection() throws UnsupportedAudioFileException, IOException, LineUnavailableException,
            OutOfRangeException {
        String recipe = recipesStored.getSelectedItem().toString();
        if (recipe.equals(steak.getName())) {
            addNewIngredientForSteakToMyCollection();
        }
        if (recipe.equals(tea.getName())) {
            addNewIngredientForTeaToMyCollection();
        }
        if (recipe.equals(ricePudding.getName())) {
            addNewIngredientForRicePuddingToMyCollection();
        }
        if (recipe.equals(rice.getName())) {
            addNewIngredientForRiceToMyCollection();
        }

    }

    //EFFECTS:a helper method to add a new ingredient for the steak
    private void addNewIngredientForSteakToMyCollection() throws OutOfRangeException {
        String ingredient = textField.getText();
        steak1.rate(4);
        steak1.addIngredient(ingredient);
        steak1.addName("Steak Updated");

    }

    //EFFECTS:a helper method to add a new ingredient for tea
    private void addNewIngredientForTeaToMyCollection() {
        String ingredient = textField.getText();
        tea1.addIngredient(ingredient);
        tea1.addName("Tea Updated");
    }

    //EFFECTS:a helper method to add a new ingredient for ricePudding
    private void addNewIngredientForRicePuddingToMyCollection() {
        String ingredient = textField.getText();

        ricePudding1.addIngredient(ingredient);
        ricePudding1.addName("RicePudding Updated");
    }

    //EFFECTS:a helper method to add a new ingredient for rice
    private void addNewIngredientForRiceToMyCollection() {
        String ingredient = textField.getText();

        rice1.addIngredient(ingredient);
        rice1.addName("Rice Updated");
    }






}
