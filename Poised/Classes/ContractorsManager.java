//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.BaseManager;
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.Contractor;
import Poised.Utils.Database.Tables.PersonalDetails;
import Poised.Utils.Database.Tables.Properties.RoleProperties;
import Poised.Utils.Dialogue.Components.FormField;
import Poised.Utils.Dialogue.DialogueManager;
import Poised.Utils.Dialogue.SelectItemDialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Contractors Database Table and the User.
 */
public class ContractorsManager extends BaseManager<Contractor, RoleProperties> {

    /**
     * Constructor for ContractorsManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public ContractorsManager(DatabaseManager databaseManager, DialogueManager dialogueManager) {

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
    protected RoleProperties itemIDProperty() {

        //Return the ID Property
        return RoleProperties.id;
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
        SelectItemDialogue<Contractor> selectItemDialogue = new SelectItemDialogue<>(dialogueManager, itemList);

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
    protected Contractor createItemFromUserInput(HashMap<RoleProperties, Object> userInputData) {

        //Get the Contractor's Person Details from the User Input Data
        PersonalDetails personalDetails = ((PersonalDetails) userInputData.get(RoleProperties.Personal_Details));

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
    protected HashMap<RoleProperties, FormField> getFilledFormFields(Contractor item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Contractor's Form Fields.
     *
     * @return The List of Form Fields for the Contractor.
     */
    protected HashMap<RoleProperties, FormField> getFormFields() {

        //Return the List of Form Fields for the Item
        return Contractor.getFormFields(databaseManager);
    }
}
