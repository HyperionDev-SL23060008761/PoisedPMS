//Setup the Package
package Poised.Utils;

/**
 * Utilities to be Used to Assist other Components.
 */
public class Utils {

    /**
     * Checks if a Value is within the Specified Range.
     *
     * @param value   The Specified Value.
     * @param minimum The Maximum Value of the Range.
     * @param maximum The Minimum Value of the Range.
     * @return boolean if the value is within the specified range.
     */
    public static boolean isInRange(int value, int minimum, int maximum) {

        //Return the Result of the In Range Check
        return value >= minimum && value <= maximum;
    }
}
