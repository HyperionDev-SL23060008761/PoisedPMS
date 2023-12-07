//Setup the Package
package Poised.Utils.Database.Tables;

//Imports

import Poised.Utils.Database.Utils.Role;

/**
 * The Customer Represents the Customers Table in the Database
 */
public class Customer extends Role {

    /**
     * Constructor for Customer.
     *
     * @param id              The ID for the item in the Database (Null if a Blank Object is Needed).
     * @param personalDetails The Personal Details for the Customer.
     */
    public Customer(Integer id, PersonalDetails personalDetails){

        //Construct the Parent Class
        super(id, personalDetails);
    }
}
