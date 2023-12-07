//Setup the Package
package Poised;

//Imports
import Poised.Classes.*;
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Dialogue.DialogueManager;

/**
 * The Main Class for the Project, Responsible for Initializing and Building the Required Components.
 */
class Poised {

    /**
     * Setup the Main Method.
     *
     * @param args Not In Use.
     */
    public static void main(String[] args) {

        //Setup the Dialogue Manager
        DialogueManager dialogueManager = new DialogueManager();

        //Setup the New Database Manager
        DatabaseManager databaseManager = new DatabaseManager(
                "localhost:3306",
                "Poised",
                "poised",
                "poised"
        );

        //Attempt to Connect to the Database
        try {

            //Connect to the Database
            databaseManager.connect();
        } catch (Exception exception) {

            //Let the User know that the Database is currently Offline
            dialogueManager.messageDialogue.showDialogue("Database is Currently Offline");

            //Log the Error Message to the Console
            System.out.printf("Failed to connect to the Database:%n%s", exception.getMessage());

            //End the Program
            System.exit(0);
        }

        //Setup the Building Types Manager
        BuildingsManager buildingsManager = new BuildingsManager(databaseManager, dialogueManager);

        //Setup the Addresses Manager
        AddressesManager addressesManager = new AddressesManager(databaseManager, dialogueManager);

        //Setup the Personal Details Manager
        PersonalDetailsManager personalDetailsManager =
                new PersonalDetailsManager(databaseManager, dialogueManager);

        //Setup the Architects Manager
        ArchitectsManager architectsManager = new ArchitectsManager(databaseManager, dialogueManager);

        //Setup the Contractors Manager
        ContractorsManager contractorsManager = new ContractorsManager(databaseManager, dialogueManager);

        //Setup the Customers Manager
        CustomersManager customersManager = new CustomersManager(databaseManager, dialogueManager);

        //Setup the Project Managers Manager
        ProjectManagersManager projectManagersManager =
                new ProjectManagersManager(databaseManager, dialogueManager);

        //Setup the Structural Engineers Manager
        StructuralEngineersManager structuralEngineersManager =
                new StructuralEngineersManager(databaseManager, dialogueManager);

        //Setup the Projects Manager
        ProjectsManager projectsManager =
                new ProjectsManager(databaseManager, dialogueManager);

        //Show the Main Menu
        dialogueManager.mainMenu.open(
                buildingsManager,
                addressesManager,
                personalDetailsManager,
                architectsManager,
                contractorsManager,
                customersManager,
                projectManagersManager,
                structuralEngineersManager,
                projectsManager
        );
    }

}