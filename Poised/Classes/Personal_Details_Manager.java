//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.Base_Manager;
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Address;
import Poised.Utils.Database.Tables.Personal_Details;
import Poised.Utils.Database.Tables.Properties.Personal_Details_Properties;
import Poised.Utils.Dialogue.Components.Form_Field;
import Poised.Utils.Dialogue.Dialogue_Manager;
import Poised.Utils.Dialogue.Select_Item_Dialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Personal Details Database Table and the User.
 */
public class Personal_Details_Manager extends Base_Manager<Personal_Details, Personal_Details_Properties> {

    /**
     * Constructor for Personal_Details_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public Personal_Details_Manager(Database_Manager databaseManager, Dialogue_Manager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.personalDetailsTable, "Personal Details");
    }

    /**
     * Updates the Details of the Selected Personal Details.
     *
     * @param selectedItem The Selected Personal Details.
     * @param newData      The new Data to Update the Selected Personal Details with.
     */
    protected void updateItemDetails(Personal_Details selectedItem, Personal_Details newData) {

        //Update the Selected Item's Details
        selectedItem.name = newData.name;
        selectedItem.phoneNumber = newData.phoneNumber;
        selectedItem.email = newData.email;
        selectedItem.address = newData.address;
    }


    /**
     * Gets the Personal Details' ID Property.
     *
     * @return The Personal Details' ID Property.
     */
    protected Personal_Details_Properties itemIDProperty() {

        //Return the ID Property
        return Personal_Details_Properties.id;
    }

    /**
     * Gets the Personal Details from the User.
     *
     * @return The Personal Details Selected by the User.
     */
    protected Personal_Details getItemFromUser() {

        //Get the List of Items
        Personal_Details[] itemList =
                databaseManager.personalDetailsTable.getAll().toArray(Personal_Details[]::new);

        //Setup the Select Item Dialogue
        Select_Item_Dialogue<Personal_Details> selectItemDialogue =
                new Select_Item_Dialogue<>(dialogueManager, itemList);

        //Get the Selected Item from the User
        Personal_Details selectedItem =
                selectItemDialogue.showDialogue("Please choose Personal Details from the List");

        //Return the Selected Item
        return selectedItem;
    }

     /**
     * Creates a New Personal Details from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Personal Details created from the User's Input Data.
     */
    protected Personal_Details createItemFromUserInput(HashMap<Personal_Details_Properties, Object> userInputData) {

        //Get the Name from the User Input Data
        String name = ((String) userInputData.get(Personal_Details_Properties.Name));

        //Get the Phone Number from the User Input Data
        String phoneNumber = ((String) userInputData.get(Personal_Details_Properties.Phone_Number));

        //Get the Email from the User Input Data
        String email = ((String) userInputData.get(Personal_Details_Properties.Email));

        //Get the Address from the User Input Data
        Address address = ((Address) userInputData.get(Personal_Details_Properties.Address));

        //Setup the New Personal Details
        Personal_Details newAddress = new Personal_Details(null, name, phoneNumber, email, address);

        //Return the New Address
        return newAddress;
    }

    /**
     * Gets the Personal Details' Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Personal Details.
     */
    protected HashMap<Personal_Details_Properties, Form_Field> getFilledFormFields(Personal_Details item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Personal Details' Form Fields.
     *
     * @return The List of Form Fields for the Personal Details.
     */
    protected HashMap<Personal_Details_Properties, Form_Field> getFormFields() {

        //Return the List of Form Fields for the Item
        return Personal_Details.getFormFields(databaseManager);
    }
}
