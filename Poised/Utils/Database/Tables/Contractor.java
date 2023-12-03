//Setup the Package
package Poised.Utils.Database.Tables;

//Imports

import Poised.Utils.Database.Utils.Role;

/**
 * The Contractor Represents the Contractors Table in the Database
 */
public class Contractor extends Role {

    /**
     * Constructor for Contractor.
     *
     * @param id              The ID for the item in the Database (Null if a Blank Object is Needed).
     * @param personalDetails The Personal Details for the Contractor.
     */
    public Contractor(Integer id, Personal_Details personalDetails){

        //Construct the Parent Class
        super(id, personalDetails);
    }
}
