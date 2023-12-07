//Setup the Package
package Poised.Utils.Database;

//Imports
import Poised.Utils.Database.Table_Managers.*;

import java.sql.*;

/**
 * The Database Manager Manages the Manipulation of Data in the Database and Provides Access to the Different Tables
 */
public class DatabaseManager {

	//Setup the Public Properties
	public Connection connection;

	//Setup the Different Tables as Public Properties
	public AddressesTableManager addressesTable;
	public ArchitectsTableManager architectsTable;
	public BuildingTypesTableManager buildingsTable;
	public ContractorsTableManager contractorsTable;
	public CustomersTableManager customersTable;
	public PersonalDetailsTableManager personalDetailsTable;
	public ProjectsTableManager projectsTable;
	public ProjectManagersTableManager projectManagersTable;
	public StructuralEngineersTableManager structuralEngineersTable;

	//Setup the Private Properties
	private final String databaseHost;
	private final String databaseName;
	private final String databaseUser;
	private final String databasePassword;

	/**
	 * Constructor for DatabaseManager.
	 *
	 * @param databaseHost     The Host Address for the Database.
	 * @param databaseName     The Name of the Database.
	 * @param databaseUser     The Username for Connecting to the Database.
	 * @param databasePassword The Password for Connecting to the Database.
	 */
	public DatabaseManager(String databaseHost, String databaseName, String databaseUser, String databasePassword) {

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
		this.addressesTable = new AddressesTableManager(this);
		this.architectsTable = new ArchitectsTableManager(this);
		this.buildingsTable = new BuildingTypesTableManager(this);
		this.contractorsTable = new ContractorsTableManager(this);
		this.customersTable = new CustomersTableManager(this);
		this.personalDetailsTable = new PersonalDetailsTableManager(this);
		this.projectsTable = new ProjectsTableManager(this);
		this.projectManagersTable = new ProjectManagersTableManager(this);
		this.structuralEngineersTable = new StructuralEngineersTableManager(this);
	}
}