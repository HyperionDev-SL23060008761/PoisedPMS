//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.BaseManager;
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.Address;
import Poised.Utils.Database.Tables.Properties.AddressProperties;
import Poised.Utils.Dialogue.Components.FormField;
import Poised.Utils.Dialogue.DialogueManager;
import Poised.Utils.Dialogue.SelectItemDialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Addresses Database Table and the User.
 */
public class AddressesManager extends BaseManager<Address, AddressProperties> {

    /**
     * Constructor for AddressesManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public AddressesManager(DatabaseManager databaseManager, DialogueManager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.addressesTable, "Address");
    }

    /**
     * Updates the Details of the Selected Address.
     *
     * @param selectedItem The Selected Address.
     * @param newData      The new Data to Update the Selected Address with.
     */
    protected void updateItemDetails(Address selectedItem, Address newData) {

        //Update the Selected Item's Details
        selectedItem.streetNumber = newData.streetNumber;
        selectedItem.streetName = newData.streetName;
        selectedItem.city = newData.city;
        selectedItem.region = newData.region;
        selectedItem.postalCode = newData.postalCode;

    }

    /**
     * Gets the Address' ID Property.
     *
     * @return The Address' ID Property.
     */
    protected AddressProperties itemIDProperty() {

        //Return the ID Property
        return AddressProperties.id;
    }

    /**
     * Gets the Address from the User.
     *
     * @return The Address Selected by the User.
     */
    protected Address getItemFromUser() {

        //Get the List of Addresses
        Address[] addressList = databaseManager.addressesTable.getAll().toArray(Address[]::new);

        //Setup the Select Item Dialogue
        SelectItemDialogue<Address> selectItemDialogue = new SelectItemDialogue<>(dialogueManager, addressList);

        //Get the Selected Address from the User
        Address selectedAddress =
                selectItemDialogue.showDialogue("Please choose an Address from the List");

        //Return the Selected Address
        return selectedAddress;
    }

     /**
     * Creates a New Address from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Address created from the User's Input Data.
     */
    protected Address createItemFromUserInput(HashMap<AddressProperties, Object> userInputData) {

        //Get the Street Number's String from the User Input Data
        String streetNumberString =
                ((String) userInputData.get(AddressProperties.Street_Number)).replaceAll("\\D", "");

        //Get the Street Name from the User Input Data
        String streetName = ((String) userInputData.get(AddressProperties.Street_Name));

        //Get the City from the User Input Data
        String city = ((String) userInputData.get(AddressProperties.City));

        //Get the Region from the User Input Data
        String region = ((String) userInputData.get(AddressProperties.Region));

        //Get the Postal Code's String from the User Input Data
        String postalCodeString =
                ((String) userInputData.get(AddressProperties.Postal_Code)).replaceAll("\\D", "");

        //Check if an Invalid Street Number was Provided
        if(streetNumberString.isEmpty()) {

            //Let the User know they provided an Invalid Street Number
            dialogueManager.messageDialogue.showDialogue("Invalid Street Number (Only Numbers allowed)");

            //End the Creation
            return null;
        }

        //Check if an Invalid Postal Code was Provided
        if(postalCodeString.isEmpty()) {

            //Let the User know they provided an Invalid Street Number
            dialogueManager.messageDialogue.showDialogue("Invalid Postal Code (Only Numbers allowed)");

            //End the Creation
            return null;
        }

        //Setup the Street Number
        int streetNumber = Integer.parseInt(streetNumberString);

        //Setup the Postal Code
        int postalCode = Integer.parseInt(postalCodeString);

        //Setup the New Address
        Address newAddress = new Address(null, streetNumber, streetName, city, region, postalCode);

        //Return the New Address
        return newAddress;
    }

    /**
     * Gets the Address' Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Address.
     */
    protected HashMap<AddressProperties, FormField> getFilledFormFields(Address item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Address' Form Fields.
     *
     * @return The List of Form Fields for the Address.
     */
    protected HashMap<AddressProperties, FormField> getFormFields() {

        //Return the List of Form Fields for the Item
        return Address.getFormFields(databaseManager);
    }
}
