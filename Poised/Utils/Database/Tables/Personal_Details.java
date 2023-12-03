//Setup the Package
package Poised.Utils.Database.Tables;

import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Properties.Personal_Details_Properties;
import Poised.Utils.Dialogue.Components.Form_Field;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The Personal Details Represents the Personal Details Table in the Database
 */
public class Personal_Details {

    //Setup the Public Properties
    public int id;
    public String name;
    public String phoneNumber;
    public String email;
    public Address address;

    /**
     * Constructor for Personal_Details.
     *
     * @param id              The ID for the item in the Database (Null if a Blank Object is Needed).
     * @param name            The Person's Name.
     * @param phoneNumber     The Person's Phone Number.
     * @param email           The Person's Email.
     * @param address         The Address for the Person.
     */
    public Personal_Details(Integer id, String name, String phoneNumber, String email, Address address){

        //Update the Public Properties
        this.id = id == null ? -1 : id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    /**
     * Returns all the Data in the form of a String.
     *
     * @return The Formatted String Version of the Data
     */
    public String toString() {

        //Return the String
        return toString(false);
    }

    /**
     * Returns a Minimized or Full version of the String Version of the Data.
     *
     * @param minimal True for a Minimized Version, False for the Full Data.
     * @return The Formatted String Version of the Data.
     */
    public String toString(boolean minimal) {

        //Get the Property Names
        ArrayList<Personal_Details_Properties> propertyNames = getProperties();

        //Get the Property Values
        ArrayList<String> propertyValues = this.getFormattedValues(minimal);

        //Setup the Data String
        StringBuilder dataString = new StringBuilder();

        //Loop through the Property Names
        for (int i = 0; i < propertyNames.size(); i++) {

            //Get the Property Name
            String propertyName = propertyNames.get(i).name();

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
    public static ArrayList<Personal_Details_Properties> getProperties() {

        //Setup the Properties String Array
        ArrayList<Personal_Details_Properties> properties = new ArrayList<>();

        //Add the Required Properties
        properties.addAll(Arrays.stream(Personal_Details_Properties.values()).toList());

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
        values.add(Integer.toString(this.id));
        values.add(String.format("'%s'", this.name));
        values.add(String.format("'%s'", this.phoneNumber));
        values.add(String.format("'%s'", this.email));
        values.add(Integer.toString(this.address.id));

        //Return the Values
        return values;
    }

    /**
     * Returns a list of Formatted Values for this Object.
     *
     * @param minimal True for a Minimized Version, False for the Full List of Values.
     * @return An ArrayList of Values for this Object.
     */
    public ArrayList<String> getFormattedValues(boolean minimal) {

        //Setup the Values String Array
        ArrayList<String> values = new ArrayList<>();

        //Add the Required Values
        values.add(Integer.toString(this.id));
        values.add(String.format("'%s'", this.name));
        values.add(String.format("'%s'", this.phoneNumber));
        values.add(String.format("'%s'", this.email));

        //Setup the Minimized Values
        if(minimal) values.add(String.format("%s", this.address.id));

        //Setup the Non-Minimized Values
        if(!minimal) values.add(String.format("{%s}", this.address.toString()));

        //Return the Values
        return values;
    }

     /**
     * Returns a Map of The Form Fields for this Object (The Form Fields have been Filled with This Object's Data).
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @return A Map Containing the Table's Properties as Keys and Filled Form Fields as the Corresponding Values.
     */
    public HashMap<Personal_Details_Properties, Form_Field> getFilledFormFields(Database_Manager databaseManager) {

        //Get the List of Form Fields
        HashMap<Personal_Details_Properties, Form_Field> formFields = getFormFields(databaseManager);

        //Get the Input Fields for the Properties
        JTextField idInputField = ((JTextField) formFields.get(Personal_Details_Properties.id).fieldInput());
        JTextField nameInputField = ((JTextField) formFields.get(Personal_Details_Properties.Name).fieldInput());
        JTextField phoneNumberInputField =
                ((JTextField) formFields.get(Personal_Details_Properties.Phone_Number).fieldInput());
        JTextField emailInputField = ((JTextField) formFields.get(Personal_Details_Properties.Email).fieldInput());
        JComboBox<Address> addressInputField =
                ((JComboBox<Address>) formFields.get(Personal_Details_Properties.Address).fieldInput());

        //Update the Input Fields
        idInputField.setText(Integer.toString(this.id));
        nameInputField.setText(this.name);
        phoneNumberInputField.setText(this.phoneNumber);
        emailInputField.setText(this.email);
        addressInputField.setSelectedItem(this.address);

        //Return the Filled Form Fields
        return formFields;
    }

     /**
     * Returns a Map of The Form Fields for this Object.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @return A Map Containing the Table's Properties as Keys and Form Fields as the Corresponding Values.
     */
    public static HashMap<Personal_Details_Properties, Form_Field> getFormFields(Database_Manager databaseManager) {

        //Setup the List of Form Fields
        HashMap<Personal_Details_Properties, Form_Field> formFields = new HashMap<>();

        //Get the List of Properties
        ArrayList<Personal_Details_Properties> propertyList = getProperties();

        //Get the List of Addresses
        Address[] addressList = databaseManager.addressesTable.getAll().toArray(Address[]::new);

        //Setup the Address Select Box
        JComboBox<Address> addressSelectBox = new JComboBox<>(addressList);

        //Loop through the Requested Properties
        for (Personal_Details_Properties currentProperty : propertyList) {

            //Setup the Input Field's Label
            JLabel inputFieldLabel = new JLabel(currentProperty.name());

            //Setup the New Input Field
            JComponent inputField = new JTextField(10);

            //Check if the Current Property is the Address Select Box and update the Input Field
            if(currentProperty.equals(Personal_Details_Properties.Address)) inputField = addressSelectBox;

            //Setup the New Form Field
            Form_Field newFormField = new Form_Field(inputFieldLabel, inputField);

            //Add the New Form Field to the Form Fields List
            formFields.put(currentProperty, newFormField);
        }

        //Return the Form Fields
        return formFields;
    }
}
