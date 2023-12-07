//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.BaseManager;
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.Address;
import Poised.Utils.Database.Tables.PersonalDetails;
import Poised.Utils.Database.Tables.Properties.PersonalDetailsProperties;
import Poised.Utils.Dialogue.Components.FormField;
import Poised.Utils.Dialogue.DialogueManager;
import Poised.Utils.Dialogue.SelectItemDialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Personal Details Database Table and the User.
 */
public class PersonalDetailsManager extends BaseManager<PersonalDetails, PersonalDetailsProperties> {

    /**
     * Constructor for PersonalDetailsManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public PersonalDetailsManager(DatabaseManager databaseManager, DialogueManager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.personalDetailsTable, "Personal Details");
    }

    /**
     * Updates the Details of the Selected Personal Details.
     *
     * @param selectedItem The Selected Personal Details.
     * @param newData      The new Data to Update the Selected Personal Details with.
     */
    protected void updateItemDetails(PersonalDetails selectedItem, PersonalDetails newData) {

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
    protected PersonalDetailsProperties itemIDProperty() {

        //Return the ID Property
        return PersonalDetailsProperties.id;
    }

    /**
     * Gets the Personal Details from the User.
     *
     * @return The Personal Details Selected by the User.
     */
    protected PersonalDetails getItemFromUser() {

        //Get the List of Items
        PersonalDetails[] itemList =
                databaseManager.personalDetailsTable.getAll().toArray(PersonalDetails[]::new);

        //Setup the Select Item Dialogue
        SelectItemDialogue<PersonalDetails> selectItemDialogue =
                new SelectItemDialogue<>(dialogueManager, itemList);

        //Get the Selected Item from the User
        PersonalDetails selectedItem =
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
    protected PersonalDetails createItemFromUserInput(HashMap<PersonalDetailsProperties, Object> userInputData) {

        //Get the Name from the User Input Data
        String name = ((String) userInputData.get(PersonalDetailsProperties.Name));

        //Get the Phone Number from the User Input Data
        String phoneNumber = ((String) userInputData.get(PersonalDetailsProperties.Phone_Number));

        //Get the Email from the User Input Data
        String email = ((String) userInputData.get(PersonalDetailsProperties.Email));

        //Get the Address from the User Input Data
        Address address = ((Address) userInputData.get(PersonalDetailsProperties.Address));

        //Setup the New Personal Details
        PersonalDetails newAddress = new PersonalDetails(null, name, phoneNumber, email, address);

        //Return the New Address
        return newAddress;
    }

    /**
     * Gets the Personal Details' Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Personal Details.
     */
    protected HashMap<PersonalDetailsProperties, FormField> getFilledFormFields(PersonalDetails item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Personal Details' Form Fields.
     *
     * @return The List of Form Fields for the Personal Details.
     */
    protected HashMap<PersonalDetailsProperties, FormField> getFormFields() {

        //Return the List of Form Fields for the Item
        return PersonalDetails.getFormFields(databaseManager);
    }
}
