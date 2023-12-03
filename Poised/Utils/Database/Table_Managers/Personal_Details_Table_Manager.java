//Setup the Package
package Poised.Utils.Database.Table_Managers;

//Imports
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Address;
import Poised.Utils.Database.Tables.Personal_Details;
import Poised.Utils.Database.Tables.Properties.Personal_Details_Properties;
import Poised.Utils.Database.Utils.Base_Table_Manager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A Class for Managing and Manipulation of the Personal Details Table.
 */
public class Personal_Details_Table_Manager extends Base_Table_Manager<Personal_Details> {

    /**
     * Constructor for Personal_Details_Table_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     */
    public Personal_Details_Table_Manager(Database_Manager databaseManager) {

        //Construct the Parent Class
        super(databaseManager, "Personal_Details");
    }

    /**
     * Builds/Formats the Personal Details using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted Personal Details.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected Personal_Details buildFromQueryData(ResultSet queryResults) throws SQLException {

        //Get the Personal Details' Data
        int personalDetailsID = queryResults.getInt("id");
        String name = queryResults.getString("Name");
        String phoneNumber = queryResults.getString("Phone_Number");
        String email = queryResults.getString("Email");
        int addressID = queryResults.getInt("Address");

        //Get the Personal Details' Address from the Address ID
        Address address = super.databaseManager.addressesTable.findByID(addressID);

        //Setup the New Personal Details
        Personal_Details newPersonalDetails = new Personal_Details(
                personalDetailsID,
                name,
                phoneNumber,
                email,
                address
        );

        //return the New Personal Details
        return newPersonalDetails;
    }

    /**
     * Checks if a Personal Details Exists in the Database
     *
     * @param personalDetails The Personal Details to Check.
     * @return boolean Result of whether the Personal Details Exist.
     */
    protected boolean itemExists(Personal_Details personalDetails) {

        //Setup the Variable to check if the Personal Details Exists in the Database
        boolean personalDetailsExists = super.findByID(personalDetails.id) != null;

        //Return the Result
        return personalDetailsExists;
    }

    /**
     * Returns the List of Property Names of a Personal Details.
     *
     * @return An ArrayList of the Personal Detail's Property Names.
     */
    protected ArrayList<String> getProperties() {

        //Get the List of Properties
        ArrayList<Personal_Details_Properties> properties = Personal_Details.getProperties();

        //Convert the List of Properties to a String Array
        ArrayList<String> propertyStrings = new ArrayList<>(properties.stream().map(Enum::name).toList());

        //Return the Property String List
        return propertyStrings;
    }

    /**
     * Returns the List of Values of a Personal Details.
     *
     * @return An ArrayList of the Personal Detail's Values.
     */
    protected ArrayList<String> getValues(Personal_Details personalDetails) {

        //Get the List of Values for the Personal Details
        ArrayList<String> values = personalDetails.getValues();

        //Return the List of Values
        return values;
    }
}
