//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.BaseManager;
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.ProjectManager;
import Poised.Utils.Database.Tables.PersonalDetails;
import Poised.Utils.Database.Tables.Properties.RoleProperties;
import Poised.Utils.Dialogue.Components.FormField;
import Poised.Utils.Dialogue.DialogueManager;
import Poised.Utils.Dialogue.SelectItemDialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Project Managers Database Table and the User.
 */
public class ProjectManagersManager extends BaseManager<ProjectManager, RoleProperties> {

    /**
     * Constructor for ProjectManagersManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public ProjectManagersManager(DatabaseManager databaseManager, DialogueManager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.projectManagersTable, "Project Manager");
    }

    /**
     * Updates the Details of the Selected Project Manager.
     *
     * @param selectedItem The Selected Project Manager.
     * @param newData      The new Data to Update the Selected Project Manager with.
     */
    protected void updateItemDetails(ProjectManager selectedItem, ProjectManager newData) {

        //Update the Selected Item's Details
        selectedItem.personalDetails = newData.personalDetails;
    }

    /**
     * Gets the Project Manager's ID Property.
     *
     * @return The Project Manager's ID Property.
     */
    protected RoleProperties itemIDProperty() {

        //Return the ID Property
        return RoleProperties.id;
    }

    /**
     * Gets the Project Manager from the User.
     *
     * @return The Project Manager Selected by the User.
     */
    protected ProjectManager getItemFromUser() {

        //Get the List of Items
        ProjectManager[] itemList = databaseManager.projectManagersTable.getAll().toArray(ProjectManager[]::new);

        //Setup the Select Item Dialogue
        SelectItemDialogue<ProjectManager> selectItemDialogue =
                new SelectItemDialogue<>(dialogueManager, itemList);

        //Get the Selected Item from the User
        ProjectManager selectedItem =
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
    protected ProjectManager createItemFromUserInput(HashMap<RoleProperties, Object> userInputData) {

        //Get the Project_Manager's Person Details from the User Input Data
        PersonalDetails personalDetails = ((PersonalDetails) userInputData.get(RoleProperties.Personal_Details));

        //Setup the New Project_Manager
        ProjectManager newProject_Manager = new ProjectManager(null, personalDetails);

        //Return the New Project_Manager
        return newProject_Manager;
    }

    /**
     * Gets the Project Manager's Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Project Manager.
     */
    protected HashMap<RoleProperties, FormField> getFilledFormFields(ProjectManager item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Project Manager's Form Fields.
     *
     * @return The List of Form Fields for the Project Manager.
     */
    protected HashMap<RoleProperties, FormField> getFormFields() {

        //Return the List of Form Fields for the Item
        return ProjectManager.getFormFields(databaseManager);
    }
}
