//Setup the Package
package Poised.Utils.Database.Tables;

//Imports
import Poised.Utils.Database.Database_Manager;
import Poised.Utils.Database.Tables.Properties.Building_Properties;
import Poised.Utils.Dialogue.Components.Form_Field;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The Building Represents the Buildings Table in the Database
 */
public class Building {

    //Setup the Public Properties
    public int id;
    public String buildingType;

    /**
     * Constructor for Building.
     *
     * @param id              The ID for the item in the Database (Null if a Blank Object is Needed).
     * @param buildingType    The Type of the Building (eg: Apartment, House, etc...)
     */
    public Building(Integer id, String buildingType){

        //Update the Public Properties
        this.id = id == null ? -1 : id;
        this.buildingType = buildingType;
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
        ArrayList<Building_Properties> propertyNames = getProperties();

        //Get the Property Values
        ArrayList<String> propertyValues = this.getValues();

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
            if(i < propertyNames.size() - 1) dataString.append(", ");
        }

        //Return the Data String
        return dataString.toString();
    }

    /**
     * Returns a list of Properties for this Table.
     *
     * @return An ArrayList of Properties for this Table.
     */
    public static ArrayList<Building_Properties> getProperties() {

        //Setup the Properties String Array
        ArrayList<Building_Properties> properties = new ArrayList<>();

        //Add the Required Properties
        properties.addAll(Arrays.stream(Building_Properties.values()).toList());

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
        values.add(String.format("'%s'", this.buildingType));

        //Return the Values
        return values;
    }

     /**
     * Returns a Map of The Form Fields for this Object (The Form Fields have been Filled with This Object's Data).
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @return A Map Containing the Table's Properties as Keys and Filled Form Fields as the Corresponding Values.
     */
    public HashMap<Building_Properties, Form_Field> getFilledFormFields(Database_Manager databaseManager) {

        //Get the List of Form Fields
        HashMap<Building_Properties, Form_Field> formFields = getFormFields(databaseManager);

        //Get the Input Fields for the Properties
        JTextField idInputField = ((JTextField) formFields.get(Building_Properties.id).fieldInput());
        JTextField buildingTypeInputField = ((JTextField) formFields.get(Building_Properties.Building_Type).fieldInput());

        //Update the Input Fields
        idInputField.setText(Integer.toString(this.id));
        buildingTypeInputField.setText(this.buildingType);

        //Return the Filled Form Fields
        return formFields;
    }

     /**
     * Returns a Map of The Form Fields for this Object.
     *
     * @param databaseManager The Database Manager that will Handle all the database Interactions.
     * @return A Map Containing the Table's Properties as Keys and Form Fields as the Corresponding Values.
     */
    public static HashMap<Building_Properties, Form_Field> getFormFields(Database_Manager databaseManager) {

        //Setup the List of Form Fields
        HashMap<Building_Properties, Form_Field> formFields = new HashMap<>();

        //Get the List of Properties
        ArrayList<Building_Properties> propertyList = getProperties();

        //Loop through the Requested Properties
        for (Building_Properties currentProperty : propertyList) {

            //Setup the Input Field's Label
            JLabel inputFieldLabel = new JLabel(currentProperty.name());

            //Setup the New Input Field
            JComponent inputField = new JTextField(10);

            //Setup the New Form Field
            Form_Field newFormField = new Form_Field(inputFieldLabel, inputField);

            //Add the New Form Field to the Form Fields List
            formFields.put(currentProperty, newFormField);
        }

        //Return the Form Fields
        return formFields;
    }
}
