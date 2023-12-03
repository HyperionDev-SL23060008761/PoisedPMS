//Setup the Package
package Poised.Utils.Database.Utils;

//Imports
import Poised.Utils.Database.Database_Manager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * An Abstract Base Class for Managing Manipulation of the Different Tables.
 *
 * @param <Table_Type> The Table Managed by the Manager.
 */
public abstract class Base_Table_Manager<Table_Type> {

    //Setup the Protected Properties
    protected final Database_Manager databaseManager;

    //Setup the Private Properties
    protected final Statement queryManager;
    private final String tableName;

    /**
     * Constructor for Base_Table_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @param tableName       The Name of the Table Managed by the Manager.
     */

    public Base_Table_Manager(Database_Manager databaseManager, String tableName) {

        //Setup the Query Manager
        Statement queryManager = null;

        //Attempt to Update the Query Manager
        try {

            //Update the Query Manager
            queryManager = databaseManager.connection.createStatement();
        } catch (java.sql.SQLException exception) {

            //Log the Exception to the Console
            System.out.printf("%nSQL ERROR:%n%s%n", exception);
        }

        //Update the Protected Properties
        this.databaseManager = databaseManager;

        //Update the Private Properties
        this.tableName = tableName;
        this.queryManager = queryManager;
    }

    /**
     * Gets a List of all the Formatted Items from the Database.
     *
     * @return An ArrayList of Formatted Items from the Database.
     */
    public ArrayList<Table_Type> getAll() {

        //Setup the Item List
        ArrayList<Table_Type> itemList = new ArrayList<>();

        //Setup the List of Items in the Database
        ResultSet itemsInDatabase;

        //Setup the Query String
        String queryString = String.format("SELECT * FROM %s", this.tableName);

        //Try to Get the List of Items from the Database
        try {

            //Get the List of Items from the Database
            itemsInDatabase = this.queryManager.executeQuery(queryString);

            //Loop through the List of Items in the Database
            while(itemsInDatabase.next()) {

                //Setup the New Item
                Table_Type newItem = buildFromQueryData(itemsInDatabase);

                //Add the New Item to the Items List
                itemList.add(newItem);
            }
        } catch (SQLException exception) {

            //Log the Exception to the Console
            System.out.printf("%nSQL ERROR:%n%s%n", exception);
        }

        //Return the Item List
        return itemList;
    }

    /**
     * Finds an Item in the Database using its ID.
     *
     * @param itemID The ID of the Item to Find.
     * @return The requested Item or Null if the Item Could not be Found.
     */
    public Table_Type findByID(int itemID) {

        //Setup the Requested Item
        Table_Type requestedItem = null;

        //Setup the List of Items in the Database
        ResultSet itemsInDatabase;

        //Setup the Query String
        String queryString = String.format("SELECT * FROM %s WHERE id = %s LIMIT 1", tableName, itemID);

        //Try to Get the List of Items from the Database
        try {

            //Get the List of Items from the Database
            itemsInDatabase = this.queryManager.executeQuery(queryString);

            //Check if the Item was Found and Set the Requested Item to the Found Item
            if(itemsInDatabase.next()) requestedItem = buildFromQueryData(itemsInDatabase);
        } catch (SQLException exception) {

            //Log the Exception to the Console
            System.out.printf("%nSQL ERROR:%n%s%n", exception);
        }

        //Return the Requested Item
        return requestedItem;
    }

    /**
     * Inserts a New Item into the Database.
     *
     * @param item The Item to Insert.
     * @return boolean Result of whether the New Item could be Inserted.
     */
    public boolean insert(Table_Type item) {

        //Check if an Invalid Item was Provided and end the Insert
        if(item == null) return false;

        //Setup the Variable to check if the Item already Exists
        boolean itemExists = itemExists(item);

        //Check if the Item Already Exists and end the Creation
        if(itemExists) return false;

        //Get the List of Properties from the New Item
        ArrayList<String> properties = getProperties();

        //Get the List of Values from the New Item
        ArrayList<String> values = getValues(item);

        //Inserts the Row into the Database
        return SQL.insert(this.queryManager, tableName, properties, values);
    }

    /**
     * Updates an Item in the Database.
     *
     * @param item The Item to Update.
     * @return boolean Result of whether the Item could be Updated.
     */
    public boolean update(Table_Type item) {

        //Check if an Invalid Item was Provided and end the Update
        if(item == null) return false;

        //Setup the Variable to check if the Item Exists in the Database
        boolean itemExists = itemExists(item);

        //Check if the Item Does not Exist and end the Update
        if(!itemExists) return false;

        //Get the Properties
        ArrayList<String> properties = getProperties();

        //Get the Values
        ArrayList<String> values = getValues(item);

        //Update the Row in the Database
        return SQL.update(this.queryManager, tableName, properties, values);
    }

    /**
     * Removes an Item from the Database.
     *
     * @param item The Item to Remove.
     * @return boolean Result of whether the Item could be Removed.
     */
    public boolean remove(Table_Type item) {

        //Check if an Invalid Item was Provided and end the Removal
        if(item == null) return false;

        //Setup the Variable to check if the Item Exists in the Database
        boolean itemExists = itemExists(item);

        //Check if the Item Does not Exist and end the Removal
        if(!itemExists) return false;

        //Get the Properties
        ArrayList<String> properties = getProperties();

        //Get the Values
        ArrayList<String> values = getValues(item);

        //Remove the Row from the Database
        return SQL.remove(this.queryManager, tableName, properties, values);
    }

    /**
     * Builds/Formats the Item using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted item.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected abstract Table_Type buildFromQueryData(ResultSet queryResults) throws SQLException;

    /**
     * Checks if an Item Exists in the Database
     *
     * @param item The Item to Check.
     * @return boolean Result of whether the Item Exists.
     */
    protected abstract boolean itemExists(Table_Type item);

    /**
     * Returns the List of Property Names of an Item.
     *
     * @return An ArrayList of the Item's Property Names.
     */
    protected abstract ArrayList<String> getProperties();

    /**
     * Returns the List of Values of an Item.
     *
     * @param item The Selected Item.
     * @return An ArrayList of the Item's Values.
     */
    protected abstract ArrayList<String> getValues(Table_Type item);
}
