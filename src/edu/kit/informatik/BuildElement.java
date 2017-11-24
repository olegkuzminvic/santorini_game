package edu.kit.informatik;

/**
 * This class contains deskription creating of the build elements
 * 
 * @author Oleh Kuzmin
 * @version 1.0
 *
 */
final class BuildElement extends GameObject {

    /**
     * Constuktor
     */
    public BuildElement() {

    }

    /**
     * There are 2 types of the build elements in the game.
     * 
     * @param element
     *            type of the build element
     * 
     */

    public BuildElement(final BuildElementEnum element) {

        if (element == BuildElementEnum.CUBOID) {
            new BuildElement();
            this.setName("C");

        } else if (element == BuildElementEnum.DOME) {
            new BuildElement();
            this.setName("D");
        }

    }

    /**
     * Returned toString,C-Cuboid,D-Dome
     * 
     * @return to string
     */
    @Override
    public String toString() {
        return String.format("%s", this.getName());
    }

}
