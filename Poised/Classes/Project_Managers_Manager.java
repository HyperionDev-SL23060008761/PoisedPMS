//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.Base_Manager;
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Project_Manager;
import Poised.Utils.Database.Tables.Personal_Details;
import Poised.Utils.Database.Tables.Properties.Role_Properties;
import Poised.Utils.Dialogue.Components.Form_Field;
import Poised.Utils.Dialogue.Dialogue_Manager;
import Poised.Utils.Dialogue.Select_Item_Dialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Project Managers Database Table and the User.
 */
public class Project_Managers_Manager extends Base_Manager<Project_Manager, Role_Properties> {

    /**
     * Constructor for Project_Managers_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public Project_Managers_Manager(Database_Manager databaseManager, Dialogue_Manager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.projectManagersTable, "Project Manager");
    }

    /**
     * Updates the Details of the Selected Project Manager.
     *
     * @param selectedItem The Selected Project Manager.
     * @param newData      The new Data to Update the Selected Project Manager with.
     */
    protected void updateItemDetails(Project_Manager selectedItem, Project_Manager newData) {

        //Update the Selected Item's Details
        selectedItem.personalDetails = newData.personalDetails;
    }

    /**
     * Gets the Project Manager's ID Property.
     *
     * @return The Project Manager's ID Property.
     */
    protected Role_Properties itemIDProperty() {

        //Return the ID Property
        return Role_Properties.id;
    }

    /**
     * Gets the Project Manager from the User.
     *
     * @return The Project Manager Selected by the User.
     */
    protected Project_Manager getItemFromUser() {

        //Get the List of Items
        Project_Manager[] itemList = databaseManager.projectManagersTable.getAll().toArray(Project_Manager[]::new);

        //Setup the Select Item Dialogue
        Select_Item_Dialogue<Project_Manager> selectItemDialogue =
                new Select_Item_Dialogue<>(dialogueManager, itemList);

        //Get the Selected Item from the User
        Project_Manager selectedItem =
                selectItemDialogue.showDialogue("Please choose a Project Manager from the List");

        //Return the Selected Item
        return selectedItem;
    }

     /**
     * Creates a New Project Manager from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Project Manager created from the User's Input Data.
     */
    protected Project_Manager createItemFromUserInput(HashMap<Role_Properties, Object> userInputData) {

        //Get the Project_Manager's Person Details from the User Input Data
        Personal_Details personalDetails = ((Personal_Details) userInputData.get(Role_Properties.Personal_Details));

        //Setup the New Project_Manager
        Project_Manager newProject_Manager = new Project_Manager(null, personalDetails);

        //Return the New Project_Manager
        return newProject_Manager;
    }

    /**
     * Gets the Project Manager's Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Project Manager.
     */
    protected HashMap<Role_Properties, Form_Field> getFilledFormFields(Project_Manager item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Project Manager's Form Fields.
     *
     * @return The List of Form Fields for the Project Manager.
     */
    protected HashMap<Role_Properties, Form_Field> getFormFields() {

        //Return the List of Form Fields for the Item
        return Project_Manager.getFormFields(databaseManager);
    }
}
