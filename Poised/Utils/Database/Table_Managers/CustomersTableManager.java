//Setup the Package
package Poised.Utils.Database.Table_Managers;

//Imports
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.Customer;
import Poised.Utils.Database.Tables.PersonalDetails;
import Poised.Utils.Database.Tables.Properties.RoleProperties;
import Poised.Utils.Database.Utils.BaseTableManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A Class for Managing and Manipulation of the Customers Table.
 */
public class CustomersTableManager extends BaseTableManager<Customer> {

    /**
     * Constructor for CustomersTableManager.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     */
    public CustomersTableManager(DatabaseManager databaseManager) {

        //Construct the Parent Class
        super(databaseManager, "Customers");
    }

    /**
     * Builds/Formats the Customer using the Raw Data from the Database.
     *
     * @param queryResults The ResultSet Containing the Query Data.
     * @return The Built/Formatted customer.
     * @throws SQLException If an Error Occurred within the SQL query Results.
     */
    protected Customer buildFromQueryData(ResultSet queryResults) throws SQLException {

        //Get the Customer's Data
        int customerID = queryResults.getInt("id");
        int personalDetailsID = queryResults.getInt("Personal_Details");

        //Get the Customer's Personal Details from the Personal Details ID
        PersonalDetails personalDetails = this.databaseManager.personalDetailsTable.findByID(personalDetailsID);

        //Setup the New Customer
        Customer newCustomer = new Customer(customerID, personalDetails);

        //return the New Customer
        return newCustomer;
    }

    /**
     * Checks if a Customer Exists in the Database
     *
     * @param customer The Customer to Check.
     * @return boolean Result of whether the Customer Exists.
     */
    protected boolean itemExists(Customer customer) {

        //Setup the Variable to check if the Customer Exists in the Database
        boolean customerExists = super.findByID(customer.id) != null;

        //Return the Result
        return customerExists;
    }

    /**
     * Returns the List of Property Names of a Customer.
     *
     * @return An ArrayList of the Customer's Property Names.
     */
    protected ArrayList<String> getProperties() {

        //Get the List of Properties
        ArrayList<RoleProperties> properties = Customer.getProperties();

        //Convert the List of Properties to a String Array
        ArrayList<String> propertyStrings = new ArrayList<>(properties.stream().map(Enum::name).toList());

        //Return the Property String List
        return propertyStrings;
    }

    /**
     * Returns the List of Values of a Customer.
     *
     * @return An ArrayList of the Customer's Values.
     */
    protected ArrayList<String> getValues(Customer customer) {

        //Get the List of Values for the Customer
        ArrayList<String> values = customer.getValues();

        //Return the List of Values
        return values;
    }
}
