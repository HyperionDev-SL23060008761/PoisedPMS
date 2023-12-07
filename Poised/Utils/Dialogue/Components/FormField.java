//Setup the Package
package Poised.Utils.Dialogue.Components;

//Imports
import javax.swing.*;

/**
 * Contains Data to be Used in a Form (Such as the Field's Label and Input Component)
 *
 * @param fieldLabel The Label for the Request Form's Input Field
 * @param fieldInput The Input Component of the Field
 */
public record FormField(JLabel fieldLabel, JComponent fieldInput) {

}