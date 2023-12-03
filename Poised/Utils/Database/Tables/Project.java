//Setup the Package
package Poised.Utils.Database.Tables;

//Imports
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Properties.Project_Properties;
import Poised.Utils.Dialogue.Components.Date_Input;
import Poised.Utils.Dialogue.Components.Form_Field;
import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The Project Represents the Projects Table in the Database
 */
public class Project {

    //Setup the Public Properties
    public String projectNumber;
    public String name;
    public Building building;
    public int erfNumber;
    public Address address;
    public double totalPrice;
    public double totalPaid;
    public Project_Manager projectManager;
    public Structural_Engineer structuralEngineer;
    public Architect architect;
    public Contractor contractor;
    public Customer customer;
    public Date deadline;
    public Date startDate;
    public Date completionDate;

    /**
     * Constructor for Project.
     *
     * @param projectNumber      The Project's Number (Acts as an Identifier).
     * @param name               The Name of the Project.
     * @param building           The Building Type of this Project.
     * @param erfNumber          The ERF Number used to ensure the Address is Correct.
     * @param address            The Address Where the Project is Located At.
     * @param totalPrice         The Total Price of the Project.
     * @param totalPaid          The Total Amount the Customer has Paid.
     * @param projectManager     The Project Manager associated with this Project.
     * @param structuralEngineer The Structural Engineer associated with this Project.
     * @param architect          The Architect associated with this Project.
     * @param contractor         The Contractor associated with this Project.
     * @param customer           The Customer associated with this Project.
     * @param deadline           The Date when the Project must be Completed By.
     * @param startDate          The Date work has Started on the Project (can be Null if Work was not started yet).
     * @param completionDate     The Date the Project has been Completed (can be Null if the Project is not Completed).
     */
    public Project(
            String projectNumber,
            String name,
            Building building,
            int erfNumber,
            Address address,
            double totalPrice,
            double totalPaid,
            Project_Manager projectManager,
            Structural_Engineer structuralEngineer,
            Architect architect,
            Contractor contractor,
            Customer customer,
            Date deadline,
            Date startDate,
            Date completionDate
    ){

        //Update the Public Properties
        this.projectNumber = projectNumber;
        this.name = name;
        this.building = building;
        this.erfNumber = erfNumber;
        this.address = address;
        this.totalPrice = totalPrice;
        this.totalPaid = totalPaid;
        this.projectManager = projectManager;
        this.structuralEngineer = structuralEngineer;
        this.architect = architect;
        this.contractor = contractor;
        this.customer = customer;
        this.deadline = deadline;
        this.startDate = startDate;
        this.completionDate = completionDate;
    }

    /**
     * Returns all the Data in the form of a String.
     *
     * @return The Formatted String Version of the Data
     */
    public String toString() {

        //Return the String
        return toString(true);
    }

    /**
     * Returns a Minimized or Full version of the String Version of the Data.
     *
     * @param minimal True for a Minimized Version, False for the Full Data.
     * @return The Formatted String Version of the Data.
     */
    public String toString(boolean minimal) {

        //Get the Property Names
        ArrayList<Project_Properties> propertyNames = getProperties();

        //Get the Property Values
        ArrayList<String> propertyValues = this.getFormattedValues();

        //Setup the Data String
        StringBuilder dataString = new StringBuilder();

        //Setup the List of Minimal Properties List
        ArrayList<Project_Properties> minimalProperties = new ArrayList<>();

        //Add the Minimal Properties to the Minimal Properties List
        minimalProperties.add(Project_Properties.Project_Number);
        minimalProperties.add(Project_Properties.Name);
        minimalProperties.add(Project_Properties.ERF_Number);


        //Loop through the Property Names
        for (int i = 0; i < propertyNames.size(); i++) {

            //Get the Current Property
            Project_Properties currentProperty = propertyNames.get(i);

            //Check if the Current Property is a non-minimal property and only minimal properties should be shown
            if(minimal && !minimalProperties.contains(currentProperty)) continue;

            //Get the Property Name
            String propertyName = currentProperty.name();

            //Get the Property Value
            String propertyValue = propertyValues.get(i);

            //Setup the Property Name with its Value
            String propertyNameAndValue = String.format("%s: %s", propertyName, propertyValue);

            //Append the Property Name and Value to the Data String
            dataString.append(propertyNameAndValue);

            //Check if there are more Properties and append a separator
            if(i < propertyNames.size() - 1) dataString.append(", \n");
        }

        //Return the Data String
        return dataString.toString();
    }

    /**
     * Returns a list of Properties for this Table.
     *
     * @return An ArrayList of Properties for this Table.
     */
    public static ArrayList<Project_Properties> getProperties() {

        //Setup the Properties String Array
        ArrayList<Project_Properties> properties = new ArrayList<>();

        //Add the Required Properties
        properties.addAll(Arrays.stream(Project_Properties.values()).toList());

        //Return the Properties
        return properties;
    }

    /**
     * Returns a list of Values for this Object.
     *
     * @return An ArrayList of Values for this Object.
     */
    public ArrayList<String> getValues() {

        //Setup the Values String Array
        ArrayList<String> values = new ArrayList<>();

        //Add the Required Values
        values.add(String.format("'%s'", this.projectNumber));
        values.add(String.format("'%s'", this.name));
        values.add(Integer.toString(this.building.id));
        values.add(Integer.toString(this.erfNumber));
        values.add(Integer.toString(this.address.id));
        values.add(Double.toString(this.totalPrice));
        values.add(Double.toString(this.totalPaid));
        values.add(Integer.toString(this.projectManager.id));
        values.add(Integer.toString(this.structuralEngineer.id));
        values.add(Integer.toString(this.architect.id));
        values.add(Integer.toString(this.contractor.id));
        values.add(Integer.toString(this.customer.id));
        values.add(String.format("'%s'", this.deadline));

        //Add the Optional Values
        values.add(String.format("'%s'", this.startDate));
        values.add(String.format("'%s'", this.completionDate));

        //Return the Values
        return values;
    }

    /**
     * Returns a list of Formatted Values for this Object.
     *
     * @return An ArrayList of Values for this Object.
     */
    public ArrayList<String> getFormattedValues() {

        //Setup the Values String Array
        ArrayList<String> values = new ArrayList<>();

        //Add the Required Values
        values.add(String.format("'%s'", this.projectNumber));
        values.add(String.format("'%s'", this.name));
        values.add(String.format("{%n%s%n}", this.building.toString()));
        values.add(Integer.toString(this.erfNumber));
        values.add(String.format("{%n%s%n}", this.address.toString()));
        values.add(Double.toString(this.totalPrice));
        values.add(Double.toString(this.totalPaid));
        values.add(String.format("{%n%s%n}", this.projectManager.toString()));
        values.add(String.format("{%n%s%n}", this.structuralEngineer.toString()));
        values.add(String.format("{%n%s%n}", this.architect.toString()));
        values.add(String.format("{%n%s%n}", this.contractor.toString()));
        values.add(String.format("{%n%s%n}", this.customer.toString()));
        values.add(String.format("'%s'", this.deadline));

        //Add the Optional Values
        values.add(String.format("'%s'", this.startDate));
        values.add(String.format("'%s'", this.completionDate));

        //Return the Values
        return values;
    }

     /**
     * Returns a Map of The Form Fields for this Object (The Form Fields have been Filled with This Object's Data).
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @return A Map Containing the Table's Properties as Keys and Filled Form Fields as the Corresponding Values.
     */
    public HashMap<Project_Properties, Form_Field> getFilledFormFields(Database_Manager databaseManager) {

        //Get the List of Form Fields
        HashMap<Project_Properties, Form_Field> formFields = getFormFields(databaseManager);

        //Get the Text Input Fields for the Properties
        JTextField projectNumberInputField =
                ((JTextField) formFields.get(Project_Properties.Project_Number).fieldInput());
        JTextField nameInputField = ((JTextField) formFields.get(Project_Properties.Name).fieldInput());
        JTextField erfNumberInputField = ((JTextField) formFields.get(Project_Properties.ERF_Number).fieldInput());
        JTextField totalPriceInputField = ((JTextField) formFields.get(Project_Properties.Total_Price).fieldInput());
        JTextField totalPaidInputField = ((JTextField) formFields.get(Project_Properties.Total_Paid).fieldInput());
        Date_Input deadlineInputField = ((Date_Input) formFields.get(Project_Properties.Deadline).fieldInput());
        Date_Input startDateInputField = ((Date_Input) formFields.get(Project_Properties.Start_Date).fieldInput());
        Date_Input completionDateInputField =
                ((Date_Input) formFields.get(Project_Properties.Completion_Date).fieldInput());

        //Get the Select Box Input Fields for the Properties
        JComboBox<Building> buildingTypeInputField =
                (JComboBox<Building>) formFields.get(Project_Properties.Building_Type).fieldInput();
        JComboBox<Project_Manager> projectManagersInputField =
                (JComboBox<Project_Manager>) formFields.get(Project_Properties.Project_Manager).fieldInput();
        JComboBox<Structural_Engineer> structuralEngineersInputField =
                (JComboBox<Structural_Engineer>) formFields.get(Project_Properties.Structural_Engineer).fieldInput();
        JComboBox<Architect> architectsInputField =
                (JComboBox<Architect>) formFields.get(Project_Properties.Architect).fieldInput();
        JComboBox<Contractor> contractorsInputField =
                (JComboBox<Contractor>) formFields.get(Project_Properties.Contractor).fieldInput();
        JComboBox<Customer> customersInputField =
                (JComboBox<Customer>) formFields.get(Project_Properties.Customer).fieldInput();

        //Update the Text Input Fields
        projectNumberInputField.setText(this.projectNumber);
        nameInputField.setText(this.name);
        erfNumberInputField.setText(Integer.toString(this.erfNumber));
        totalPriceInputField.setText(Double.toString(this.totalPrice));
        totalPaidInputField.setText(Double.toString(this.totalPaid));
        deadlineInputField.setSelectedDate(this.deadline);
        startDateInputField.setSelectedDate(this.startDate);
        completionDateInputField.setSelectedDate(this.completionDate);

        //Update the Select Box Input Fields
        buildingTypeInputField.setSelectedItem(this.building);
        projectManagersInputField.setSelectedItem(this.projectManager);
        structuralEngineersInputField.setSelectedItem(this.structuralEngineer);
        architectsInputField.setSelectedItem(this.architect);
        contractorsInputField.setSelectedItem(this.contractor);
        customersInputField.setSelectedItem(this.customer);

        //Return the Filled Form Fields
        return formFields;
    }

     /**
     * Returns a Map of The Form Fields for this Object.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @return A Map Containing the Table's Properties as Keys and Form Fields as the Corresponding Values.
     */
    public static HashMap<Project_Properties, Form_Field> getFormFields(Database_Manager databaseManager) {

        //Setup the List of Form Fields
        HashMap<Project_Properties, Form_Field> formFields = new HashMap<>();

        //Get the List of Properties
        ArrayList<Project_Properties> propertyList = getProperties();

        //Get the List of Required Select Boxes
        HashMap<Project_Properties, JComboBox<?>> selectBoxList = getSelectBoxList(databaseManager);

        //Setup the Date Properties
        ArrayList<Project_Properties> dateProperties = new ArrayList<>();
        dateProperties.add(Project_Properties.Deadline);
        dateProperties.add(Project_Properties.Start_Date);
        dateProperties.add(Project_Properties.Completion_Date);

        //Loop through the Requested Properties
        for (Project_Properties currentProperty : propertyList) {

            //Setup the Input Field's Label
            JLabel inputFieldLabel = new JLabel(currentProperty.name());

            //Setup the New Input Field
            JComponent inputField = new JTextField(10);

            //Check if the Current Property has a Select Box attached to it and update the Input Field
            if(selectBoxList.containsKey(currentProperty)) inputField = selectBoxList.get(currentProperty);

            //Check if the Current Property Should be a Date Input Field and update the Input Field
            if(dateProperties.contains(currentProperty))inputField = new Date_Input();

            //Setup the New Form Field
            Form_Field newFormField = new Form_Field(inputFieldLabel, inputField);

            //Add the New Form Field to the Form Fields List
            formFields.put(currentProperty, newFormField);
        }

        //Return the Form Fields
        return formFields;
    }

    /**
     * Returns a Map of The Select Boxes for this Object's Properties.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @return A Map Containing the Table's Properties as Keys and Select Boxes as the Corresponding Values.
     */
    public static HashMap<Project_Properties, JComboBox<?>> getSelectBoxList(Database_Manager databaseManager) {

        //Setup the Select Box List
        HashMap<Project_Properties, JComboBox<?>> selectBoxList = new HashMap<>();

//Get the List of Addresses
        Address[] addressList =
                databaseManager.addressesTable.getAll().toArray(Address[]::new);

        //Get the List of Buildings
        Building[] buildingList =
                databaseManager.buildingsTable.getAll().toArray(Building[]::new);

        //Get the List of Project Managers
        Project_Manager[] projectManagerList =
                databaseManager.projectManagersTable.getAll().toArray(Project_Manager[]::new);

        //Get the List of Structural Engineers
        Structural_Engineer[] structuralEngineerList =
                databaseManager.structuralEngineersTable.getAll().toArray(Structural_Engineer[]::new);

        //Get the List of Customers
        Customer[] customerList =
                databaseManager.customersTable.getAll().toArray(Customer[]::new);

        //Get the List of Contractors
        Contractor[] contractorList =
                databaseManager.contractorsTable.getAll().toArray(Contractor[]::new);

        //Get the List of Architects
        Architect[] architectList =
                databaseManager.architectsTable.getAll().toArray(Architect[]::new);

        //Setup the Required Select Boxes Select Box
        JComboBox<Address> addressSelectBox = new JComboBox<>(addressList);
        JComboBox<Building> buildingSelectBox = new JComboBox<>(buildingList);
        JComboBox<Project_Manager> projectManagerSelectBox = new JComboBox<>(projectManagerList);
        JComboBox<Structural_Engineer> structuralEngineerSelectBox = new JComboBox<>(structuralEngineerList);
        JComboBox<Customer> customerSelectBox = new JComboBox<>(customerList);
        JComboBox<Contractor> contractorSelectBox = new JComboBox<>(contractorList);
        JComboBox<Architect> architectSelectBox = new JComboBox<>(architectList);

        //Add the Items to the Required Items List
        selectBoxList.put(Project_Properties.Address, addressSelectBox);
        selectBoxList.put(Project_Properties.Building_Type, buildingSelectBox);
        selectBoxList.put(Project_Properties.Project_Manager, projectManagerSelectBox);
        selectBoxList.put(Project_Properties.Structural_Engineer, structuralEngineerSelectBox);
        selectBoxList.put(Project_Properties.Architect, architectSelectBox);
        selectBoxList.put(Project_Properties.Contractor, contractorSelectBox);
        selectBoxList.put(Project_Properties.Customer, customerSelectBox);

        //Return the Required Items List
        return selectBoxList;
    }
}

