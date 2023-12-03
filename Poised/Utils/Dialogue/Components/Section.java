//Setup the Package
package Poised.Utils.Dialogue.Components;

//Import
import Poised.Utils.Dialogue.Dialogue_Manager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A supportive Component for Generating Sectioned Dialogues to hold Specified Components.
 */
public class Section {

    //Setup the Private Properties
    private final Dialogue_Manager dialogueManager;
    private final String sectionTitle;
    private final ArrayList<Dialogue_Button> buttons;

    /**
     * Constructor for Section.
     *
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     * @param sectionTitle The Title for the Section Component
     * @param sectionButtons The List of Buttons to be Added to the Section.
     */
    public Section(Dialogue_Manager dialogueManager, String sectionTitle, ArrayList<Dialogue_Button> sectionButtons) {

        //Update the Private Properties
        this.dialogueManager = dialogueManager;
        this.sectionTitle = sectionTitle;
        this.buttons = sectionButtons;
    }

    /**
     * Shows the Section to the User
     */
    public void open() {

        //Setup the Dialogue's Title
        JLabel dialogueTitle = new JLabel(sectionTitle);

        //Setup the Dialogue's Main Label
        JLabel dialogueMainLabel = new JLabel("Please Select an Action you would like to perform:");

        //Setup the Dialogue's Spacer Label
        JLabel dialogueSpacerLabel = new JLabel(" ");

        //Setup the Dialogue Components
        ArrayList<JComponent> dialogueComponents = new ArrayList<>();

        //Add the Components to the Dialogue Components
        dialogueComponents.add(dialogueTitle);
        dialogueComponents.add(dialogueMainLabel);
        dialogueComponents.add(dialogueSpacerLabel);

        //Setup the Dialogue Options List
        ArrayList<Button> dialogueOptions = new ArrayList<>();

        for (Dialogue_Button dialogueButton : this.buttons) {

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
}
