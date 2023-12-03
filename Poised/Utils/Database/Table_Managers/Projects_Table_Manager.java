//Setup the Package
package Poised.Utils.Database.Table_Managers;

//Imports
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.*;
import Poised.Utils.Database.Tables.Properties.Project_Properties;
import Poised.Utils.Database.Utils.Base_Table_Manager;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A Class for Managing and Manipulation of the Projects Table.
 */
public class Projects_Table_Manager extends Base_Table_Manager<Project> {

    /**
     * Constructor for Projects_Table_Manager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     */
    public Projects_Table_Manager(Database_Manager databaseManager) {

        //Construct the Parent Class
        super(databaseManager, "Projects");
    }

    /**
     * Gets a List of All the Projects with the Specified Parameters.
     *
     * @param overdue         Flag indicating if Only the Overdue projects Should be Shown.
     * @param inProgress      Flag indicating if Only the In-Progress projects Should be Shown.
     * @param completed       Flag indicating if Only the Completed projects Should be Shown.
     *
     * @return An ArrayList Containing the Projects that Match the Specified Parameters
     */

    public ArrayList<Project> getAll(boolean overdue, boolean inProgress, boolean completed) {

        //Setup the Project List
        ArrayList<Project> projectList = new ArrayList<>();

        //Setup the List of Projects in the Database
        ResultSet projectsInDatabase;

        //Setup the Current Date
        LocalDate currentDate = LocalDate.now();

        //Setup the Query String
        String queryString = "SELECT * FROM Projects";

        //Check if the User wants all the Overdue Projects
        if(overdue) queryString += String.format(" WHERE DATEDIFF(Deadline, '%s') < 0", currentDate);

        //Check if the User wants all the In Progress Projects
        if(inProgress) queryString += " WHERE Completion_Date IS null";

        //Check if the User wants all the Completed Projects
        if(completed) queryString += " WHERE Completion_Date";

        //Try to Get the List of Projects from the Database
        try {

            //Get the List of Projects from the Database
            projectsInDatabase = this.queryManager.executeQuery(queryString);

            //Loop through the List of Projects in the Database
            while(projectsInDatabase.next()) {

                //Setup the New Project
                Project newProject = buildFromQueryData(projectsInDatabase);

                //Add the New Project to the Projects List
                projectList.add(newProject);
            }
        } catch (SQLException exception) {

            //Log the Exception to the Console
            System.out.printf("%nSQL ERROR:%n%s%n", exception);
        }

        //Return the Project List
        return projectList;
    }

    /**
     * Finds a Project using the Project Number.
     *
     * @param projectNumber The Project's Number to Find.
     *
     * @return The Found Project or Null if no Project Could be Found
     */
    public Project findByProjectNumber(String projectNumber) {

        //Setup the Requested Project
        Project requestedProject = null;

        //Setup the List of Projects in the Database
        ResultSet projectsInDatabase;

        //Setup the Query String
        String queryString =
                String.format("SELECT * FROM Projects WHERE Project_Number = '%s' LIMIT 1", projectNumber);

        //Try to Get the List of Projects from the Database
        try {

            //Get the List of Projects from the Database
            projectsInDatabase = this.queryManager.executeQuery(queryString);

            //Check if the Project was Found and Set the Requested Project to the Found Project
            if(projectsInDatabase.next()) requestedProject = buildFromQueryData(projectsInDatabase);
        } catch (SQLException exception) {

            //Log the Exception to the Console
            System.out.printf("%nSQL ERROR:%n%s%n", exception);
        }

        //Return the Requested Project
        return requestedProject;
    }

    /**
     * Builds/Formats the Project using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted project.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected Project buildFromQueryData(ResultSet queryResults) throws SQLException {

        //Get the Project Data
        String projectNumber = queryResults.getString("Project_Number");
        String projectName = queryResults.getString("Name");
        int buildingID = queryResults.getInt("Building_Type");
        int erfNumber = queryResults.getInt("ERF_Number");
        int addressID = queryResults.getInt("Address");
        double totalPrice = queryResults.getDouble("Total_Price");
        double totalPaid = queryResults.getDouble("Total_Paid");
        int projectManagerID = queryResults.getInt("Project_Manager");
        int structuralEngineerID = queryResults.getInt("Structural_Engineer");
        int architectID = queryResults.getInt("Architect");
        int contractorID = queryResults.getInt("Contractor");
        int customerID = queryResults.getInt("Customer");
        Date deadline = queryResults.getDate("Deadline");
        Date startDate = queryResults.getDate("Start_Date");
        Date completionDate = queryResults.getDate("Completion_Date");

        //Get the Project's Building from the Building ID
        Building building = super.databaseManager.buildingsTable.findByID(buildingID);

        //Get the Project's Address from the Address ID
        Address address = super.databaseManager.addressesTable.findByID(addressID);

        //Get the Project's Project Manager from the Project Manager's ID
        Project_Manager projectManager = this.databaseManager.projectManagersTable.findByID(projectManagerID);

        //Get the Project's Structural Engineer from the Structural Engineer's ID
        Structural_Engineer structuralEngineer =
                this.databaseManager.structuralEngineersTable.findByID(structuralEngineerID);

        //Get the Project's Architect from the Architect's ID
        Architect architect = this.databaseManager.architectsTable.findByID(architectID);

        //Get the Project's Contractor from the Contractor's ID
        Contractor contractor = this.databaseManager.contractorsTable.findByID(contractorID);

        //Get the Project's Customer from the Customer's ID
        Customer customer = this.databaseManager.customersTable.findByID(customerID);

        //Setup the New Project
        Project newProject = new Project(
                projectNumber,
                projectName,
                building,
                erfNumber,
                address,
                totalPrice,
                totalPaid,
                projectManager,
                structuralEngineer,
                architect,
                contractor,
                customer,
                deadline,
                startDate,
                completionDate
        );

        //Return the New Project
        return newProject;
    }

    /**
     * Checks if a Project Exists in the Database
     *
     * @param project The Project to Check.
     * @return boolean Result of whether the Project Exists.
     */
    protected boolean itemExists(Project project) {

        //Setup the Variable to check if the Project Exists in the Database
        boolean projectExists = findByProjectNumber(project.projectNumber) != null;

        //Return the Result
        return projectExists;
    }

    /**
     * Returns the List of Property Names of a Project.
     *
     * @return An ArrayList of the Project's Property Names.
     */
    protected ArrayList<String> getProperties() {

        //Get the List of Properties
        ArrayList<Project_Properties> properties = Project.getProperties();

        //Convert the List of Properties to a String Array
        ArrayList<String> propertyStrings = new ArrayList<>(properties.stream().map(Enum::name).toList());

        //Return the Property String List
        return propertyStrings;
    }

    /**
     * Returns the List of Values of a Project.
     *
     * @return An ArrayList of the Project's Values.
     */
    protected ArrayList<String> getValues(Project project) {

        //Get the List of Values for the Project
        ArrayList<String> values = project.getValues();

        //Return the List of Values
        return values;
    }
}
