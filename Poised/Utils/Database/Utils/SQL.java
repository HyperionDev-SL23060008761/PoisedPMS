//Setup the Package
package Poised.Utils.Database.Utils;

//Imports
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Utility for Performing SQL Manipulations to Data in the Database
 */
public class SQL {

    /**
     * Inserts a Row into the Specified Database Table.
     *
     * @param queryManager   The Statement used to Manage Queries to the Database.
     * @param tableName      The Name of the Table where the row will be Inserted at.
     * @param propertyList   The List of Property Names Associated with the Table.
     * @param valueList      The List of Values Associated with the Table.
     * @return               boolean Result of whether the Row could be Inserted.
     */
    public static boolean insert(
            Statement queryManager,
            String tableName,
            ArrayList<String> propertyList,
            ArrayList<String> valueList
    ) {

        //Generate the SQL Insert String
        String queryString = generateInsertString(tableName, propertyList, valueList);

        //Insert the Row into the Database
        return modifyDB(queryManager, queryString);
    }

    /**
     * Updates a Row in the Specified Database Table.
     *
     * @param queryManager   The Statement used to Manage Queries to the Database.
     * @param tableName      The Name of the Table where the row will be Updated at.
     * @param propertyList   The List of Property Names Associated with the Table.
     * @param valueList      The List of Values Associated with the Table.
     * @return               boolean Result of whether the Row could be Updated.
     */
    public static boolean update(
            Statement queryManager,
            String tableName,
            ArrayList<String> propertyList,
            ArrayList<String> valueList
    ) {

        //Generate the SQL Update String
        String queryString = generateUpdateString(tableName, propertyList, valueList);

        //Update the Row in the Database
        return modifyDB(queryManager, queryString);
    }

    /**
     * Removes a Row from the Specified Database Table.
     *
     * @param queryManager   The Statement used to Manage Queries to the Database.
     * @param tableName      The Name of the Table where the row will be Removed from.
     * @param propertyList   The List of Property Names Associated with the Table.
     * @param valueList      The List of Values Associated with the Table.
     * @return               boolean Result of whether the Row could be Inserted.
     */
    public static boolean remove(
            Statement queryManager,
            String tableName,
            ArrayList<String> propertyList,
            ArrayList<String> valueList
    ) {

        //Setup the Primary Key String for the Where Clause
        String primaryKey = String.format("%s=%s", propertyList.remove(0), valueList.remove(0));

        //Setup the SQL Remove String
        String queryString = String.format("DELETE FROM %s WHERE %s", tableName, primaryKey);

        //Update the Row in the Database
        return modifyDB(queryManager, queryString);
    }

    /**
     * Generates an SQL Insert String using Properties and Values of a Table.
     *
     * @param tableName      The Name of the Table where the row will be Inserted at.
     * @param propertyList   The List of Property Names Associated with the Table.
     * @param valueList      The List of Values Associated with the Table.
     * @return               The String used to Manipulate the Data in the Database
     */
    private static String generateInsertString(
            String tableName,
            ArrayList<String> propertyList,
            ArrayList<String> valueList
    ) {

        //Check if the first property and value is an auto increment primary key
        if(propertyList.get(0).equals("id")) {

            //Remove the First Property
            propertyList.remove(0);

            //Remove the First Value
            valueList.remove(0);
        }

        //Loop through the List of Properties and Values
        for (int i = 0; i < propertyList.size(); i++) {

            //Get the Current Value
            String currentValue = valueList.get(i);

            //Check if the Value is Null and fix the Null String
            if (currentValue.equals("'null'")) valueList.set(i, "null");
        }

        //Setup the Property Names String
        String properties = propertyList.toString().replaceAll("(^\\[)|(]$)", "");

        //Setup the Property Values String
        String values = valueList.toString().replaceAll("(^\\[)|(]$)", "");

        //Setup the SQL Insert String
        String sqlInsertString = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, properties, values);

        //Return the SQL Insert String
        return sqlInsertString;
    }

    /**
     * Generates an SQL Update String using Properties and Values of a Table.
     *
     * @param tableName      The Name of the Table where the row will be Updated at.
     * @param propertyList   The List of Property Names Associated with the Table.
     * @param valueList      The List of Values Associated with the Table.
     * @return               The String used to Manipulate the Data in the Database
     */
    private static String generateUpdateString(
            String tableName,
            ArrayList<String> propertyList,
            ArrayList<String> valueList
    ) {

        //Setup the Primary Key String for the Where Clause
        String primaryKey = String.format("%s=%s", propertyList.remove(0), valueList.remove(0));

        //Setup the Key Values String
        StringBuilder keyValues = new StringBuilder();

        //Loop through the List of Properties
        for (int i = 0; i < propertyList.size(); i++) {

            //Get the Specific Property
            String property = propertyList.get(i);

            //Get the Property's Value
            String value = valueList.get(i);

            //Check if the Value is Null and fix the Null String
            if (value.equals("'null'")) value = "null";

            //Setup the Property with its value
            String propertyValue = String.format("%s=%s", property, value);

            //Add the Property with its value to the Key Values String
            keyValues.append(propertyValue);

            //Check if there are more Properties and append a separator
            if(i < propertyList.size() - 1) keyValues.append(", ");
        }

        //Setup the SQL Insert String
        String sqlInsertString = String.format("UPDATE %s SET %s WHERE %s", tableName, keyValues, primaryKey);

        //Return the SQL Insert String
        return sqlInsertString;
    }

    /**
     * Manipulates Data in the Database using the Specified Query String.
     *
     * @param queryManager   The Statement used to Manage Queries to the Database.
     * @param queryString    The String used to Manipulate the Data in the Database.
     * @return               boolean Result of whether the Data could be Manipulated.
     */
    private static boolean modifyDB(
            Statement queryManager,
            String queryString
    ) {

        //Try to Modify the Row in the Database
        try {

            //Modify the Row in the Database
            queryManager.executeUpdate(queryString);
        } catch (SQLException exception) {

            //Log the Exception to the Console
            System.out.printf("%nSQL ERROR:%n%s%n", exception);

            //Return False indicating an Unsuccessful Modification
            return false;
        }

        //Return True indicating a Successful Modification
        return true;
    }

}
