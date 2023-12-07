//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.BaseManager;
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.Architect;
import Poised.Utils.Database.Tables.PersonalDetails;
import Poised.Utils.Database.Tables.Properties.RoleProperties;
import Poised.Utils.Dialogue.Components.FormField;
import Poised.Utils.Dialogue.DialogueManager;
import Poised.Utils.Dialogue.SelectItemDialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Architects Database Table and the User.
 */
public class ArchitectsManager extends BaseManager<Architect, RoleProperties> {

    /**
     * Constructor for ArchitectsManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public ArchitectsManager(DatabaseManager databaseManager, DialogueManager dialogueManager) {

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
    protected RoleProperties itemIDProperty() {

        //Return the ID Property
        return RoleProperties.id;
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
        SelectItemDialogue<Architect> selectItemDialogue = new SelectItemDialogue<>(dialogueManager, itemList);

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
    protected Architect createItemFromUserInput(HashMap<RoleProperties, Object> userInputData) {

        //Get the Architect's Person Details from the User Input Data
        PersonalDetails personalDetails = ((PersonalDetails) userInputData.get(RoleProperties.Personal_Details));

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
    protected HashMap<RoleProperties, FormField> getFilledFormFields(Architect item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Architect's Form Fields.
     *
     * @return The List of Form Fields for the Architect.
     */
    protected HashMap<RoleProperties, FormField> getFormFields() {

        //Return the List of Form Fields for the Item
        return Architect.getFormFields(databaseManager);
    }
}
