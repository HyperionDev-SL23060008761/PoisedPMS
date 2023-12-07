//Setup the Package
package Poised.Utils.Database.Table_Managers;

//Imports
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.Address;
import Poised.Utils.Database.Tables.Properties.AddressProperties;
import Poised.Utils.Database.Utils.BaseTableManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A Class for Managing and Manipulation of the Addresses Table.
 */
public class AddressesTableManager extends BaseTableManager<Address> {

    /**
     * Constructor for AddressesTableManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     */
    public AddressesTableManager(DatabaseManager databaseManager) {

        //Construct the Parent Class
        super(databaseManager, "Addresses");
    }

    /**
     * Builds/Formats the Address using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted address.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected Address buildFromQueryData(ResultSet queryResults) throws SQLException {

        //Get the Address' Data
        int addressID = queryResults.getInt("id");
        int streetNumber = queryResults.getInt("Street_Number");
        String streetName = queryResults.getString("Street_Name");
        String city = queryResults.getString("City");
        String region = queryResults.getString("Region");
        int postalCode = queryResults.getInt("Postal_Code");

        //Setup the New Address
        Address newAddress = new Address(addressID, streetNumber, streetName, city, region, postalCode);

        //return the New Address
        return newAddress;
    }

    /**
     * Checks if an Address Exists in the Database
     *
     * @param address The Address to Check.
     * @return boolean Result of whether the Address Exists.
     */
    protected boolean itemExists(Address address) {

        //Setup the Variable to check if the Address Exists in the Database
        boolean addressExists = super.findByID(address.id) != null;

        //Return the Result
        return addressExists;
    }

    /**
     * Returns the List of Property Names of an Address.
     *
     * @return An ArrayList of the Address's Property Names.
     */
    protected ArrayList<String> getProperties() {

        //Get the List of Properties
        ArrayList<AddressProperties> properties = Address.getProperties();

        //Convert the List of Properties to a String Array
        ArrayList<String> propertyStrings = new ArrayList<>(properties.stream().map(Enum::name).toList());

        //Return the Property String List
        return propertyStrings;
    }

    /**
     * Returns the List of Values of an Address.
     *
     * @return An ArrayList of the Address's Values.
     */
    protected ArrayList<String> getValues(Address address) {

        //Get the List of Values for the Address
        ArrayList<String> values = address.getValues();

        //Return the List of Values
        return values;
    }
}
