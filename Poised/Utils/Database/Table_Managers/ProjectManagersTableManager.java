//Setup the Package
package Poised.Utils.Database.Table_Managers;

//Imports
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.ProjectManager;
import Poised.Utils.Database.Tables.PersonalDetails;
import Poised.Utils.Database.Tables.Properties.RoleProperties;
import Poised.Utils.Database.Utils.BaseTableManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A Class for Managing and Manipulation of the Project Managers Table.
 */
public class ProjectManagersTableManager extends BaseTableManager<ProjectManager> {

    /**
     * Constructor for ProjectManagersTableManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     */
    public ProjectManagersTableManager(DatabaseManager databaseManager) {

        //Construct the Parent Class
        super(databaseManager, "Project_Managers");
    }

    /**
     * Builds/Formats the Project Manager using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted Project Manager.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected ProjectManager buildFromQueryData(ResultSet queryResults) throws SQLException {

        //Get the Project Manager's Data
        int projectManagerID = queryResults.getInt("id");
        int personalDetailsID = queryResults.getInt("Personal_Details");

        //Get the Project Manager's Personal Details from the Personal Details ID
        PersonalDetails personalDetails = this.databaseManager.personalDetailsTable.findByID(personalDetailsID);

        //Setup the New Project Manager
        ProjectManager newProjectManager = new ProjectManager(projectManagerID, personalDetails);

        //return the New Project Manager
        return newProjectManager;
    }

    /**
     * Checks if a Project Manager Exists in the Database
     *
     * @param projectManager The Project Manager to Check.
     * @return boolean Result of whether the Project Manager Exists.
     */
    protected boolean itemExists(ProjectManager projectManager) {

        //Setup the Variable to check if the Project Manager Exists in the Database
        boolean projectManagerExists = super.findByID(projectManager.id) != null;

        //Return the Result
        return projectManagerExists;
    }

    /**
     * Returns the List of Property Names of a Project Manager.
     *
     * @return An ArrayList of the Project Manager's Property Names.
     */
    protected ArrayList<String> getProperties() {

        //Get the List of Properties
        ArrayList<RoleProperties> properties = ProjectManager.getProperties();

        //Convert the List of Properties to a String Array
        ArrayList<String> propertyStrings = new ArrayList<>(properties.stream().map(Enum::name).toList());

        //Return the Property String List
        return propertyStrings;
    }

    /**
     * Returns the List of Values of a Project Manager.
     *
     * @return An ArrayList of the Project Manager's Values.
     */
    protected ArrayList<String> getValues(ProjectManager projectManager) {

        //Get the List of Values for the Project Manager
        ArrayList<String> values = projectManager.getValues();

        //Return the List of Values
        return values;
    }
}
