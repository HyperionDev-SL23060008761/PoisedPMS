//Setup the Package
package Poised.Utils.Database;

//Imports
import Poised.Utils.Database.Table_Managers.*;

import java.sql.*;

/**
 * The Database Manager Manages the Manipulation of Data in the Database and Provides Access to the Different Tables
 */
public class Database_Manager {

	//Setup the Public Properties
	public Connection connection;

	//Setup the Different Tables as Public Properties
	public Addresses_Table_Manager addressesTable;
	public Architects_Table_Manager architectsTable;
	public Buildings_Table_Manager buildingsTable;
	public Contractors_Table_Manager contractorsTable;
	public Customers_Table_Manager customersTable;
	public Personal_Details_Table_Manager personalDetailsTable;
	public Projects_Table_Manager projectsTable;
	public Project_Managers_Table_Manager projectManagersTable;
	public Structural_Engineers_Table_Manager structuralEngineersTable;

	//Setup the Private Properties
	private final String databaseHost;
	private final String databaseName;
	private final String databaseUser;
	private final String databasePassword;

	/**
	 * Constructor for Database_Manager.
	 *
	 * @param databaseHost     The Host Address for the Database.
	 * @param databaseName     The Name of the Database.
	 * @param databaseUser     The Username for Connecting to the Database.
	 * @param databasePassword The Password for Connecting to the Database.
	 */
	public Database_Manager(String databaseHost, String databaseName, String databaseUser, String databasePassword) {

		//Update the Private Properties
		this.databaseHost = databaseHost;
		this.databaseName = databaseName;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
	}

	/**
	 * Connects to the Database.
	 *
	 * @throws SQLException If a Connection to the Database can not be Established.
	 */
	public void connect() throws SQLException {

		//Setup the Database Connection URL
		String databaseConnectionURL = String.format(
				"jdbc:mysql://%s/%s?allowPublicKeyRetrieval=true&useSSL=false",
				this.databaseHost,
				this.databaseName
		);

		//Update the MySQL Connection
		Connection connection = DriverManager.getConnection(databaseConnectionURL, this.databaseUser, this.databasePassword);

		//Update the Connection
		this.connection = connection;

		//Setup the Tables
		setupDatabaseTables();
	}

	/**
	 * Sets up the Tables that will be Available in this Database
	 */
	private void setupDatabaseTables() {

		//Update the Public Tables
		this.addressesTable = new Addresses_Table_Manager(this);
		this.architectsTable = new Architects_Table_Manager(this);
		this.buildingsTable = new Buildings_Table_Manager(this);
		this.contractorsTable = new Contractors_Table_Manager(this);
		this.customersTable = new Customers_Table_Manager(this);
		this.personalDetailsTable = new Personal_Details_Table_Manager(this);
		this.projectsTable = new Projects_Table_Manager(this);
		this.projectManagersTable = new Project_Managers_Table_Manager(this);
		this.structuralEngineersTable = new Structural_Engineers_Table_Manager(this);
	}
}