//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.BaseManager;
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.StructuralEngineer;
import Poised.Utils.Database.Tables.PersonalDetails;
import Poised.Utils.Database.Tables.Properties.RoleProperties;
import Poised.Utils.Dialogue.Components.FormField;
import Poised.Utils.Dialogue.DialogueManager;
import Poised.Utils.Dialogue.SelectItemDialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Structural Engineers Database Table and the User.
 */
public class StructuralEngineersManager extends BaseManager<StructuralEngineer, RoleProperties> {

    /**
     * Constructor for StructuralEngineersManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public StructuralEngineersManager(DatabaseManager databaseManager, DialogueManager dialogueManager) {

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
    protected void updateItemDetails(StructuralEngineer selectedItem, StructuralEngineer newData) {

        //Update the Selected Item's Details
        selectedItem.personalDetails = newData.personalDetails;
    }

    /**
     * Gets the Structural Engineer's ID Property.
     *
     * @return The Structural Engineer's ID Property.
     */
    protected RoleProperties itemIDProperty() {

        //Return the ID Property
        return RoleProperties.id;
    }

    /**
     * Gets the Structural Engineer from the User.
     *
     * @return The Structural Engineer Selected by the User.
     */
    protected StructuralEngineer getItemFromUser() {

        //Get the List of Items
        StructuralEngineer[] itemList =
                databaseManager.structuralEngineersTable.getAll().toArray(StructuralEngineer[]::new);

        //Setup the Select Item Dialogue
        SelectItemDialogue<StructuralEngineer> selectItemDialogue =
                new SelectItemDialogue<>(dialogueManager, itemList);

        //Get the Selected Item from the User
        StructuralEngineer selectedItem =
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
    protected StructuralEngineer createItemFromUserInput(HashMap<RoleProperties, Object> userInputData) {

        //Get the Structural_Engineer's Person Details from the User Input Data
        PersonalDetails personalDetails = ((PersonalDetails) userInputData.get(RoleProperties.Personal_Details));

        //Setup the New Structural_Engineer
        StructuralEngineer newStructural_Engineer = new StructuralEngineer(null, personalDetails);

        //Return the New Structural_Engineer
        return newStructural_Engineer;
    }

    /**
     * Gets the Structural Engineer's Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Structural Engineer.
     */
    protected HashMap<RoleProperties, FormField> getFilledFormFields(StructuralEngineer item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Structural Engineer's Form Fields.
     *
     * @return The List of Form Fields for the Structural Engineer.
     */
    protected HashMap<RoleProperties, FormField> getFormFields() {

        //Return the List of Form Fields for the Item
        return StructuralEngineer.getFormFields(databaseManager);
    }
}
