//Setup the Package
package Poised.Utils.Dialogue;

//Imports
import Poised.Utils.Dialogue.Components.DateInput;
import Poised.Utils.Dialogue.Components.FormField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * A Class for Managing Form Dialogues.
 *
 * @param <Table_Properties> The Properties for the Specified Table
 */
public class FormDialogue<Table_Properties> {

    //Setup the Private Properties
    private final DialogueManager dialogueManager;
    private Boolean canceled;

    /**
     * Constructor for FormDialogue.
     *
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public FormDialogue(DialogueManager dialogueManager) {

        //Update the Private Properties
        this.dialogueManager = dialogueManager;
        this.canceled = false;
    }

    /**
     * Shows the Dialogue to the User and Collects the User's Inputs.
     *
     * @param dialogueMessage The main message to be displayed in the dialogue.
     * @param formFields      A map of form properties to their corresponding form fields.
     * @return A map of User Inputs with the Related Table's Properties as Keys.
     */
    public HashMap<Table_Properties, Object> showDialogue(
            String dialogueMessage,
            HashMap<Table_Properties, FormField> formFields
    ) {

        //Setup the Dialogue's Main Label
        JLabel dialogueMainLabel = new JLabel(dialogueMessage);

        //Setup the Dialogue's Spacer Label
        JLabel dialogueSpacerLabel = new JLabel(" ");

        //Setup the Dialogue Components
        ArrayList<JComponent> dialogueComponents = new ArrayList<>();

        //Add the Components to the Dialogue Components
        dialogueComponents.add(dialogueMainLabel);
        dialogueComponents.add(dialogueSpacerLabel);

        //Get the List of Form Field Keys
        Table_Properties[] formFieldKeys = (Table_Properties[]) formFields.keySet().toArray();

        //Get the List of Form Field Values
        FormField[] formFieldValues = formFields.values().toArray(FormField[]::new);

        //Loop through the List of Form Field Values
        for (FormField currentField : formFieldValues) {

            //Get the Label Field
            JLabel labelField = currentField.fieldLabel();

            //Get the Input Field
            JComponent inputField = currentField.fieldInput();

            //Add the Form Field's Label to the Dialogue Components List
            dialogueComponents.add(labelField);

            //Check if the Input Field is a Date Input
            if(inputField instanceof DateInput dateInput){

                //Add the Date Picker to the Dialogue Components
                dialogueComponents.add(dateInput.inputComponent);

                //Continue to the Next Loop
                continue;
            }

            //Add the Form Field's Input to the Dialogue Components List
            dialogueComponents.add(inputField);
        }

        //Setup the Options List
        ArrayList<Button> dialogueOptions = new ArrayList<>();

        //Setup the Submit Button
        Button submitButton = new Button("Continue");

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

        //Setup the List of User Inputs
        HashMap<Table_Properties, Object> userInputs = new HashMap<>();

        //Loop through the Form Field Values
        for (int i = 0; i < formFieldValues.length; i++) {

            //Get the Current Key for the Form Field
            Table_Properties currentFormFieldKey = formFieldKeys[i];

            //Get the Current Form Field Value
            FormField currentFormFieldValue = formFieldValues[i];

            //Get the Input Field
            JComponent inputField = currentFormFieldValue.fieldInput();

            //Check if the input field is a Select Box
            if(inputField instanceof JComboBox<?> selectBox) {

                //Get the User's Input
                Object userInput = selectBox.getSelectedItem();

                //Add the user's Input to the User Inputs List
                userInputs.put(currentFormFieldKey, userInput);

                //Continue to the Next Item
                continue;
            }

            //Check if the input field is a Text Field
            if(inputField instanceof JTextField textInput) {

                //Get the User's Input
                String userInput = textInput.getText();

                //Add the user's Input to the User Inputs List
                userInputs.put(currentFormFieldKey, userInput);

                //Continue to the Next Item
                continue;
            }

            //Check if the input field is a Date Field
            if(inputField instanceof DateInput) {

                //Get the User's Input
                Date userInput = ((DateInput) inputField).getSelectedDate();

                //Add the user's Input to the User Inputs List
                userInputs.put(currentFormFieldKey, userInput);
            }
        }

        //Return the List of User Inputs
        return userInputs;
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
