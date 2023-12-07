//Setup the Package
package Poised.Utils.Dialogue;

//Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * A Class for Managing Dialogue to Gather input from the User.
 */
public class UserInputDialogue {

    //Setup the Private Properties
    DialogueManager dialogueManager;
    private Boolean canceled;

    /**
     * Constructor for UserInputDialogue.
     *
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public UserInputDialogue(DialogueManager dialogueManager) {

        //Update the Private Properties
        this.dialogueManager = dialogueManager;
        this.canceled = false;
    }

    /**
     * Shows the Dialogue to the User and Collects the User's Inputs.
     *
     * @param dialogueTitle   The main title to be displayed in the dialogue.
     * @param inputMessages   A list of Strings to be used as the Keys for the Data to be Collected.
     * @return An ArrayList containing the User's Input
     */
    public ArrayList<String> showDialogue(String dialogueTitle, ArrayList<String> inputMessages) {

        //Setup the Dialogue's Main Label
        JLabel dialogueMainLabel = new JLabel(dialogueTitle);

        //Setup the Dialogue's Spacer Label
        JLabel dialogueSpacerLabel = new JLabel(" ");

        //Setup the Dialogue Input Fields
        ArrayList<JTextField> dialogueInputFields = new ArrayList<>();

        //Setup the Dialogue Components
        ArrayList<JComponent> dialogueComponents = new ArrayList<>();

        //Add the Components to the Dialogue Components
        dialogueComponents.add(dialogueMainLabel);
        dialogueComponents.add(dialogueSpacerLabel);

        //Loop through the List of Input Messages
        for (String inputMessage : inputMessages) {

            //Get the Input Field's Label
            JLabel inputFieldLabel = new JLabel(inputMessage);

            //Setup the New Input Field
            JTextField inputField = new JTextField(10);

            //Add the Input Field to the Dialogue Input Fields List
            dialogueInputFields.add(inputField);

            //Add the Label to the Dialogue Components
            dialogueComponents.add(inputFieldLabel);

            //Add the Input Field to the Dialogue Components
            dialogueComponents.add(inputField);
        }

        //Setup the Options List
        ArrayList<Button> dialogueOptions = new ArrayList<>();

        //Setup the Submit Button
        Button submitButton = new Button("Submit");

        //Setup the Cancel Button
        Button cancelButton = new Button("Cancel");

        //Add the Submit Listener to the Submit Button
        submitButton.addActionListener(this.dialogueManager.closeWindowAction);

        //Add the Cancel Listener to the Cancel Button
        cancelButton.addActionListener(this::cancelListener);

        //Add the Submit Button to the Dialogue Options
        dialogueOptions.add(submitButton);

        //Add the Cancel Button to the Dialogue Options
        dialogueOptions.add(cancelButton);

        //Show the Dialogue to the User
        this.dialogueManager.showDialogue("Poised", dialogueComponents, dialogueOptions);

        //Check if the Dialogue was Canceled and return Null
        if(this.canceled) return null;

        //Setup the User Input List
        ArrayList<String> userInputList = new ArrayList<>();

        //Loop through the List of Dialogue Inputs and get the User's Input
        for (JTextField dialogueInputField : dialogueInputFields) {

            //Get the User's Input
            String userInput = dialogueInputField.getText();

            //Add the User's Input to the User Input List
            userInputList.add(userInput);
        }

        //Return the User's Input
        return userInputList;
    }

    /**
     * Handles Cancel Events for a Dialogue.
     *
     * @param event The ActionEvent triggering the method.
     */
    private void cancelListener(ActionEvent event) {

        //Set the Canceled Result to True
        this.canceled = true;

        //Get the Dialogue's Window
        Window dialogueWindow = SwingUtilities.getWindowAncestor(this.dialogueManager.dialogue);

        //Close the Dialogue Window
        dialogueWindow.dispose();
    }
}
