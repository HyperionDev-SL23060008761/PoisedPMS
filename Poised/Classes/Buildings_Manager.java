//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.Base_Manager;
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Building;
import Poised.Utils.Database.Tables.Properties.Building_Properties;
import Poised.Utils.Dialogue.Components.Form_Field;
import Poised.Utils.Dialogue.Dialogue_Manager;
import Poised.Utils.Dialogue.Select_Item_Dialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Buildings Database Table and the User.
 */
public class Buildings_Manager extends Base_Manager<Building, Building_Properties> {

    /**
     * Constructor for Buildings_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public Buildings_Manager(Database_Manager databaseManager, Dialogue_Manager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.buildingsTable, "Building");
    }

    /**
     * Updates the Details of the Selected Building.
     *
     * @param selectedItem The Selected Building.
     * @param newData      The new Data to Update the Selected Building with.
     */
    protected void updateItemDetails(Building selectedItem, Building newData) {

        //Update the Selected Item's Details
        selectedItem.buildingType = newData.buildingType;
    }

    /**
     * Gets the Building's ID Property.
     *
     * @return The Building's ID Property.
     */
    protected Building_Properties itemIDProperty() {

        //Return the ID Property
        return Building_Properties.id;
    }

    /**
     * Gets the Building from the User.
     *
     * @return The Building Selected by the User.
     */
    protected Building getItemFromUser() {

        //Get the List of Items
        Building[] itemList = databaseManager.buildingsTable.getAll().toArray(Building[]::new);

        //Setup the Select Item Dialogue
        Select_Item_Dialogue<Building> selectItemDialogue = new Select_Item_Dialogue<>(dialogueManager, itemList);



        //Get the Selected Item from the User
        Building selectedItem =
                selectItemDialogue.showDialogue("Please choose a Building from the List");

        //Return the Selected Item
        return selectedItem;
    }

     /**
     * Creates a New Building from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Building created from the User's Input Data.
     */
    protected Building createItemFromUserInput(HashMap<Building_Properties, Object> userInputData) {

        //Get the Building Type from the User Input Data
        String buildingType = ((String) userInputData.get(Building_Properties.Building_Type));

        //Setup the New Building
        Building newBuilding = new Building(null, buildingType);

        //Return the New Building
        return newBuilding;
    }

    /**
     * Gets the Building's Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Building.
     */
    protected HashMap<Building_Properties, Form_Field> getFilledFormFields(Building item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Building's Form Fields.
     *
     * @return The List of Form Fields for the Building.
     */
    protected HashMap<Building_Properties, Form_Field> getFormFields() {

        //Return the List of Form Fields for the Item
        return Building.getFormFields(databaseManager);
    }
}
