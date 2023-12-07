//Setup the Package
package Poised.Utils.Dialogue;

//Imports
import Poised.Classes.*;
import Poised.Utils.Dialogue.Components.DialogueButton;
import Poised.Utils.Dialogue.Menus.ManagerMenu;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A Class for Managing the Main Menu Dialogue.
 */
public class MainMenu {

    //Setup the Private Properties
    private final DialogueManager dialogueManager;

    /**
     * Constructor for MainMenu.
     *
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public MainMenu(DialogueManager dialogueManager) {

        //Update the Private Properties
        this.dialogueManager = dialogueManager;
    }

    /**
     * Shows the Main Menu Dialogue to the User and Listens for User Input.
     *
     * @param buildingsManager           manages the Interactions Between the Building Types Table and the User.
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
            BuildingsManager buildingsManager,
            AddressesManager addressesManager,
            PersonalDetailsManager personalDetailsManager,
            ArchitectsManager architectsManager,
            ContractorsManager contractorsManager,
            CustomersManager customersManager,
            ProjectManagersManager projectManagersManager,
            StructuralEngineersManager structuralEngineersManager,
            ProjectsManager projectsManager
    ) {

        //Get the Menu Buttons
        ArrayList<DialogueButton> menuButtons = buildMenuButtons(
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

        for (DialogueButton dialogueButton : menuButtons) {

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
     * @param buildingsManager           manages the Interactions Between the Building Types Table and the User.
     * @param addressesManager           manages the Interactions Between the Addresses Table and the User.
     * @param personalDetailsManager     manages the Interactions Between the Personal Details Table and the User.
     * @param architectsManager          manages the Interactions Between the Architects Table and the User.
     * @param contractorsManager         manages the Interactions Between the Contractors Table and the User.
     * @param customersManager           manages the Interactions Between the Customers Table and the User.
     * @param projectManagersManager     manages the Interactions Between the Project Managers Table and the User.
     * @param structuralEngineersManager manages the Interactions Between the Structural Engineers Table and the User.
     * @param projectsManager            manages the Interactions Between the Projects Table and the User.
     */
    private ArrayList<DialogueButton> buildMenuButtons(
            BuildingsManager buildingsManager,
            AddressesManager addressesManager,
            PersonalDetailsManager personalDetailsManager,
            ArchitectsManager architectsManager,
            ContractorsManager contractorsManager,
            CustomersManager customersManager,
            ProjectManagersManager projectManagersManager,
            StructuralEngineersManager structuralEngineersManager,
            ProjectsManager projectsManager
    ) {

        //Setup the Menu Buttons List
        ArrayList<DialogueButton> menuButtons = new ArrayList<>();

        //Setup the Building Types Menu
        ManagerMenu buildingsMenu =
                new ManagerMenu(this.dialogueManager, buildingsManager, "Building Type");

        //Setup the Addresses Menu
        ManagerMenu addressesMenu = new ManagerMenu(this.dialogueManager, addressesManager, "Address");

        //Setup the Personal Details Menu
        ManagerMenu personalDetailsMenu =
                new ManagerMenu(this.dialogueManager, personalDetailsManager, "Personal Details");

        //Setup the Architects Menu
        ManagerMenu architectsMenu =
                new ManagerMenu(this.dialogueManager, architectsManager, "Architect");

        //Setup the Contractors Menu
        ManagerMenu contractorsMenu =
                new ManagerMenu(this.dialogueManager, contractorsManager, "Contractor");

        //Setup the Customers Menu
        ManagerMenu customersMenu =
                new ManagerMenu(this.dialogueManager, customersManager, "Customer");

        //Setup the Project Managers Menu
        ManagerMenu projectManagersMenu =
                new ManagerMenu(this.dialogueManager, projectManagersManager, "Project Manager");

        //Setup the Structural Engineers Menu
        ManagerMenu structuralEngineersMenu =
                new ManagerMenu(this.dialogueManager, structuralEngineersManager, "Structural Engineer");

        //Setup the Projects Menu
        ManagerMenu projectsMenu = new ManagerMenu(this.dialogueManager, projectsManager, "Project");

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
