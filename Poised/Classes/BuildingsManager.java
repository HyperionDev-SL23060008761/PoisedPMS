//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.BaseManager;
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.BuildingType;
import Poised.Utils.Database.Tables.Properties.BuildingTypeProperties;
import Poised.Utils.Dialogue.Components.FormField;
import Poised.Utils.Dialogue.DialogueManager;
import Poised.Utils.Dialogue.SelectItemDialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Building Types Database Table and the User.
 */
public class BuildingsManager extends BaseManager<BuildingType, BuildingTypeProperties> {

    /**
     * Constructor for BuildingsManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public BuildingsManager(DatabaseManager databaseManager, DialogueManager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.buildingsTable, "Building Type");
    }

    /**
     * Updates the Details of the Selected Building Type.
     *
     * @param selectedItem The Selected Building Type.
     * @param newData      The new Data to Update the Selected Building Type with.
     */
    protected void updateItemDetails(BuildingType selectedItem, BuildingType newData) {

        //Update the Selected Item's Details
        selectedItem.buildingType = newData.buildingType;
    }

    /**
     * Gets the Building Type's ID Property.
     *
     * @return The Building Type's ID Property.
     */
    protected BuildingTypeProperties itemIDProperty() {

        //Return the ID Property
        return BuildingTypeProperties.id;
    }

    /**
     * Gets the Building Type from the User.
     *
     * @return The Building Type Selected by the User.
     */
    protected BuildingType getItemFromUser() {

        //Get the List of Items
        BuildingType[] itemList = databaseManager.buildingsTable.getAll().toArray(BuildingType[]::new);

        //Setup the Select Item Dialogue
        SelectItemDialogue<BuildingType> selectItemDialogue = new SelectItemDialogue<>(dialogueManager, itemList);



        //Get the Selected Item from the User
        BuildingType selectedItem =
                selectItemDialogue.showDialogue("Please choose a Building Type from the List");

        //Return the Selected Item
        return selectedItem;
    }

     /**
     * Creates a New Building Type from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Building Type created from the User's Input Data.
     */
    protected BuildingType createItemFromUserInput(HashMap<BuildingTypeProperties, Object> userInputData) {

        //Get the Building Type's Name from the User Input Data
        String buildingTypeName = ((String) userInputData.get(BuildingTypeProperties.Building_Type));

        //Setup the New Building Type
        BuildingType newBuildingType = new BuildingType(null, buildingTypeName);

        //Return the New Building Type
        return newBuildingType;
    }

    /**
     * Gets the Building Type's Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Building Type.
     */
    protected HashMap<BuildingTypeProperties, FormField> getFilledFormFields(BuildingType item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Building Type's Form Fields.
     *
     * @return The List of Form Fields for the Building Type.
     */
    protected HashMap<BuildingTypeProperties, FormField> getFormFields() {

        //Return the List of Form Fields for the Item
        return BuildingType.getFormFields(databaseManager);
    }
}
