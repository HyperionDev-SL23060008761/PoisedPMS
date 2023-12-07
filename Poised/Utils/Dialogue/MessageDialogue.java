//Setup the Package
package Poised.Utils.Dialogue;

//Imports
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A Class for Managing Message Only Dialogues
 */
public class MessageDialogue {

    //Setup the Private Properties
    DialogueManager dialogueManager;

    /**
     * Constructor for MessageDialogue.
     *
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public MessageDialogue(DialogueManager dialogueManager) {

        //Update the Private Properties
        this.dialogueManager = dialogueManager;
    }

    /**
     * Shows the Dialogue to the User
     *
     * @param dialogueMessage The main message to be displayed in the dialogue.
     */
    public void showDialogue(String dialogueMessage) {

        //Setup the Dialogue's Main Label
        JLabel dialogueMainLabel = new JLabel(dialogueMessage);

        //Setup the Dialogue's Spacer Label
        JLabel dialogueSpacerLabel = new JLabel(" ");

        //Setup the Dialogue Components
        ArrayList<JComponent> dialogueComponents = new ArrayList<>();

        //Add the Components to the Dialogue Components
        dialogueComponents.add(dialogueMainLabel);
        dialogueComponents.add(dialogueSpacerLabel);

        //Setup the Options List
        ArrayList<Button> dialogueOptions = new ArrayList<>();

        //Setup the Submit Button
        Button submitButton = new Button("Close");

        //Add the Submit Listener to the Submit Button
        submitButton.addActionListener(this.dialogueManager.closeWindowAction);

        //Add the Submit Button to the Dialogue Options
        dialogueOptions.add(submitButton);

        //Show the Dialogue to the User
        this.dialogueManager.showDialogue("Poised", dialogueComponents, dialogueOptions);
    }
}
