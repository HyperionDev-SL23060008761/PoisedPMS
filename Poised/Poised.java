//Setup the Package
package Poised;

//Imports
import Poised.Classes.*;
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Dialogue.Dialogue_Manager;

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
        Dialogue_Manager dialogueManager = new Dialogue_Manager();

        //Setup the New Database Manager
        Database_Manager databaseManager = new Database_Manager(
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

        //Setup the Buildings Manager
        Buildings_Manager buildingsManager = new Buildings_Manager(databaseManager, dialogueManager);

        //Setup the Addresses Manager
        Addresses_Manager addressesManager = new Addresses_Manager(databaseManager, dialogueManager);

        //Setup the Personal Details Manager
        Personal_Details_Manager personalDetailsManager =
                new Personal_Details_Manager(databaseManager, dialogueManager);

        //Setup the Architects Manager
        Architects_Manager architectsManager = new Architects_Manager(databaseManager, dialogueManager);

        //Setup the Contractors Manager
        Contractors_Manager contractorsManager = new Contractors_Manager(databaseManager, dialogueManager);

        //Setup the Customers Manager
        Customers_Manager customersManager = new Customers_Manager(databaseManager, dialogueManager);

        //Setup the Project Managers Manager
        Project_Managers_Manager projectManagersManager =
                new Project_Managers_Manager(databaseManager, dialogueManager);

        //Setup the Structural Engineers Manager
        Structural_Engineers_Manager structuralEngineersManager =
                new Structural_Engineers_Manager(databaseManager, dialogueManager);

        //Setup the Projects Manager
        Projects_Manager projectsManager =
                new Projects_Manager(databaseManager, dialogueManager);

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