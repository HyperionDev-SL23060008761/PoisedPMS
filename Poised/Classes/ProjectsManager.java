//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.BaseManager;
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.*;
import Poised.Utils.Database.Tables.Properties.ProjectProperties;
import Poised.Utils.Dialogue.Components.FormField;
import Poised.Utils.Dialogue.DialogueManager;
import Poised.Utils.Dialogue.FormDialogue;
import Poised.Utils.Dialogue.SelectItemDialogue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Projects Database Table and the User.
 */
public class ProjectsManager extends BaseManager<Project, ProjectProperties> {

    //Setup the Public Properties
    public ActionListener findListener;
    public ActionListener viewOverdueListener;
    public ActionListener viewInProgressListener;
    public ActionListener viewCompletedListener;
    public ActionListener finalizeProjectListener;

    /**
     * Constructor for ProjectsManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public ProjectsManager(DatabaseManager databaseManager, DialogueManager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.projectsTable, "Project");

        //Update the Public Properties
        this.findListener = this::find;
        this.viewOverdueListener = this::viewOverdue;
        this.viewInProgressListener = this::viewInProgress;
        this.viewCompletedListener = this::viewCompleted;
        this.finalizeProjectListener = this::finalizeProject;
    }

    /**
     * Shows a List of all the Overdue Projects.
     *
     * @param event The Event triggering the method.
     */
    public void viewOverdue(ActionEvent event) {

        //Show the List of All the Overdue Projects
        showAll("Overdue Projects", true, false, false);
    }

    /**
     * Shows a List of all the In-Progress Projects.
     *
     * @param event The Event triggering the method.
     */
    public void viewInProgress(ActionEvent event) {

        //Show the List of All the In Progress Projects
        showAll("In-Progress Projects", false, true, false);
    }

    /**
     * Shows a List of all the Completed Projects.
     *
     * @param event The Event triggering the method.
     */
    public void viewCompleted(ActionEvent event) {

        //Show the List of All the Completed Projects
        showAll("Completed Projects", false, false, true);
    }

    /**
     * Finalizes a Project By Updating its Completion Date.
     *
     * @param event The Event triggering the method.
     */
    public void finalizeProject(ActionEvent event) {

        //Get the List of In Progress Projects
        Project[] projectList = databaseManager.projectsTable
                .getAll(false, true, false)
                .toArray(Project[]::new);

        //Setup the Select Project Dialogue
        SelectItemDialogue<Project> selectProjectDialogue = new SelectItemDialogue<>(dialogueManager, projectList);

        //Get the Selected Project from the User
        Project selectedProject =
                selectProjectDialogue.showDialogue("Please choose a Project from the List");

        //Check if the User Canceled
        if (selectedProject == null) return;

        //Setup the Current Date
        Date currentDate = Date.valueOf(LocalDate.now());

        //Update the Date the Selected Projects Completion Date
        selectedProject.completionDate = currentDate;

        //Update the Selected Project
        boolean finalizeResult = databaseManager.projectsTable.update(selectedProject);

        //Check if the Finalization Failed
        if (!finalizeResult) dialogueManager.messageDialogue.showDialogue(
                    String.format("Project '%s' Could not Be Finalized", selectedProject.projectNumber)
            );

        //Check if the Finalization Was Successful
        if (finalizeResult) dialogueManager.messageDialogue.showDialogue(
                String.format("Project '%s' Successfully Finalized", selectedProject.projectNumber)
        );
    }

    /**
     * Updates the Details of the Selected Project.
     *
     * @param selectedItem The Selected Project.
     * @param newData      The new Data to Update the Selected Project with.
     */
    protected void updateItemDetails(Project selectedItem, Project newData) {

        //Update the Selected Item's Details
        selectedItem.projectNumber = newData.projectNumber;
        selectedItem.name = newData.name;
        selectedItem.buildingType = newData.buildingType;
        selectedItem.erfNumber = newData.erfNumber;
        selectedItem.address = newData.address;
        selectedItem.totalPrice = newData.totalPrice;
        selectedItem.totalPaid = newData.totalPaid;
        selectedItem.projectManager = newData.projectManager;
        selectedItem.structuralEngineer = newData.structuralEngineer;
        selectedItem.architect = newData.architect;
        selectedItem.contractor = newData.contractor;
        selectedItem.customer = newData.customer;
        selectedItem.deadline = newData.deadline;
        selectedItem.startDate = newData.startDate;
        selectedItem.completionDate = newData.completionDate;
    }

    /**
     * Shows a List of All the Projects with the Specified Parameters.
     *
     * @param dialogueMessage The message to be displayed in the dialogue.
     * @param overdue         Flag indicating if Only the Overdue projects Should be Shown.
     * @param inProgress      Flag indicating if Only the In-Progress projects Should be Shown.
     * @param completed       Flag indicating if Only the Completed projects Should be Shown.
     */
    private void showAll(String dialogueMessage, boolean overdue, boolean inProgress, boolean completed) {

        //Setup the
        //Get the List of All Projects
        ArrayList<Project> projectList = databaseManager.projectsTable.getAll(overdue, inProgress, completed);

        //Setup the List of Project Strings
        String[] itemStrings = projectList.stream().map(Object::toString).toArray(String[]::new);

        //Show the List of All Projects
        dialogueManager.showAllItemsDialogue.showDialogue(dialogueMessage, itemStrings);
    }

    /**
     * Finds a Project using the Project Number.
     *
     * @param event The ActionEvent triggering the method.
     */
    private void find(ActionEvent event) {

        //Setup the Input Messages
        ArrayList<String> inputMessages = new ArrayList<>();

        //Add the Input Messages
        inputMessages.add("Project Number");

        //Get the User's Input
        ArrayList<String> userInput = dialogueManager.userInputDialogue.showDialogue(
                "Please enter the Project's Number to find",
                inputMessages
        );

        //Check if the User Canceled and End the Find
        if(userInput == null) return;

        //Get the Project Number from the User's Input
        String projectNumber = userInput.get(0);

        //Check if the Project Number is Invalid
        if(projectNumber.isEmpty()) {

            //Let the User know they provided an Invalid Project Number
            dialogueManager.messageDialogue.showDialogue("Invalid Project Number Provided");

            //End the Find
            return;
        }

        //Attempt to Find the Project in the Database
        Project selectedproject = databaseManager.projectsTable.findByProjectNumber(projectNumber);

        //Check if the Selected Project is Invalid
        if(selectedproject == null) {

            //Let the User know the Project could not Be found
            dialogueManager.messageDialogue.showDialogue(String.format("Project '%s' not Found", projectNumber));

            //End the Find
            return;
        }

        //Get the Project's Filled Form Fields
        HashMap<ProjectProperties, FormField> formFields = selectedproject.getFilledFormFields(databaseManager);

        //Setup the Form Dialogue
        FormDialogue<ProjectProperties> formDialogue = new FormDialogue<>(dialogueManager);

        //Get the Updated Data from the User for this Project
        HashMap<ProjectProperties, Object> projectData =
                formDialogue.showDialogue("Update Project", formFields);

        //Check if the User Canceled and End the Update
        if(projectData == null) return;

        //Create the Project from the User's Input Data (Project Data)
        Project newProject = createItemFromUserInput(projectData);

        //Check if the Project Data is Invalid
        if(newProject == null) return;

        //Update the Selected Project's Details
        updateItemDetails(selectedproject, newProject);

        //Update the Project
        databaseManager.projectsTable.update(selectedproject);
    }

    /**
     * Gets the Project's ID Property.
     *
     * @return The Project's ID Property.
     */
    protected ProjectProperties itemIDProperty() {

        //Return the ID Property
        return null;
    }

    /**
     * Gets the Project from the User.
     *
     * @return The Project Selected by the User.
     */
    protected Project getItemFromUser() {

        //Get the List of Items
        Project[] itemList = databaseManager.projectsTable.getAll().toArray(Project[]::new);

        //Setup the Select Item Dialogue
        SelectItemDialogue<Project> selectItemDialogue = new SelectItemDialogue<>(dialogueManager, itemList);

        //Get the Selected Item from the User
        Project selectedItem =
                selectItemDialogue.showDialogue("Please choose a Project from the List");

        //Return the Selected Item
        return selectedItem;
    }

     /**
     * Creates a New Project from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Project created from the User's Input Data.
     */
    protected Project createItemFromUserInput(HashMap<ProjectProperties, Object> userInputData) {

        //Get the Project's Person Details from the User Input Data
        String projectNumber = (String) userInputData.get(ProjectProperties.Project_Number);
        String name = (String) userInputData.get(ProjectProperties.Name);
        BuildingType buildingType = (BuildingType) userInputData.get(ProjectProperties.Building_Type);
        String erfNumberString = (String) userInputData.get(ProjectProperties.ERF_Number);
        Address address = (Address) userInputData.get(ProjectProperties.Address);
        String totalPriceString = (String) userInputData.get(ProjectProperties.Total_Price);
        String totalPaidString = (String) userInputData.get(ProjectProperties.Total_Paid);
        ProjectManager projectManager = (ProjectManager) userInputData.get(ProjectProperties.Project_Manager);
        StructuralEngineer structuralEngineer =
                (StructuralEngineer) userInputData.get(ProjectProperties.Structural_Engineer);
        Architect architect = (Architect) userInputData.get(ProjectProperties.Architect);
        Contractor contractor = (Contractor) userInputData.get(ProjectProperties.Contractor);
        Customer customer = (Customer) userInputData.get(ProjectProperties.Customer);
        Date deadline = (Date) userInputData.get(ProjectProperties.Deadline);
        Date startDate = (Date) userInputData.get(ProjectProperties.Start_Date);
        Date completionDate = (Date) userInputData.get(ProjectProperties.Completion_Date);

        //Alter the ERF Number String to Remove all Non-Digit Characters
        erfNumberString = erfNumberString
                .replaceAll("\\D", "");

        //Alter the Total Price String to only keep the Digits and First Decimal Point
        totalPriceString = totalPriceString
                .replaceAll("[^0-9.]", "");

        //Alter the Total Paid String to only keep the Digits and First Decimal Point
        totalPaidString = totalPaidString
                .replaceAll("[^0-9.]", "");

        //Get the Total Price String Array (This will be used to only keep the Second Decimal Point)
        String[] totalPriceStringArray = totalPriceString.split("\\.");

        //Get the Total Paid String Array (This will be used to only keep the Second Decimal Point)
        String[] totalPaidStringArray = totalPaidString.split("\\.");

        //Check if an Invalid ERF Number was Provided
        if(erfNumberString.isEmpty()) {

            //Let the User know they provided an Invalid ERF Number
            dialogueManager.messageDialogue.showDialogue("Invalid ERF Number (Only Numbers allowed)");

            //End the Creation
            return null;
        }

        //Check if Total Price is Invalid
        if(totalPriceString.isEmpty() || totalPriceStringArray.length < 1) {

            //Let the User know they provided an Invalid Total Price
            dialogueManager.messageDialogue.showDialogue("Invalid Total Price (Only Numbers allowed)");

            //End the Creation
            return null;
        }

        //Check if Total Paid is Invalid
        if(totalPaidString.isEmpty() || totalPaidStringArray.length < 1) {

            //Let the User know they provided an Invalid Total Paid Amount
            dialogueManager.messageDialogue.showDialogue("Invalid Total Paid (Only Numbers allowed)");

            //End the Creation
            return null;
        }

        //Update the Total Price String
        totalPriceString =
                totalPaidStringArray.length > 1 ?
                totalPriceStringArray[0] + "." + totalPriceStringArray[1] : totalPriceStringArray[0];

        //Update the Total Paid String
        totalPaidString =
                totalPaidStringArray.length > 1 ?
                        totalPriceStringArray[0] + "." + totalPriceStringArray[1] : totalPriceStringArray[0];

        //Setup the ERF Number
        int erfNumber = Integer.parseInt(erfNumberString);

        //Setup the Total Price
        double totalPrice = Double.parseDouble(totalPriceString);

        //Setup the Total Paid
        double totalPaid = Double.parseDouble(totalPaidString);

        //Setup the New Project
        Project newProject = new Project(
                projectNumber,
                name, buildingType,
                erfNumber,
                address,
                totalPrice,
                totalPaid,
                projectManager,
                structuralEngineer,
                architect,
                contractor,
                customer,
                deadline,
                startDate,
                completionDate);

        //Return the New Project
        return newProject;
    }

    /**
     * Gets the Project's Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Project.
     */
    protected HashMap<ProjectProperties, FormField> getFilledFormFields(Project item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Project's Form Fields.
     *
     * @return The List of Form Fields for the Project.
     */
    protected HashMap<ProjectProperties, FormField> getFormFields() {

        //Return the List of Form Fields for the Item
        return Project.getFormFields(databaseManager);
    }
}
