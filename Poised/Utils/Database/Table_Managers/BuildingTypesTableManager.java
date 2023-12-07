//Setup the Package
package Poised.Utils.Database.Table_Managers;

//Imports
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.BuildingType;
import Poised.Utils.Database.Tables.Properties.BuildingTypeProperties;
import Poised.Utils.Database.Utils.BaseTableManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A Class for Managing and Manipulation of the Buildings Table.
 */
public class BuildingTypesTableManager extends BaseTableManager<BuildingType> {

    /**
     * Constructor for BuildingsTableManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     */
    public BuildingTypesTableManager(DatabaseManager databaseManager) {

        //Construct the Parent Class
        super(databaseManager, "Building_Types");
    }

    /**
     * Builds/Formats the Building Type using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted Building Type.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected BuildingType buildFromQueryData(ResultSet queryResults) throws SQLException {

        //Get the Building Type's Data
        int buildingID = queryResults.getInt("id");
        String buildingTypeName = queryResults.getString("Building_Type");

        //Setup the New Building Type
        BuildingType newBuildingType = new BuildingType(buildingID, buildingTypeName);

        //return the New Building Type
        return newBuildingType;
    }

    /**
     * Checks if a Building Type Exists in the Database
     *
     * @param buildingType The Building Type to Check.
     * @return boolean Result of whether the Building Type Exists.
     */
    protected boolean itemExists(BuildingType buildingType) {

        //Setup the Variable to check if the Building Type Exists in the Database
        boolean buildingExists = super.findByID(buildingType.id) != null;

        //Return the Result
        return buildingExists;
    }

    /**
     * Returns the List of Property Names of a Building Type.
     *
     * @return An ArrayList of the Building Type's Property Names.
     */
    protected ArrayList<String> getProperties() {

        //Get the List of Properties
        ArrayList<BuildingTypeProperties> properties = BuildingType.getProperties();

        //Convert the List of Properties to a String Array
        ArrayList<String> propertyStrings = new ArrayList<>(properties.stream().map(Enum::name).toList());

        //Return the Property String List
        return propertyStrings;
    }

    /**
     * Returns the List of Values of a Building Type.
     *
     * @return An ArrayList of the Building Type's Values.
     */
    protected ArrayList<String> getValues(BuildingType buildingType) {

        //Get the List of Values for the Building Type
        ArrayList<String> values = buildingType.getValues();

        //Return the List of Values
        return values;
    }
}
