//Setup the Package
package Poised.Utils.Database.Table_Managers;

//Imports
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.Architect;
import Poised.Utils.Database.Tables.PersonalDetails;
import Poised.Utils.Database.Tables.Properties.RoleProperties;
import Poised.Utils.Database.Utils.BaseTableManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A Class for Managing and Manipulation of the Architects Table.
 */
public class ArchitectsTableManager extends BaseTableManager<Architect> {

    /**
     * Constructor for ArchitectsTableManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     */
    public ArchitectsTableManager(DatabaseManager databaseManager) {

        //Construct the Parent Class
        super(databaseManager, "Architects");
    }

    /**
     * Builds/Formats the Architect using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted architect.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected Architect buildFromQueryData(ResultSet queryResults) throws SQLException {

        //Get the Architect's Data
        int architectID = queryResults.getInt("id");
        int personalDetailsID = queryResults.getInt("Personal_Details");

        //Get the Architect's Personal Details from the Personal Details ID
        PersonalDetails personalDetails = super.databaseManager.personalDetailsTable.findByID(personalDetailsID);

        //Setup the New Architect
        Architect newArchitect = new Architect(architectID, personalDetails);

        //return the New Architect
        return newArchitect;
    }

    /**
     * Checks if an Architect Exists in the Database
     *
     * @param architect The Architect to Check.
     * @return boolean Result of whether the Architect Exists.
     */
    protected boolean itemExists(Architect architect) {

        //Setup the Variable to check if the Architect Exists in the Database
        boolean architectExists = super.findByID(architect.id) != null;

        //Return the Result
        return architectExists;
    }

    /**
     * Returns the List of Property Names of an Architect.
     *
     * @return An ArrayList of the Architect's Property Names.
     */
    protected ArrayList<String> getProperties() {

        //Get the List of Properties
        ArrayList<RoleProperties> properties = Architect.getProperties();

        //Convert the List of Properties to a String Array
        ArrayList<String> propertyStrings = new ArrayList<>(properties.stream().map(Enum::name).toList());

        //Return the Property String List
        return propertyStrings;
    }

    /**
     * Returns the List of Values of an Architect.
     *
     * @return An ArrayList of the Architect's Values.
     */
    protected ArrayList<String> getValues(Architect architect) {

        //Get the List of Values for the Architect
        ArrayList<String> values = architect.getValues();

        //Return the List of Values
        return values;
    }
}
