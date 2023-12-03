//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.Base_Manager;
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Structural_Engineer;
import Poised.Utils.Database.Tables.Personal_Details;
import Poised.Utils.Database.Tables.Properties.Role_Properties;
import Poised.Utils.Dialogue.Components.Form_Field;
import Poised.Utils.Dialogue.Dialogue_Manager;
import Poised.Utils.Dialogue.Select_Item_Dialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Structural Engineers Database Table and the User.
 */
public class Structural_Engineers_Manager extends Base_Manager<Structural_Engineer, Role_Properties> {

    /**
     * Constructor for Structural_Engineers_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public Structural_Engineers_Manager(Database_Manager databaseManager, Dialogue_Manager dialogueManager) {

        //Construct the Parent Class
        super(
                databaseManager,
                dialogueManager,
                databaseManager.structuralEngineersTable,
                "Structural Engineer"
        );
    }

    /**
     * Updates the Details of the Selected Structural Engineer.
     *
     * @param selectedItem The Selected Structural Engineer.
     * @param newData      The new Data to Update the Selected Structural Engineer with.
     */
    protected void updateItemDetails(Structural_Engineer selectedItem, Structural_Engineer newData) {

        //Update the Selected Item's Details
        selectedItem.personalDetails = newData.personalDetails;
    }

    /**
     * Gets the Structural Engineer's ID Property.
     *
     * @return The Structural Engineer's ID Property.
     */
    protected Role_Properties itemIDProperty() {

        //Return the ID Property
        return Role_Properties.id;
    }

    /**
     * Gets the Structural Engineer from the User.
     *
     * @return The Structural Engineer Selected by the User.
     */
    protected Structural_Engineer getItemFromUser() {

        //Get the List of Items
        Structural_Engineer[] itemList =
                databaseManager.structuralEngineersTable.getAll().toArray(Structural_Engineer[]::new);

        //Setup the Select Item Dialogue
        Select_Item_Dialogue<Structural_Engineer> selectItemDialogue =
                new Select_Item_Dialogue<>(dialogueManager, itemList);

        //Get the Selected Item from the User
        Structural_Engineer selectedItem =
                selectItemDialogue.showDialogue("Please choose a Structural Engineer from the List");

        //Return the Selected Item
        return selectedItem;
    }

     /**
     * Creates a New Structural Engineer from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Structural Engineer created from the User's Input Data.
     */
    protected Structural_Engineer createItemFromUserInput(HashMap<Role_Properties, Object> userInputData) {

        //Get the Structural_Engineer's Person Details from the User Input Data
        Personal_Details personalDetails = ((Personal_Details) userInputData.get(Role_Properties.Personal_Details));

        //Setup the New Structural_Engineer
        Structural_Engineer newStructural_Engineer = new Structural_Engineer(null, personalDetails);

        //Return the New Structural_Engineer
        return newStructural_Engineer;
    }

    /**
     * Gets the Structural Engineer's Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Structural Engineer.
     */
    protected HashMap<Role_Properties, Form_Field> getFilledFormFields(Structural_Engineer item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Structural Engineer's Form Fields.
     *
     * @return The List of Form Fields for the Structural Engineer.
     */
    protected HashMap<Role_Properties, Form_Field> getFormFields() {

        //Return the List of Form Fields for the Item
        return Structural_Engineer.getFormFields(databaseManager);
    }
}
