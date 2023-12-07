//Setup the Package
package Poised.Utils;

//Imports
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Utils.BaseTableManager;
import Poised.Utils.Dialogue.Components.FormField;
import Poised.Utils.Dialogue.DialogueManager;
import Poised.Utils.Dialogue.FormDialogue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * An Abstract Base Class for managing the Interactions Between a Database Table and the User.
 *
 * @param <Table_Type>        The Table Managed by the Manager.
 * @param <Table_Properties>  The Table's Properties.
 */
public abstract class BaseManager<Table_Type, Table_Properties> {

    //Setup the Public Properties
    public ActionListener createListener;
    public ActionListener updateListener;
    public ActionListener deleteListener;
    public ActionListener viewListener;

    //Setup the Protected Properties
    protected final DatabaseManager databaseManager;
    protected final DialogueManager dialogueManager;
    protected final BaseTableManager<Table_Type> tableManager;

    //Setup the Private Properties
    private final String tableName;

    /**
     * Constructor for BaseManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param dialogueManager The Dialogue Manager that will Handle all the dialogue Interactions.
     * @param tableManager    The Manager for the Table Manipulations.
     * @param tableName       The Name of the Table Managed by the Manager.
     */
    public BaseManager(
            DatabaseManager databaseManager,
            DialogueManager dialogueManager,
            BaseTableManager<Table_Type> tableManager,
            String tableName
    ) {


        //Update the Public Properties
        this.createListener = this::create;
        this.updateListener = this::update;
        this.deleteListener = this::delete;
        this.viewListener = this::view;

        //Update the Protected Properties
        this.databaseManager = databaseManager;
        this.dialogueManager = dialogueManager;
        this.tableManager = tableManager;

        //Update the Private Properties
        this.tableName = tableName;
    }

    /**
     * Shows a List of all the Items.
     *
     * @param event The Event triggering the method.
     */
    public void view(ActionEvent event) {

        //Get the List of All Items
        ArrayList<Table_Type> itemList = tableManager.getAll();

        //Setup the List of Item Strings
        String[] itemStrings = itemList.stream().map(Object::toString).toArray(String[]::new);

        //Show the List of All Items
        dialogueManager.showAllItemsDialogue.showDialogue(tableName, itemStrings);
    }

    /**
     * Creates a New Item.
     *
     * @param event The Event triggering the method.
     */
    public void create(ActionEvent event) {

        //Setup the Form Fields for this Item
        HashMap<Table_Properties, FormField> formFields = getFormFields();

        //Get the Item ID Property
        Table_Properties itemIDProperty = itemIDProperty();

        //Remove the Item's ID from the Form Fields
        formFields.remove(itemIDProperty);

        //Setup the Form Dialogue
        FormDialogue<Table_Properties> formDialogue = new FormDialogue<>(dialogueManager);

        //Get the Item Data from the User
        HashMap<Table_Properties, Object> itemData = formDialogue.showDialogue(
                String.format("New %s", tableName),
                formFields
        );

        //Check if the User Canceled and End the Creation
        if (itemData == null) return;

        //Get the New Rol using the Item Data
        Table_Type newItem = createItemFromUserInput(itemData);

        //Attempt to Insert the Item and Save the Result
        boolean insertResult = tableManager.insert(newItem);

        //Check if the Item could not be Inserted
        if (!insertResult)
            dialogueManager.messageDialogue.showDialogue(String.format("Unable to Create a New %s", tableName));

        //Check if the Item Was successfully Inserted not be Inserted
        if (insertResult)
            dialogueManager.messageDialogue.showDialogue(String.format("%s Successfully Created", tableName));
    }

    /**
     * Updates an Item.
     *
     * @param event The Event triggering the method.
     */
    public void update(ActionEvent event) {

        //Get the Selected Item from the User
        Table_Type selectedItem = getItemFromUser();

        //Check if the User Canceled
        if (selectedItem == null) return;

        //Get the List Filled Form Fields for this Item
        HashMap<Table_Properties, FormField> filledFormFields = getFilledFormFields(selectedItem);

        //Get the Item ID Property
        Table_Properties itemIDProperty = itemIDProperty();

        //Remove the Item's ID from the Form Fields
        filledFormFields.remove(itemIDProperty);

        //Setup the Form Dialogue
        FormDialogue<Table_Properties> formDialogue = new FormDialogue<>(dialogueManager);

        //Get the Item Data from the User
        HashMap<Table_Properties, Object> itemData = formDialogue.showDialogue(
                String.format("Update %s", tableName),
                filledFormFields
        );

        //Check if the User Canceled and End the Update
        if (itemData == null) return;

        //Create the New Item from the Item Data
        Table_Type newItem = createItemFromUserInput(itemData);

        //Update the Selected Item's Details
        updateItemDetails(selectedItem, newItem);

        //Attempt to Update the Item and Save the Result
        boolean updateResult = tableManager.update(selectedItem);

        //Check if the Update Failed
        if (!updateResult)
            dialogueManager.messageDialogue.showDialogue(String.format("Unable to Update the %s", tableName));

        //Check if the Update Was Successful
        if (updateResult)
            dialogueManager.messageDialogue.showDialogue(String.format("%s Successfully Updated", tableName));
    }

    /**
     * Deletes an Item.
     *
     * @param event The Event triggering the method.
     */
    public void delete(ActionEvent event) {

        //Get the Selected Item from the User
        Table_Type selectedItem = getItemFromUser();

        //Check if the User Canceled
        if (selectedItem == null) return;

        //Attempt to Delete the Item and Save the Result
        boolean deleteResult = tableManager.remove(selectedItem);

        //Check if the Item Failed to Delete
        if (!deleteResult)
            dialogueManager.messageDialogue.showDialogue(
                    String.format("Unable to Delete the %s, They are currently referenced by another table", tableName)
            );

        //Check if the Item Deleted Successfully
        if (deleteResult)
            dialogueManager.messageDialogue.showDialogue(String.format("%s Successfully Deleted", tableName));
    }

    /**
     * Gets the Item's ID Property.
     *
     * @return The Item's ID Property.
     */
    protected abstract Table_Properties itemIDProperty();

    /**
     * Gets the Item from the User.
     *
     * @return The Item Selected by the User.
     */
    protected abstract Table_Type getItemFromUser();

    /**
     * Creates a New Item from the User's Input Data.
     *
     * @param userInputData The User's Input Data containing the New Data.
     * @return The new Item created from the User's Input Data.
     */
    protected abstract Table_Type createItemFromUserInput(HashMap<Table_Properties, Object> userInputData);

    /**
     * Updates the Details of the Selected Item.
     *
     * @param selectedItem The Selected Item.
     * @param newItem      The new Data to Update the Selected Item with.
     */
    protected abstract void updateItemDetails(Table_Type selectedItem, Table_Type newItem);

    /**
     * Gets the Item's Filled Form Fields.
     *
     * @param item The Selected Item.
     * @return The List of Filled Form Fields for the item.
     */
    protected abstract HashMap<Table_Properties, FormField> getFilledFormFields(Table_Type item);

    /**
     * Gets the Item's Form Fields.
     *
     * @return The List of Form Fields for the Item.
     */
    protected abstract HashMap<Table_Properties, FormField> getFormFields();
}
