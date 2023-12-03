//Setup this Package
package Poised.Utils.Dialogue.Menus;

//Imports
import Poised.Classes.Projects_Manager;
import Poised.Utils.Base_Manager;
import Poised.Utils.Dialogue.Components.Dialogue_Button;
import Poised.Utils.Dialogue.Components.Section;
import Poised.Utils.Dialogue.Dialogue_Manager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A Class for Managing the Menu for a Specific Table's Manager
 */
public class Manager_Menu {

    //Setup the Public Properties
    public Dialogue_Button dialogueButton;

    //Setup the Private Properties
    private final Dialogue_Manager dialogueManager;
    private final Base_Manager<?, ?> requestedManager;
    private final String managerName;

    /**
     * Constructor for Manager_Menu.
     *
     * @param dialogueManager  The Dialogue Manager that will Handle all the dialogue Interactions.
     * @param requestedManager manages the Interactions Between the Table and the User
     * @param managerName      The Name of the Table that is Linked to the Requested Manager
     */
    public Manager_Menu(Dialogue_Manager dialogueManager, Base_Manager<?, ?> requestedManager, String managerName) {

        //Update the Public Properties
        this.dialogueButton = new Dialogue_Button(this::showMenu, String.format("Manage %s", managerName));

        //Update the Private Properties
        this.dialogueManager = dialogueManager;
        this.requestedManager = requestedManager;
        this.managerName = managerName;
    }

    /**
     * Shows the Manager's Menu for the Specified Table.
     */
    private void showMenu(ActionEvent event) {

        //Get the Listeners
        ActionListener createListener = this.requestedManager.createListener;
        ActionListener updateListener = this.requestedManager.updateListener;
        ActionListener deleteListener = this.requestedManager.deleteListener;
        ActionListener viewListener = this.requestedManager.viewListener;

        //Setup the Buttons
        Dialogue_Button createButton = new Dialogue_Button(createListener, String.format("Create %s", managerName));
        Dialogue_Button updateButton = new Dialogue_Button(updateListener, String.format("Update %s", managerName));
        Dialogue_Button deleteButton = new Dialogue_Button(deleteListener, String.format("Delete %s", managerName));
        Dialogue_Button viewButton = new Dialogue_Button(viewListener, String.format("View %s", managerName));

        //Setup the Buttons List
        ArrayList<Dialogue_Button> buttonsList = new ArrayList<>();

        //Add the Buttons to the Buttons List
        buttonsList.add(createButton);
        buttonsList.add(updateButton);
        buttonsList.add(deleteButton);
        buttonsList.add(viewButton);

        //Check if the Requested Manager is the Projects Manager
        if(requestedManager instanceof Projects_Manager projectsManager) {

            //Setup the Project Find Button
            Dialogue_Button findButton = new Dialogue_Button(
                    projectsManager.findListener,
                    "Find Projects"
            );

            //Setup the Project View Overdue Button
            Dialogue_Button viewOverdueButton = new Dialogue_Button(
                    projectsManager.viewOverdueListener,
                    "View Overdue Projects"
            );

            //Setup the Project View In Progress Button
            Dialogue_Button viewInProgressButton = new Dialogue_Button(
                    projectsManager.viewInProgressListener,
                    "View In-Progress Projects"
            );

            //Setup the Project View Completed Button
            Dialogue_Button viewCompletedButton = new Dialogue_Button(
                    projectsManager.viewCompletedListener,
                    "View Completed Projects"
            );

            //Setup the Finalize Project Button
            Dialogue_Button finalizeProjectButton = new Dialogue_Button(
                    projectsManager.finalizeProjectListener,
                    "Finalize a Project"
            );

            //Add the Project Specific Buttons to the Buttons List
            buttonsList.add(findButton);
            buttonsList.add(viewOverdueButton);
            buttonsList.add(viewInProgressButton);
            buttonsList.add(viewCompletedButton);
            buttonsList.add(finalizeProjectButton);
        }

        //Setup the New Section
        Section newSection = new Section(dialogueManager, String.format("%s Manager", managerName), buttonsList);

        //Show the New Section
        newSection.open();
    }
}
