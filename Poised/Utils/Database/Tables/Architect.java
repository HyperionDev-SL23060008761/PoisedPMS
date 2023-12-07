//Setup the Package
package Poised.Utils.Database.Tables;

//Imports

import Poised.Utils.Database.Utils.Role;

/**
 * The Architect Represents the Architects Table in the Database
 */
public class Architect extends Role {

    /**
     * Constructor for Architect.
     *
     * @param id              The ID for the item in the Database (Null if a Blank Object is Needed).
     * @param personalDetails The Personal Details for the Architect.
     */
    public Architect(Integer id, PersonalDetails personalDetails){

        //Construct the Parent Class
        super(id, personalDetails);
    }
}
