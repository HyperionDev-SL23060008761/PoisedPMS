//Setup the Package
package Poised.Utils.Dialogue.Components;

//Imports
import java.awt.event.ActionListener;

/**
 * Contains Data for use with a Button (Such as the Listener and the Name of the Button)
 *
 * @param listener The Listener that will be Run on the Button Click Event
 * @param buttonName The Name of the Button
 */
public record Dialogue_Button(ActionListener listener, String buttonName) { }
