//Setup the Package
package Poised.Utils.Dialogue;

//Imports
import Poised.Classes.*;
import Poised.Utils.Dialogue.Components.Dialogue_Button;
import Poised.Utils.Dialogue.Menus.Manager_Menu;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A Class for Managing the Main Menu Dialogue.
 */
public class Main_Menu {

    //Setup the Private Properties
    private final Dialogue_Manager dialogueManager;

    /**
     * Constructor for Main_Menu.
     *
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public Main_Menu(Dialogue_Manager dialogueManager) {

        //Update the Private Properties
        this.dialogueManager = dialogueManager;
    }

    /**
     * Shows the Main Menu Dialogue to the User and Listens for User Input.
     *
     * @param buildingsManager           manages the Interactions Between the Buildings Table and the User.
     * @param addressesManager           manages the Interactions Between the Addresses Table and the User.
     * @param personalDetailsManager     manages the Interactions Between the Personal Details Table and the User.
     * @param architectsManager          manages the Interactions Between the Architects Table and the User.
     * @param contractorsManager         manages the Interactions Between the Contractors Table and the User.
     * @param customersManager           manages the Interactions Between the Customers Table and the User.
     * @param projectManagersManager     manages the Interactions Between the Project Managers Table and the User.
     * @param structuralEngineersManager manages the Interactions Between the Structural Engineers Table and the User.
     * @param projectsManager            manages the Interactions Between the Projects Table and the User.
     */
    public void open(
            Buildings_Manager buildingsManager,
            Addresses_Manager addressesManager,
            Personal_Details_Manager personalDetailsManager,
            Architects_Manager architectsManager,
            Contractors_Manager contractorsManager,
            Customers_Manager customersManager,
            Project_Managers_Manager projectManagersManager,
            Structural_Engineers_Manager structuralEngineersManager,
            Projects_Manager projectsManager
    ) {

        //Get the Menu Buttons
        ArrayList<Dialogue_Button> menuButtons = buildMenuButtons(
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

        //Setup the Dialogue's Main Label
        JLabel dialogueMainLabel = new JLabel("Please Select an Action you would like to perform:");

        //Setup the Dialogue's Spacer Label
        JLabel dialogueSpacerLabel = new JLabel(" ");

        //Setup the Dialogue Components
        ArrayList<JComponent> dialogueComponents = new ArrayList<>();

        //Add the Components to the Dialogue Components
        dialogueComponents.add(dialogueMainLabel);
        dialogueComponents.add(dialogueSpacerLabel);

        //Setup the Dialogue Options List
        ArrayList<Button> dialogueOptions = new ArrayList<>();

        for (Dialogue_Button dialogueButton : menuButtons) {

            //Create the New Button
            Button newButton = new Button(dialogueButton.buttonName());

            //Add the Action Listener to the New Button
            newButton.addActionListener(dialogueButton.listener());

            //Add the New Button to the Dialogue Options List
            dialogueOptions.add(newButton);
        }

        //Show the Dialogue to the User
        this.dialogueManager.showDialogue("Poised", dialogueComponents, dialogueOptions);
    }

    /**
     * Builds the Menu Buttons for the Different Managers to add to the Main Menu.
     *
     * @param buildingsManager           manages the Interactions Between the Buildings Table and the User.
     * @param addressesManager           manages the Interactions Between the Addresses Table and the User.
     * @param personalDetailsManager     manages the Interactions Between the Personal Details Table and the User.
     * @param architectsManager          manages the Interactions Between the Architects Table and the User.
     * @param contractorsManager         manages the Interactions Between the Contractors Table and the User.
     * @param customersManager           manages the Interactions Between the Customers Table and the User.
     * @param projectManagersManager     manages the Interactions Between the Project Managers Table and the User.
     * @param structuralEngineersManager manages the Interactions Between the Structural Engineers Table and the User.
     * @param projectsManager            manages the Interactions Between the Projects Table and the User.
     */
    private ArrayList<Dialogue_Button> buildMenuButtons(
            Buildings_Manager buildingsManager,
            Addresses_Manager addressesManager,
            Personal_Details_Manager personalDetailsManager,
            Architects_Manager architectsManager,
            Contractors_Manager contractorsManager,
            Customers_Manager customersManager,
            Project_Managers_Manager projectManagersManager,
            Structural_Engineers_Manager structuralEngineersManager,
            Projects_Manager projectsManager
    ) {

        //Setup the Menu Buttons List
        ArrayList<Dialogue_Button> menuButtons = new ArrayList<>();

        //Setup the Buildings Menu
        Manager_Menu buildingsMenu = new Manager_Menu(this.dialogueManager, buildingsManager, "Building");

        //Setup the Addresses Menu
        Manager_Menu addressesMenu = new Manager_Menu(this.dialogueManager, addressesManager, "Address");

        //Setup the Personal Details Menu
        Manager_Menu personalDetailsMenu =
                new Manager_Menu(this.dialogueManager, personalDetailsManager, "Personal Details");

        //Setup the Architects Menu
        Manager_Menu architectsMenu =
                new Manager_Menu(this.dialogueManager, architectsManager, "Architect");

        //Setup the Contractors Menu
        Manager_Menu contractorsMenu =
                new Manager_Menu(this.dialogueManager, contractorsManager, "Contractor");

        //Setup the Customers Menu
        Manager_Menu customersMenu =
                new Manager_Menu(this.dialogueManager, customersManager, "Customer");

        //Setup the Project Managers Menu
        Manager_Menu projectManagersMenu =
                new Manager_Menu(this.dialogueManager, projectManagersManager, "Project Manager");

        //Setup the Structural Engineers Menu
        Manager_Menu structuralEngineersMenu =
                new Manager_Menu(this.dialogueManager, structuralEngineersManager, "Structural Engineer");

        //Setup the Projects Menu
        Manager_Menu projectsMenu = new Manager_Menu(this.dialogueManager, projectsManager, "Project");

        //Add the Menu Buttons to the Menu Buttons List
        menuButtons.add(buildingsMenu.dialogueButton);
        menuButtons.add(addressesMenu.dialogueButton);
        menuButtons.add(personalDetailsMenu.dialogueButton);
        menuButtons.add(architectsMenu.dialogueButton);
        menuButtons.add(contractorsMenu.dialogueButton);
        menuButtons.add(customersMenu.dialogueButton);
        menuButtons.add(projectManagersMenu.dialogueButton);
        menuButtons.add(structuralEngineersMenu.dialogueButton);
        menuButtons.add(projectsMenu.dialogueButton);

        //Return the Menu Buttons List
        return menuButtons;
    }
}
