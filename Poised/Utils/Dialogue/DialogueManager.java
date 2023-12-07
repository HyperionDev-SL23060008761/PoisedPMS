//Setup the Package
package Poised.Utils.Dialogue;

//Imports

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A Class for Managing Dialogue Between the User and the Components.
 */
public class DialogueManager {

    //Setup the Public Attributes
    public JPanel dialogue;
    public MainMenu mainMenu;
    public UserInputDialogue userInputDialogue;
    public MessageDialogue messageDialogue;
    public ShowAllItemsDialogue showAllItemsDialogue;

    //Setup the Private Attributes
    ActionListener closeWindowAction;

    /**
     * Constructor for DialogueManager.
     */
    public DialogueManager() {

        //Update the Public Attributes
        this.dialogue = new JPanel();
        this.mainMenu = new MainMenu(this);
        this.userInputDialogue = new UserInputDialogue(this);
        this.messageDialogue = new MessageDialogue(this);
        this.showAllItemsDialogue = new ShowAllItemsDialogue(this);

        //Update the Private Attributes
        this.closeWindowAction = e -> {

            //Get the Dialogue's Window
            Window dialogueWindow = SwingUtilities.getWindowAncestor(dialogue);

            //Close the Dialogue Window
            dialogueWindow.dispose();
        };
    }

    /**
     * Shows the Dialogue using the Specified Options.
     *
     * @param dialogueTitle         The Title of the Dialogue.
     * @param dialogueComponents    List of Components to be Added on to the Dialogue.
     * @param dialogueOptions       List of Options (Buttons) for the Dialogue.
     */
    public void showDialogue(
            String dialogueTitle,
            ArrayList<JComponent> dialogueComponents,
            ArrayList<Button> dialogueOptions
    ) {

        //Clear the Original Dialogue Contents
        this.dialogue.removeAll();

        //Setup the Dialogue's Layout
        this.dialogue.setLayout(new BoxLayout(this.dialogue, BoxLayout.Y_AXIS));

        //Loop through the Dialogue Components
        for (JComponent dialogueComponent : dialogueComponents) {

            //Add the Component to the Dialogue
            this.dialogue.add(dialogueComponent);
        }

        //Show the Dialogue
        JOptionPane.showOptionDialog(
                null,
                this.dialogue,
                dialogueTitle,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                dialogueOptions.toArray(),
                null
        );
    }
}
