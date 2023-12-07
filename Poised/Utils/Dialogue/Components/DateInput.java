//Setup the Package
package Poised.Utils.Dialogue.Components;

//Imports
import Poised.Utils.Utils;
import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * A supportive Component for Generating Date Inputs and Formatting Dates from User Inputs.
 */
public class DateInput extends JComponent {

    //Setup the Public Properties
    public final JTextField inputComponent;

    //Setup the Private Properties
    DateTimeFormatter dateFormatter;

    /**
     * Constructor for DateInput.
     */
    public DateInput() {

        //Update the Public Properties
        this.inputComponent = new JTextField(10);

        //Update the Private Properties
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    /**
     * Gets the Selected Date Based on the User's Input.
     *
     * @return The Selected Date as an SQL Date object or Null if the User's Input is Invalid
     */
    public Date getSelectedDate() {

        //Get the Date String
        String dateString = this.inputComponent.getText();

        //Remove all non date related characters
        dateString = dateString.replaceAll("[^0-9-]", "");

        //Split the Date String into its Components
        String[] dateStringArray = dateString.split("-");

        //Check if an Invalid Date String was Provided
        if(dateStringArray.length != 3) return null;

        //Setup the Selected Date Components' Strings
        String yearString = dateStringArray[0];
        String monthString = dateStringArray[1];
        String dayString = dateStringArray[2];

        //Check if the Date Components' Strings are Invalid
        if(yearString.length() != 4 || monthString.length() != 2 || dayString.length() != 2) return null;

        //Get the Selected Date Values
        int selectedYear = Integer.parseInt(yearString);
        int selectedMonth = Integer.parseInt(monthString);
        int selectedDay = Integer.parseInt(dayString);

        //Check if the Selected Date Values are Invalid
        if(!Utils.isInRange(selectedYear, 1900, 3000)) return null;
        if(!Utils.isInRange(selectedMonth, 1, 12)) return null;
        if(!Utils.isInRange(selectedDay, 1, 31)) return null;

        //Setup the Formatted String
        String formattedDateString = String.format("%s-%s-%s", yearString, monthString, dayString);

        //Setup the Local Date
        LocalDate localDate;

        //Attempt to Parse the Local Date from the Date String
        try {

            //Parse the Local Date from the Date String
            localDate = LocalDate.parse(formattedDateString, this.dateFormatter);
        } catch (Exception exception) {
            return null;
        }

        //Get the Local Date's Timestamp (In Milliseconds)
        long dateTimestampMS = localDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000;

        //Setup the Selected Date
        Date selectedDate = new Date(dateTimestampMS);

        //Return the Selected Date
        return selectedDate;
    }

    /**
     * Sets the Date to the Specified Date.
     *
     * @param selectedDate The Date to be Set in the Input Component.
     */
    public void setSelectedDate(Date selectedDate) {

        //Check if the Selected Date is Null
        if(selectedDate == null) return;

        //Setup the Date String
        String dateString = selectedDate.toString();

        //Update the Date
        this.inputComponent.setText(dateString);
    }
}
