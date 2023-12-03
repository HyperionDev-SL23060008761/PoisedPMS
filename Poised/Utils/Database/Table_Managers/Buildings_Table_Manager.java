//Setup the Package
package Poised.Utils.Database.Table_Managers;

//Imports
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Building;
import Poised.Utils.Database.Tables.Properties.Building_Properties;
import Poised.Utils.Database.Utils.Base_Table_Manager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A Class for Managing and Manipulation of the Buildings Table.
 */
public class Buildings_Table_Manager extends Base_Table_Manager<Building> {

    /**
     * Constructor for Buildings_Table_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     */
    public Buildings_Table_Manager(Database_Manager databaseManager) {

        //Construct the Parent Class
        super(databaseManager, "Buildings");
    }

    /**
     * Builds/Formats the Building using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted building.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected Building buildFromQueryData(ResultSet queryResults) throws SQLException {

        //Get the Building's Data
        int buildingID = queryResults.getInt("id");
        String buildingType = queryResults.getString("Building_Type");

        //Setup the New Building
        Building newBuilding = new Building(buildingID, buildingType);

        //return the New Building
        return newBuilding;
    }

    /**
     * Checks if a Building Exists in the Database
     *
     * @param building The Building to Check.
     * @return boolean Result of whether the Building Exists.
     */
    protected boolean itemExists(Building building) {

        //Setup the Variable to check if the Building Exists in the Database
        boolean buildingExists = super.findByID(building.id) != null;

        //Return the Result
        return buildingExists;
    }

    /**
     * Returns the List of Property Names of a Building.
     *
     * @return An ArrayList of the Building's Property Names.
     */
    protected ArrayList<String> getProperties() {

        //Get the List of Properties
        ArrayList<Building_Properties> properties = Building.getProperties();

        //Convert the List of Properties to a String Array
        ArrayList<String> propertyStrings = new ArrayList<>(properties.stream().map(Enum::name).toList());

        //Return the Property String List
        return propertyStrings;
    }

    /**
     * Returns the List of Values of a Building.
     *
     * @return An ArrayList of the Building's Values.
     */
    protected ArrayList<String> getValues(Building building) {

        //Get the List of Values for the Building
        ArrayList<String> values = building.getValues();

        //Return the List of Values
        return values;
    }
}
