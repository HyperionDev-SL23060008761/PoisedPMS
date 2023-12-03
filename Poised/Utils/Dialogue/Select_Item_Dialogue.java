//Setup the Package
package Poised.Utils.Dialogue;

//Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * A Class for Managing Dialogue for a User to Select from a List of Items.
 *
 * @param <Item_Type> The type of Item that will be Returned
 */
public class Select_Item_Dialogue<Item_Type> {

    //Setup the Private Properties
    private final Dialogue_Manager dialogueManager;
    private final Item_Type[] itemList;
    private Boolean canceled;

    /**
     * Constructor for Select_Item_Dialogue.
     *
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     * @param itemList        The List of Items for the User to Choose From.
     */
    public Select_Item_Dialogue(Dialogue_Manager dialogueManager, Item_Type[] itemList) {

        //Update the Private Properties
        this.dialogueManager = dialogueManager;
        this.itemList = itemList;
        this.canceled = false;
    }

    /**
     * Shows the Dialogue to the User and Collects the Selected Item from the User.
     *
     * @param dialogueMessage The main message to be displayed in the dialogue.
     * @return The Selected Item.
     */
    public Item_Type showDialogue(String dialogueMessage) {

        //Setup the Dialogue's Main Label
        JLabel dialogueMainLabel = new JLabel(dialogueMessage);

        //Setup the Dialogue's Spacer Label
        JLabel dialogueSpacerLabel = new JLabel(" ");

        //Setup the Dialogue Components
        ArrayList<JComponent> dialogueComponents = new ArrayList<>();

        //Add the Components to the Dialogue Components
        dialogueComponents.add(dialogueMainLabel);
        dialogueComponents.add(dialogueSpacerLabel);

        //Create the Select Box
        JComboBox<Item_Type> selectBox = new JComboBox<>(this.itemList);

        //Add the Select Box to the Dialogue Components
        dialogueComponents.add(selectBox);

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

        //Get the Selected Item's Index from the User
        int selectedItemIndex = selectBox.getSelectedIndex();

        //Get the Selected Item from the Selected Item's Index
        Item_Type selectedItem = selectBox.getItemAt(selectedItemIndex);

        //Return the Selected Item
        return selectedItem;
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
