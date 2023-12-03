//Setup the Package
package Poised.Utils.Database.Tables;

//Imports

import Poised.Utils.Database.Utils.Role;

/**
 * The Project Manager Represents the Project Managers Table in the Database
 */
public class Project_Manager extends Role {

    /**
     * Constructor for Project_Manager.
     *
     * @param id              The ID for the item in the Database (Null if a Blank Object is Needed).
     * @param personalDetails The Personal Details for the Project Manager.
     */
    public Project_Manager(Integer id, Personal_Details personalDetails){

        //Construct the Parent Class
        super(id, personalDetails);
    }
}
