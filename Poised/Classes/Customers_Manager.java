//Setup this Package
package Poised.Classes;

//Imports
import Poised.Utils.Base_Manager;
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Customer;
import Poised.Utils.Database.Tables.Personal_Details;
import Poised.Utils.Database.Tables.Properties.Role_Properties;
import Poised.Utils.Dialogue.Components.Form_Field;
import Poised.Utils.Dialogue.Dialogue_Manager;
import Poised.Utils.Dialogue.Select_Item_Dialogue;
import java.util.HashMap;

/**
 * A Class for managing the Interactions Between the Customers Database Table and the User.
 */
public class Customers_Manager extends Base_Manager<Customer, Role_Properties> {

    /**
     * Constructor for Customers_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     */
    public Customers_Manager(Database_Manager databaseManager, Dialogue_Manager dialogueManager) {

        //Construct the Parent Class
        super(databaseManager, dialogueManager, databaseManager.customersTable, "Customer");
    }

    /**
     * Updates the Details of the Selected Customer.
     *
     * @param selectedItem The Selected Customer.
     * @param newData      The new Data to Update the Selected Customer with.
     */
    protected void updateItemDetails(Customer selectedItem, Customer newData) {

        //Update the Selected Item's Details
        selectedItem.personalDetails = newData.personalDetails;
    }

    /**
     * Gets the Customer's ID Property.
     *
     * @return The Customer's ID Property.
     */
    protected Role_Properties itemIDProperty() {

        //Return the ID Property
        return Role_Properties.id;
    }

    /**
     * Gets the Customer from the User.
     *
     * @return The Customer Selected by the User.
     */
    protected Customer getItemFromUser() {

        //Get the List of Items
        Customer[] itemList = databaseManager.customersTable.getAll().toArray(Customer[]::new);

        //Setup the Select Item Dialogue
        Select_Item_Dialogue<Customer> selectItemDialogue = new Select_Item_Dialogue<>(dialogueManager, itemList);

        //Get the Selected Item from the User
        Customer selectedItem =
                selectItemDialogue.showDialogue("Please choose a Customer from the List");

        //Return the Selected Item
        return selectedItem;
    }

     /**
     * Creates a New Customer from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Customer created from the User's Input Data.
     */
    protected Customer createItemFromUserInput(HashMap<Role_Properties, Object> userInputData) {

        //Get the Customer's Person Details from the User Input Data
        Personal_Details personalDetails = ((Personal_Details) userInputData.get(Role_Properties.Personal_Details));

        //Setup the New Customer
        Customer newCustomer = new Customer(null, personalDetails);

        //Return the New Customer
        return newCustomer;
    }

    /**
     * Gets the Customer's Filled Form Fields.
     *
     * @return The List of Filled Form Fields for the Customer.
     */
    protected HashMap<Role_Properties, Form_Field> getFilledFormFields(Customer item) {

        //Return the List of Filled Form Fields for the Item
        return item.getFilledFormFields(databaseManager);
    }

    /**
     * Gets the Customer's Form Fields.
     *
     * @return The List of Form Fields for the Customer.
     */
    protected HashMap<Role_Properties, Form_Field> getFormFields() {

        //Return the List of Form Fields for the Item
        return Customer.getFormFields(databaseManager);
    }
}
