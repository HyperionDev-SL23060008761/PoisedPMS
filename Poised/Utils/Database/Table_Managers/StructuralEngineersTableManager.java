//Setup the Package
package Poised.Utils.Database.Table_Managers;

//Imports
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.StructuralEngineer;
import Poised.Utils.Database.Tables.PersonalDetails;
import Poised.Utils.Database.Tables.Properties.RoleProperties;
import Poised.Utils.Database.Utils.BaseTableManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A Class for Managing and Manipulation of the Structural Engineers Table.
 */
public class StructuralEngineersTableManager extends BaseTableManager<StructuralEngineer> {

    /**
     * Constructor for StructuralEngineersTableManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     */
    public StructuralEngineersTableManager(DatabaseManager databaseManager) {

        //Construct the Parent Class
        super(databaseManager, "Structural_Engineers");
    }

    /**
     * Builds/Formats the Structural Engineer using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted Structural Engineer.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected StructuralEngineer buildFromQueryData(ResultSet queryResults) throws SQLException {

        //Get the Structural Engineer's Data
        int structuralEngineerID = queryResults.getInt("id");
        int personalDetailsID = queryResults.getInt("Personal_Details");

        //Get the Structural Engineer's Personal Details from the Personal Details ID
        PersonalDetails personalDetails = this.databaseManager.personalDetailsTable.findByID(personalDetailsID);

        //Setup the New Structural Engineer
        StructuralEngineer newStructuralEngineer = new StructuralEngineer(structuralEngineerID, personalDetails);

        //return the New Structural Engineer
        return newStructuralEngineer;
    }

    /**
     * Checks if a Structural Engineer Exists in the Database
     *
     * @param structuralEngineer The Structural Engineer to Check.
     * @return boolean Result of whether the Structural Engineer Exists.
     */
    protected boolean itemExists(StructuralEngineer structuralEngineer) {

        //Setup the Variable to check if the Structural Engineer Exists in the Database
        boolean structuralEngineerExists = super.findByID(structuralEngineer.id) != null;

        //Return the Result
        return structuralEngineerExists;
    }

    /**
     * Returns the List of Property Names of a Structural Engineer.
     *
     * @return An ArrayList of the Structural Engineer's Property Names.
     */
    protected ArrayList<String> getProperties() {

        //Get the List of Properties
        ArrayList<RoleProperties> properties = StructuralEngineer.getProperties();

        //Convert the List of Properties to a String Array
        ArrayList<String> propertyStrings = new ArrayList<>(properties.stream().map(Enum::name).toList());

        //Return the Property String List
        return propertyStrings;
    }

    /**
     * Returns the List of Values of a Structural Engineer.
     *
     * @return An ArrayList of the Structural Engineer's Values.
     */
    protected ArrayList<String> getValues(StructuralEngineer structuralEngineer) {

        //Get the List of Values for the Structural Engineer
        ArrayList<String> values = structuralEngineer.getValues();

        //Return the List of Values
        return values;
    }
}
