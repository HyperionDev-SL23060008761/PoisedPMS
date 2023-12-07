//Setup this Package
package Poised.Utils.Dialogue.Menus;

//Imports
import Poised.Classes.ProjectsManager;
import Poised.Utils.BaseManager;
import Poised.Utils.Dialogue.Components.DialogueButton;
import Poised.Utils.Dialogue.Components.Section;
import Poised.Utils.Dialogue.DialogueManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A Class for Managing the Menu for a Specific Table's Manager
 */
public class ManagerMenu {

    //Setup the Public Properties
    public DialogueButton dialogueButton;

    //Setup the Private Properties
    private final DialogueManager dialogueManager;
    private final BaseManager<?, ?> requestedManager;
    private final String managerName;

    /**
     * Constructor for ManagerMenu.
     *
     * @param dialogueManager  The Dialogue Manager that will Handle all the dialogue Interactions.
     * @param requestedManager manages the Interactions Between the Table and the User
     * @param managerName      The Name of the Table that is Linked to the Requested Manager
     */
    public ManagerMenu(DialogueManager dialogueManager, BaseManager<?, ?> requestedManager, String managerName) {

        //Update the Public Properties
        this.dialogueButton = new DialogueButton(this::showMenu, String.format("Manage %s", managerName));

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
        DialogueButton createButton = new DialogueButton(createListener, String.format("Create %s", managerName));
        DialogueButton updateButton = new DialogueButton(updateListener, String.format("Update %s", managerName));
        DialogueButton deleteButton = new DialogueButton(deleteListener, String.format("Delete %s", managerName));
        DialogueButton viewButton = new DialogueButton(viewListener, String.format("View %s", managerName));

        //Setup the Buttons List
        ArrayList<DialogueButton> buttonsList = new ArrayList<>();

        //Add the Buttons to the Buttons List
        buttonsList.add(createButton);
        buttonsList.add(updateButton);
        buttonsList.add(deleteButton);
        buttonsList.add(viewButton);

        //Check if the Requested Manager is the Projects Manager
        if(requestedManager instanceof ProjectsManager projectsManager) {

            //Setup the Project Find Button
            DialogueButton findButton = new DialogueButton(
                    projectsManager.findListener,
                    "Find Projects"
            );

            //Setup the Project View Overdue Button
            DialogueButton viewOverdueButton = new DialogueButton(
                    projectsManager.viewOverdueListener,
                    "View Overdue Projects"
            );

            //Setup the Project View In Progress Button
            DialogueButton viewInProgressButton = new DialogueButton(
                    projectsManager.viewInProgressListener,
                    "View In-Progress Projects"
            );

            //Setup the Project View Completed Button
            DialogueButton viewCompletedButton = new DialogueButton(
                    projectsManager.viewCompletedListener,
                    "View Completed Projects"
            );

            //Setup the Finalize Project Button
            DialogueButton finalizeProjectButton = new DialogueButton(
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
