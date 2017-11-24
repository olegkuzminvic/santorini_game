package edu.kit.informatik;

/**
 * This class describes system of the coordinate.
 * 
 * @author Oleh Kuzmin
 * @version 1.0
 */
final class Coordinate {
    private int oX;
    private int oY;
    private int oZ;

    /**
     * There are 3 dimensions
     * 
     * @param oX
     *            x dimension
     * @param oY
     *            y dimension
     * @param oZ
     *            z dimension
     */
    Coordinate(final int oX, final int oY, final int oZ) {
        this.oX = oX;
        this.oY = oY;
        this.oZ = oZ;
    }

    /**
     * Constuktor only with 2 dimensions for action in x,y coordinates
     * 
     * @param oX
     *            x dimension
     * @param oY
     *            y dimension
     */
    public Coordinate(final int oX, final int oY) {
        this.oX = oX;
        this.oY = oY;
        this.oZ = 0;
    }

    /**
     * @return the oX
     */
    public int getOx() {
        return this.oX;
    }

    /**
     * @return the oY
     */
    public int getOy() {
        return this.oY;
    }

    /**
     * 
     * @return oz
     */
    public int getOz() {
        return this.oZ;
    }

    /**
     * @param oX
     *            the oX to set
     */
    public void setOx(int oX) {
        this.oX = oX;
    }

    /**
     * @param oY
     *            the oY to set
     */
    public void setOy(int oY) {
        this.oY = oY;
    }

    /**
     * @param oZ
     *            the oZ to set
     */
    public void setOz(int oZ) {
        this.oZ = oZ;
    }

    /**
     * Returned coordinates in the form ox,oy,oz
     * 
     * @return toString
     */
    @Override
    public String toString() {

        return String.format("(%d% d% d)", this.oX, this.oY, this.oZ);
    }

}
