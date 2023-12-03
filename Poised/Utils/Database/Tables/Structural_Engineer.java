//Setup the Package
package Poised.Utils.Database.Tables;

//Imports

import Poised.Utils.Database.Utils.Role;

/**
 * The Structural Engineer Represents the Structural Engineers Table in the Database
 */
public class Structural_Engineer extends Role {

    /**
     * Constructor for Structural_Engineer.
     *
     * @param id              The ID for the item in the Database (Null if a Blank Object is Needed).
     * @param personalDetails The Personal Details for the Structural Engineer.
     */
    public Structural_Engineer(Integer id, Personal_Details personalDetails){

        //Construct the Parent Class
        super(id, personalDetails);
    }
}
