package edu.kit.informatik;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class describes 3 dimensional struckture wiht basics functionality
 * 
 * @author Oleh Kuzmin
 * @version 1.0
 */
final class GameField {

    private final LinkedList<GameObject> struckturOfGame[][];
    private final Coordinate             coordinate;

    /**
     * Set size of system
     * 
     * @param setDimensionLayout
     *            size
     * 
     */

    @SuppressWarnings("unchecked")
    public GameField(final Coordinate setDimensionLayout) {
        this.coordinate = setDimensionLayout;
        // init x and y dimensions (5x5)
        this.struckturOfGame = new LinkedList[this.coordinate.getOx()][this.coordinate
                .getOy()];
        // init z dimension(5x5x4)
        for (int oY = 0; oY < struckturOfGame.length; oY++) {
            for (int oX = 0; oX < struckturOfGame.length; oX++) {
                struckturOfGame[oY][oX] = new LinkedList<>();
            }
        }

    }

    /**
     * Add only over the items
     * 
     * @param coordinate
     *            where to add
     * @param object
     *            what to add
     * @return true if successfully
     */

    boolean addToFieldOver(final Coordinate coordinate, final GameObject object) {
        if (checkInputofCoordinate(coordinate) && object != null
                && checkHeightofStrukture(coordinate)) {

            final int tempOx = coordinate.getOx();
            final int tempOy = coordinate.getOy();

            struckturOfGame[tempOy][tempOx].add(object);
            return true;
        }
        return false;

    }

    /**
     * 
     * @param coordinate
     *            whence to remove
     * @return true if successfully
     */
    public boolean removeElementFromField(final Coordinate coordinate) {
        if (checkInputofCoordinate(coordinate)) {

            final int tempOx = coordinate.getOx();
            final int tempOy = coordinate.getOy();
            final int tempOz = coordinate.getOz();
            try {
                struckturOfGame[tempOy][tempOx].remove(tempOz);
            } catch (IndexOutOfBoundsException e) {
                return false;
            }

            return true;
        }
        return false;
    }

    /**
     * 
     * @param coordinate
     *            the position
     * @return null if false
     */
    public GameObject getElementFromField(final Coordinate coordinate) {
        GameObject objectTemp = null;

        if (checkInputofCoordinate(coordinate)) {
            final int tempOx = coordinate.getOx();
            final int tempOy = coordinate.getOy();
            final int tempOz = coordinate.getOz();

            try {
                objectTemp = this.struckturOfGame[tempOy][tempOx].get(tempOz);
            } catch (IndexOutOfBoundsException e) {

            }

        }

        return objectTemp;
    }

    /**
     * 
     * @param coordinate
     *            whence
     * @return size
     */
    Integer getSizeOfColumn(final Coordinate coordinate) {
        final int tempOx = coordinate.getOx();
        final int tempOy = coordinate.getOy();
        return this.struckturOfGame[tempOy][tempOx].size();
    }

    /**
     * 
     * @param coordinate
     *            whence
     * @return true if empty
     */
    public boolean isEmptyColumn(final Coordinate coordinate) {
        final int tempOx = coordinate.getOx();
        final int tempOy = coordinate.getOy();
        return this.struckturOfGame[tempOy][tempOx].isEmpty();
    }

    /**
     * 
     * @param coordinate
     *            whence
     * @param object
     *            to search
     * @return true if containse
     */
    public boolean containsObjectInColumn(final Coordinate coordinate,
            final GameObject object) {
        final int tempOx = coordinate.getOx();
        final int tempOy = coordinate.getOy();
        return this.struckturOfGame[tempOy][tempOx].contains(object);
    }

    /**
     * 
     * @param coordinate
     *            whence
     * @return true if removed
     */
    boolean removeElementFromFieldOver(final Coordinate coordinate) {
        if (checkInputofCoordinate(coordinate)) {
            final int tempOx = coordinate.getOx();
            final int tempOy = coordinate.getOy();
            try {

                this.struckturOfGame[tempOy][tempOx].removeLast();

            } catch (NoSuchElementException e) {
                return false;
            }
            return true;

        }
        return false;

    }

    /**
     * Returned the struckture of game
     * 
     * @return LinkedList
     */

    public LinkedList<GameObject>[][] getStruckturOfGame() {
        return struckturOfGame;
    }

    private boolean checkInputofCoordinate(Coordinate coordinate) {
        if (coordinate != null) {

            final int tempOx = coordinate.getOx();
            final int tempOy = coordinate.getOy();
            final int tempOz = coordinate.getOz();
            final boolean tempCheckOx = tempOx >= 0
                    && tempOx < this.coordinate.getOx();
            final boolean tempCheckOy = tempOy >= 0
                    && tempOy < this.coordinate.getOy();
            final boolean tempCheckOz = tempOz >= 0
                    && tempOz < this.coordinate.getOz();
            if (tempCheckOx && tempCheckOy && tempCheckOz) {
                return true;
            }
        }
        return false;

    }

    // set the max oZ to 4
    private boolean checkHeightofStrukture(final Coordinate coordinate) {

        final int tempOx = coordinate.getOx();
        final int tempOy = coordinate.getOy();
        return struckturOfGame[tempOy][tempOx].size() < this.coordinate.getOz();
    }

    /**
     * 
     * @return sruckture from above of the game
     */
    public String printStruckture() {
        String tempStruckture = "";
        int tmpCnt = 0;

        for (int i = 0; i < struckturOfGame.length; i++) {
            if (i != 0) {

                tempStruckture += "\n";
            }
            for (int j = 0; j < struckturOfGame.length; j++) {
                try {

                    if (tmpCnt < 5) {
                        tempStruckture += struckturOfGame[i][j].getLast()
                                .toString().replace("[", "").replace("]", "")
                                + ",";
                        tmpCnt++;
                    } else {
                        tmpCnt = 0;
                    }

                } catch (NoSuchElementException e) {
                    tempStruckture += ".";
                    tmpCnt++;
                    if (tmpCnt < 5) {

                        tempStruckture += ",";
                    } else {
                        tmpCnt = 0;
                    }

                }
            }
        }

        return tempStruckture;
    }

    /**
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    /**
     * 
     * @param coordinate
     *            whence
     * @return array of the cell
     */
    public Object[] cellPrint(final Coordinate coordinate) {
        final int tempOx = coordinate.getOx();
        final int tempOy = coordinate.getOy();
        return this.struckturOfGame[tempOy][tempOx].toArray();
    }

}
