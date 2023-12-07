//Setup the Package
package Poised.Utils.Database.Tables;

//Imports
import Poised.Utils.Database.DatabaseManager;
import Poised.Utils.Database.Tables.Properties.ProjectProperties;
import Poised.Utils.Dialogue.Components.DateInput;
import Poised.Utils.Dialogue.Components.FormField;
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
    public BuildingType buildingType;
    public int erfNumber;
    public Address address;
    public double totalPrice;
    public double totalPaid;
    public ProjectManager projectManager;
    public StructuralEngineer structuralEngineer;
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
     * @param buildingType       The Building Type of this Project.
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
            BuildingType buildingType,
            int erfNumber,
            Address address,
            double totalPrice,
            double totalPaid,
            ProjectManager projectManager,
            StructuralEngineer structuralEngineer,
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
        this.buildingType = buildingType;
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
        ArrayList<ProjectProperties> propertyNames = getProperties();

        //Get the Property Values
        ArrayList<String> propertyValues = this.getFormattedValues();

        //Setup the Data String
        StringBuilder dataString = new StringBuilder();

        //Setup the List of Minimal Properties List
        ArrayList<ProjectProperties> minimalProperties = new ArrayList<>();

        //Add the Minimal Properties to the Minimal Properties List
        minimalProperties.add(ProjectProperties.Project_Number);
        minimalProperties.add(ProjectProperties.Name);
        minimalProperties.add(ProjectProperties.ERF_Number);


        //Loop through the Property Names
        for (int i = 0; i < propertyNames.size(); i++) {

            //Get the Current Property
            ProjectProperties currentProperty = propertyNames.get(i);

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
    public static ArrayList<ProjectProperties> getProperties() {

        //Setup the Properties String Array
        ArrayList<ProjectProperties> properties = new ArrayList<>();

        //Add the Required Properties
        properties.addAll(Arrays.stream(ProjectProperties.values()).toList());

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
        values.add(Integer.toString(this.buildingType.id));
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
        values.add(String.format("{%n%s%n}", this.buildingType.toString()));
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
    public HashMap<ProjectProperties, FormField> getFilledFormFields(DatabaseManager databaseManager) {

        //Get the List of Form Fields
        HashMap<ProjectProperties, FormField> formFields = getFormFields(databaseManager);

        //Get the Text Input Fields for the Properties
        JTextField projectNumberInputField =
                ((JTextField) formFields.get(ProjectProperties.Project_Number).fieldInput());
        JTextField nameInputField = ((JTextField) formFields.get(ProjectProperties.Name).fieldInput());
        JTextField erfNumberInputField = ((JTextField) formFields.get(ProjectProperties.ERF_Number).fieldInput());
        JTextField totalPriceInputField = ((JTextField) formFields.get(ProjectProperties.Total_Price).fieldInput());
        JTextField totalPaidInputField = ((JTextField) formFields.get(ProjectProperties.Total_Paid).fieldInput());
        DateInput deadlineInputField = ((DateInput) formFields.get(ProjectProperties.Deadline).fieldInput());
        DateInput startDateInputField = ((DateInput) formFields.get(ProjectProperties.Start_Date).fieldInput());
        DateInput completionDateInputField =
                ((DateInput) formFields.get(ProjectProperties.Completion_Date).fieldInput());

        //Get the Select Box Input Fields for the Properties
        JComboBox<BuildingType> buildingTypeInputField =
                (JComboBox<BuildingType>) formFields.get(ProjectProperties.Building_Type).fieldInput();
        JComboBox<ProjectManager> projectManagersInputField =
                (JComboBox<ProjectManager>) formFields.get(ProjectProperties.Project_Manager).fieldInput();
        JComboBox<StructuralEngineer> structuralEngineersInputField =
                (JComboBox<StructuralEngineer>) formFields.get(ProjectProperties.Structural_Engineer).fieldInput();
        JComboBox<Architect> architectsInputField =
                (JComboBox<Architect>) formFields.get(ProjectProperties.Architect).fieldInput();
        JComboBox<Contractor> contractorsInputField =
                (JComboBox<Contractor>) formFields.get(ProjectProperties.Contractor).fieldInput();
        JComboBox<Customer> customersInputField =
                (JComboBox<Customer>) formFields.get(ProjectProperties.Customer).fieldInput();

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
        buildingTypeInputField.setSelectedItem(this.buildingType);
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
    public static HashMap<ProjectProperties, FormField> getFormFields(DatabaseManager databaseManager) {

        //Setup the List of Form Fields
        HashMap<ProjectProperties, FormField> formFields = new HashMap<>();

        //Get the List of Properties
        ArrayList<ProjectProperties> propertyList = getProperties();

        //Get the List of Required Select Boxes
        HashMap<ProjectProperties, JComboBox<?>> selectBoxList = getSelectBoxList(databaseManager);

        //Setup the Date Properties
        ArrayList<ProjectProperties> dateProperties = new ArrayList<>();
        dateProperties.add(ProjectProperties.Deadline);
        dateProperties.add(ProjectProperties.Start_Date);
        dateProperties.add(ProjectProperties.Completion_Date);

        //Loop through the Requested Properties
        for (ProjectProperties currentProperty : propertyList) {

            //Setup the Input Field's Label
            JLabel inputFieldLabel = new JLabel(currentProperty.name());

            //Setup the New Input Field
            JComponent inputField = new JTextField(10);

            //Check if the Current Property has a Select Box attached to it and update the Input Field
            if(selectBoxList.containsKey(currentProperty)) inputField = selectBoxList.get(currentProperty);

            //Check if the Current Property Should be a Date Input Field and update the Input Field
            if(dateProperties.contains(currentProperty))inputField = new DateInput();

            //Setup the New Form Field
            FormField newFormField = new FormField(inputFieldLabel, inputField);

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
    public static HashMap<ProjectProperties, JComboBox<?>> getSelectBoxList(DatabaseManager databaseManager) {

        //Setup the Select Box List
        HashMap<ProjectProperties, JComboBox<?>> selectBoxList = new HashMap<>();

//Get the List of Addresses
        Address[] addressList =
                databaseManager.addressesTable.getAll().toArray(Address[]::new);

        //Get the List of Building Types
        BuildingType[] buildingTypeList =
                databaseManager.buildingsTable.getAll().toArray(BuildingType[]::new);

        //Get the List of Project Managers
        ProjectManager[] projectManagerList =
                databaseManager.projectManagersTable.getAll().toArray(ProjectManager[]::new);

        //Get the List of Structural Engineers
        StructuralEngineer[] structuralEngineerList =
                databaseManager.structuralEngineersTable.getAll().toArray(StructuralEngineer[]::new);

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
        JComboBox<BuildingType> buildingSelectBox = new JComboBox<>(buildingTypeList);
        JComboBox<ProjectManager> projectManagerSelectBox = new JComboBox<>(projectManagerList);
        JComboBox<StructuralEngineer> structuralEngineerSelectBox = new JComboBox<>(structuralEngineerList);
        JComboBox<Customer> customerSelectBox = new JComboBox<>(customerList);
        JComboBox<Contractor> contractorSelectBox = new JComboBox<>(contractorList);
        JComboBox<Architect> architectSelectBox = new JComboBox<>(architectList);

        //Add the Items to the Required Items List
        selectBoxList.put(ProjectProperties.Address, addressSelectBox);
        selectBoxList.put(ProjectProperties.Building_Type, buildingSelectBox);
        selectBoxList.put(ProjectProperties.Project_Manager, projectManagerSelectBox);
        selectBoxList.put(ProjectProperties.Structural_Engineer, structuralEngineerSelectBox);
        selectBoxList.put(ProjectProperties.Architect, architectSelectBox);
        selectBoxList.put(ProjectProperties.Contractor, contractorSelectBox);
        selectBoxList.put(ProjectProperties.Customer, customerSelectBox);

        //Return the Required Items List
        return selectBoxList;
    }
}

