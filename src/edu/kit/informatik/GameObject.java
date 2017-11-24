package edu.kit.informatik;

/**
 * @author Oleh Kuzmin
 * @version 1.0
 */
abstract class GameObject {

    private String name;

    /**
     * name of game object
     * 
     * @return name
     */

    public String getName() {
        return name;
    }

    /**
     * Set the name of object
     * 
     * @param name
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

}
