//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.Base_Manager;
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Contractor;
import Poised.Utils.Database.Tables.Personal_Details;
import Poised.Utils.Database.Tables.Properties.Role_Properties;
import Poised.Utils.Dialogue.Components.Form_Field;
import Poised.Utils.Dialogue.Dialogue_Manager;
import Poised.Utils.Dialogue.Select_Item_Dialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Contractors Database Table and the User.
 */
public class Contractors_Manager extends Base_Manager<Contractor, Role_Properties> {

    /**
     * Constructor for Contractors_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public Contractors_Manager(Database_Manager databaseManager, Dialogue_Manager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.contractorsTable, "Contractor");
    }

    /**
     * Updates the Details of the Selected Contractor.
     *
     * @param selectedItem The Selected Contractor.
     * @param newData      The new Data to Update the Selected Contractor with.
     */
    protected void updateItemDetails(Contractor selectedItem, Contractor newData) {

        //Update the Selected Item's Details
        selectedItem.personalDetails = newData.personalDetails;
    }

    /**
     * Gets the Contractor's ID Property.
     *
     * @return The Contractor's ID Property.
     */
    protected Role_Properties itemIDProperty() {

        //Return the ID Property
        return Role_Properties.id;
    }

    /**
     * Gets the Contractor from the User.
     *
     * @return The Contractor Selected by the User.
     */
    protected Contractor getItemFromUser() {

        //Get the List of Items
        Contractor[] itemList = databaseManager.contractorsTable.getAll().toArray(Contractor[]::new);

        //Setup the Select Item Dialogue
        Select_Item_Dialogue<Contractor> selectItemDialogue = new Select_Item_Dialogue<>(dialogueManager, itemList);

        //Get the Selected Item from the User
        Contractor selectedItem =
                selectItemDialogue.showDialogue("Please choose a Contractor from the List");

        //Return the Selected Item
        return selectedItem;
    }

     /**
     * Creates a New Contractor from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Contractor created from the User's Input Data.
     */
    protected Contractor createItemFromUserInput(HashMap<Role_Properties, Object> userInputData) {

        //Get the Contractor's Person Details from the User Input Data
        Personal_Details personalDetails = ((Personal_Details) userInputData.get(Role_Properties.Personal_Details));

        //Setup the New Contractor
        Contractor newContractor = new Contractor(null, personalDetails);

        //Return the New Contractor
        return newContractor;
    }

    /**
     * Gets the Contractor's Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Contractor.
     */
    protected HashMap<Role_Properties, Form_Field> getFilledFormFields(Contractor item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Contractor's Form Fields.
     *
     * @return The List of Form Fields for the Contractor.
     */
    protected HashMap<Role_Properties, Form_Field> getFormFields() {

        //Return the List of Form Fields for the Item
        return Contractor.getFormFields(databaseManager);
    }
}
