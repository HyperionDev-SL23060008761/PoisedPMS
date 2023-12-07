//Setup the Package
package Poised.Utils.Database.Tables;

//Imports

import Poised.Utils.Database.Utils.Role;

/**
 * The Structural Engineer Represents the Structural Engineers Table in the Database
 */
public class StructuralEngineer extends Role {

    /**
     * Constructor for StructuralEngineer.
     *
     * @param id              The ID for the item in the Database (Null if a Blank Object is Needed).
     * @param personalDetails The Personal Details for the Structural Engineer.
     */
    public StructuralEngineer(Integer id, PersonalDetails personalDetails){

        //Construct the Parent Class
        super(id, personalDetails);
    }
}
