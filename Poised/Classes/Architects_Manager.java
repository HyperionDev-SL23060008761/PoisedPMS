//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.Base_Manager;
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Architect;
import Poised.Utils.Database.Tables.Personal_Details;
import Poised.Utils.Database.Tables.Properties.Role_Properties;
import Poised.Utils.Dialogue.Components.Form_Field;
import Poised.Utils.Dialogue.Dialogue_Manager;
import Poised.Utils.Dialogue.Select_Item_Dialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Architects Database Table and the User.
 */
public class Architects_Manager extends Base_Manager<Architect, Role_Properties> {

    /**
     * Constructor for Architects_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public Architects_Manager(Database_Manager databaseManager, Dialogue_Manager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.architectsTable, "Architect");
    }

    /**
     * Updates the Details of the Selected Architect.
     *
     * @param selectedItem The Selected Architect.
     * @param newData      The new Data to Update the Selected Architect with.
     */
    protected void updateItemDetails(Architect selectedItem, Architect newData) {

        //Update the Selected Item's Details
        selectedItem.personalDetails = newData.personalDetails;
    }

    /**
     * Gets the Architect's ID Property.
     *
     * @return The Architect's ID Property.
     */
    protected Role_Properties itemIDProperty() {

        //Return the ID Property
        return Role_Properties.id;
    }

    /**
     * Gets the Architect from the User.
     *
     * @return The Architect Selected by the User.
     */
    protected Architect getItemFromUser() {

        //Get the List of Items
        Architect[] itemList = databaseManager.architectsTable.getAll().toArray(Architect[]::new);

        //Setup the Select Item Dialogue
        Select_Item_Dialogue<Architect> selectItemDialogue = new Select_Item_Dialogue<>(dialogueManager, itemList);

        //Get the Selected Item from the User
        Architect selectedItem =
                selectItemDialogue.showDialogue("Please choose an Architect from the List");

        //Return the Selected Item
        return selectedItem;
    }

     /**
     * Creates a New Architect from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Architect created from the User's Input Data.
     */
    protected Architect createItemFromUserInput(HashMap<Role_Properties, Object> userInputData) {

        //Get the Architect's Person Details from the User Input Data
        Personal_Details personalDetails = ((Personal_Details) userInputData.get(Role_Properties.Personal_Details));

        //Setup the New Architect
        Architect newArchitect = new Architect(null, personalDetails);

        //Return the New Architect
        return newArchitect;
    }

    /**
     * Gets the Architect's Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Architect.
     */
    protected HashMap<Role_Properties, Form_Field> getFilledFormFields(Architect item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Architect's Form Fields.
     *
     * @return The List of Form Fields for the Architect.
     */
    protected HashMap<Role_Properties, Form_Field> getFormFields() {

        //Return the List of Form Fields for the Item
        return Architect.getFormFields(databaseManager);
    }
}
