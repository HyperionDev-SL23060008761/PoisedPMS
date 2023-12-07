//Setup the Package
package Poised.Utils.Database.Table_Managers;

//Imports
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.Contractor;
import Poised.Utils.Database.Tables.PersonalDetails;
import Poised.Utils.Database.Tables.Properties.RoleProperties;
import Poised.Utils.Database.Utils.BaseTableManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A Class for Managing and Manipulation of the Contractors Table.
 */
public class ContractorsTableManager extends BaseTableManager<Contractor> {

    /**
     * Constructor for ContractorsTableManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     */
    public ContractorsTableManager(DatabaseManager databaseManager) {

        //Construct the Parent Class
        super(databaseManager, "Contractors");
    }

    /**
     * Builds/Formats the Contractor using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted contractor.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected Contractor buildFromQueryData(ResultSet queryResults) throws SQLException {

        //Get the Contractor's Data
        int contractorID = queryResults.getInt("id");
        int personalDetailsID = queryResults.getInt("Personal_Details");

        //Get the Contractor's Personal Details from the Personal Details ID
        PersonalDetails personalDetails = this.databaseManager.personalDetailsTable.findByID(personalDetailsID);

        //Setup the New Contractor
        Contractor newContractor = new Contractor(contractorID, personalDetails);

        //return the New Contractor
        return newContractor;
    }

    /**
     * Checks if a Contractor Exists in the Database
     *
     * @param contractor The Contractor to Check.
     * @return boolean Result of whether the Contractor Exists.
     */
    protected boolean itemExists(Contractor contractor) {

        //Setup the Variable to check if the Contractor Exists in the Database
        boolean contractorExists = super.findByID(contractor.id) != null;

        //Return the Result
        return contractorExists;
    }

    /**
     * Returns the List of Property Names of a Contractor.
     *
     * @return An ArrayList of the Contractor's Property Names.
     */
    protected ArrayList<String> getProperties() {

        //Get the List of Properties
        ArrayList<RoleProperties> properties = Contractor.getProperties();

        //Convert the List of Properties to a String Array
        ArrayList<String> propertyStrings = new ArrayList<>(properties.stream().map(Enum::name).toList());

        //Return the Property String List
        return propertyStrings;
    }

    /**
     * Returns the List of Values of a Contractor.
     *
     * @return An ArrayList of the Contractor's Values.
     */
    protected ArrayList<String> getValues(Contractor contractor) {

        //Get the List of Values for the Contractor
        ArrayList<String> values = contractor.getValues();

        //Return the List of Values
        return values;
    }
}
