//Setup the Package
package Poised.Utils.Database.Utils;

//Imports
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Personal_Details;
import Poised.Utils.Database.Tables.Properties.Role_Properties;
import Poised.Utils.Dialogue.Components.Form_Field;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The Role Represents what the Relationship of different Human Related Tables are
 */
public class Role {

    //Setup the Public Properties
    public int id;
    public Personal_Details personalDetails;

    /**
     * Constructor for Role.
     *
     * @param id              The ID for the item in the Database (Null if a Blank Object is Needed).
     * @param personalDetails The Personal Details for this Human.
     */
    public Role(Integer id, Personal_Details personalDetails) {

        //Update the Public Properties
        this.id = id == null ? -1 : id;
        this.personalDetails = personalDetails;
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
        ArrayList<Role_Properties> propertyNames = getProperties();

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
    public static ArrayList<Role_Properties> getProperties() {

        //Setup the Properties String Array
        ArrayList<Role_Properties> properties = new ArrayList<>();

        //Add the Required Properties
        properties.addAll(Arrays.stream(Role_Properties.values()).toList());

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
        values.add(Integer.toString(this.personalDetails.id));

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

        //Setup the Minimized Values
        if(minimal) values.add(String.format("%s", this.personalDetails.name));

        //Setup the Non-Minimized Values
        if(!minimal) values.add(String.format("{%s}", this.personalDetails.toString()));

        //Return the Values
        return values;
    }

    /**
     * Returns a Map of The Form Fields for this Object (The Form Fields have been Filled with This Object's Data).
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @return A Map Containing the Table's Properties as Keys and Filled Form Fields as the Corresponding Values.
     */
    public HashMap<Role_Properties, Form_Field> getFilledFormFields(Database_Manager databaseManager) {

        //Get the List of Form Fields
        HashMap<Role_Properties, Form_Field> formFields = getFormFields(databaseManager);

        //Get the Input Fields for the Properties
        JTextField idInputField = ((JTextField) formFields.get(Role_Properties.id).fieldInput());
        JComboBox<Personal_Details> personalDetailsInputField =
                ((JComboBox<Personal_Details>) formFields.get(Role_Properties.Personal_Details).fieldInput());

        //Update the Input Fields
        idInputField.setText(Integer.toString(this.id));
        personalDetailsInputField.setSelectedItem(this.personalDetails);

        //Return the Filled Form Fields
        return formFields;
    }

    /**
     * Returns a Map of The Form Fields for this Object.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @return A Map Containing the Table's Properties as Keys and Form Fields as the Corresponding Values.
     */
    public static HashMap<Role_Properties, Form_Field> getFormFields(Database_Manager databaseManager) {

        //Setup the List of Form Fields
        HashMap<Role_Properties, Form_Field> formFields = new HashMap<>();

        //Get the List of Properties
        ArrayList<Role_Properties> propertyList = getProperties();

        //Get the List of Personal Details
        Personal_Details[] personalDetailsList =
                databaseManager.personalDetailsTable.getAll().toArray(Personal_Details[]::new);

        //Setup the Personal Details Select Box
        JComboBox<Personal_Details> personalDetailsSelectBox = new JComboBox<>(personalDetailsList);

        //Loop through the Requested Properties
        for (Role_Properties currentProperty : propertyList) {

            //Setup the Input Field's Label
            JLabel inputFieldLabel = new JLabel(currentProperty.name(), SwingConstants.LEFT);

            //Setup the New Input Field
            JComponent inputField = new JTextField(10);

            //Check if the Current Property is the Personal Details Select Box and update the Input Field
            if(currentProperty.equals(Role_Properties.Personal_Details)) inputField = personalDetailsSelectBox;

            //Setup the New Form Field
            Form_Field newFormField = new Form_Field(inputFieldLabel, inputField);

            //Add the New Form Field to the Form Fields List
            formFields.put(currentProperty, newFormField);
        }

        //Return the Form Fields
        return formFields;
    }
}
