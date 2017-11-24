package edu.kit.informatik;

/**
 * This class desribes dynamic behavior of gaming strukture
 * 
 * @author Oleh Kuzmin
 * @version 1.0
 */
class GameAction {

    private static final String ERROR_MSG_FALSE_INPUT    = "Error, your input is false!";

    private static final String NOTIFICATION_MSG_VICTORY = "Victory ";

    private GameField           struckture;
    private Coordinate          coordinateDest;

    /**
     * d
     * 
     * @param struckture
     *            d
     */
    public GameAction(final GameField struckture) {
        this.struckture = struckture;

    }

    /**
     * This method moves figure from current coordinate to dest coordinate
     * 
     * @param coordinateSend
     *            current coordinate
     * @param coordinateDest
     *            final coordinate
     * @return true if successfully if the object is moved,other false
     */

    boolean moveFigureTo(final Coordinate coordinateSend,
            final Coordinate coordinateDest) {
        final boolean checkInputSend = checkInputofCoordinate(coordinateSend);
        final boolean checkInputDest = checkInputofCoordinate(coordinateDest);
        // Copy the object
        final GameObject object;

        if (checkInputSend && checkInputDest) {
            object = this.struckture.getElementFromField(coordinateSend);
            if (object instanceof BuildElement) {
                return false;
            }

            if (object != null && checkMoveRule(coordinateSend, coordinateDest)
                    && checkMoveOzRule(coordinateSend, coordinateDest)
                    && isOccupiedByAnotherPlayer(coordinateDest)) {

                final boolean checkAdd = this.struckture.addToFieldOver(
                        coordinateDest, object);
                final boolean checkDel = this.struckture
                        .removeElementFromFieldOver(coordinateSend);
                if (checkDel && checkAdd) {
                    this.coordinateDest = coordinateDest;
                    if (checkForTower(coordinateDest)) {
                        Terminal.printLine(NOTIFICATION_MSG_VICTORY);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param coordinateBuild
     *            place to the building
     * @param build
     *            an object that should be built
     * @return true if successfully if the object is builded,other false
     */

    boolean buildElement(final Coordinate coordinateBuild,
            final BuildElement build) {
        if (checkInputofCoordinate(coordinateBuild) && build != null
                && isOccupiedByAnotherPlayer(coordinateBuild)) {
            if (checkMoveRule(coordinateBuild, this.coordinateDest)) {
                if (build.getName().equals("D")) {
                    if (checkForTower(coordinateBuild)) {
                        return this.struckture.addToFieldOver(coordinateBuild,
                                build);
                    } else {
                        return false;
                    }

                } else {
                    if (!checkForTower(coordinateBuild)) {
                        return this.struckture.addToFieldOver(coordinateBuild,
                                build);
                    }

                }
            }

        }

        return false;
    }

    // The field can not be more that 5x5x4(logical)
    private boolean checkInputofCoordinate(Coordinate coordinate) {
        if (coordinate != null) {

            final int tempOx = coordinate.getOx();
            final int tempOy = coordinate.getOy();
            final int tempOz = coordinate.getOz();
            final boolean tempCheckOx = tempOx >= 0
                    && tempOx < this.struckture.getCoordinate().getOx();
            final boolean tempCheckOy = tempOy >= 0
                    && tempOy < this.struckture.getCoordinate().getOy();
            final boolean tempCheckOz = tempOz >= 0
                    && tempOz < this.struckture.getCoordinate().getOz();
            if (tempCheckOx && tempCheckOy && tempCheckOz) {
                return true;
            }
        }
        return false;

    }

    /**
     * Init the game
     * 
     * @param coordinate
     *            where the figur that should be inizialized
     * @param figure
     *            of the player
     * @return true if successfully has been initialized
     */

    boolean initGame(final Coordinate coordinate, final GamingFigure figure) {
        if (checkInputofCoordinate(coordinate)
                && isOccupiedByAnotherPlayer(coordinate)) {
            if (figure.getNickname().matches("[0-9a-z]+")) {
                this.struckture.addToFieldOver(coordinate, figure);
                this.coordinateDest = coordinate;
                return true;
            } else {
                Terminal.printLine(ERROR_MSG_FALSE_INPUT
                        + " Name should match pattern[0-9a-z]+");
            }

        }

        return false;
    }

    private boolean checkMoveRule(final Coordinate coordinateSend,
            final Coordinate coordinateDest) {
        final int tempOxSend = coordinateSend.getOx();
        final int tempOySend = coordinateSend.getOy();
        final int tempOxDest;
        final int tempOyDest;
        try {
            tempOxDest = coordinateDest.getOx();
            tempOyDest = coordinateDest.getOy();

        } catch (NullPointerException e) {
            return false;
        }
        // Only 1 position
        if (Math.abs(tempOxDest - tempOxSend) == 1
                || (Math.abs(tempOySend - tempOyDest) == 1)) {
            if (Math.abs(tempOxDest - tempOxSend) > 1
                    || Math.abs(tempOyDest - tempOySend) > 1) {
                return false;
            }

            return true;

        }

        return false;
    }

    // the rules climb/down
    private boolean checkMoveOzRule(final Coordinate coordinateSend,
            final Coordinate coordinateDest) {

        final int tempHeightSend = this.struckture
                .getSizeOfColumn(coordinateSend) - 1;

        final int tempHeightDest = this.struckture
                .getSizeOfColumn(coordinateDest);

        if (tempHeightSend < 0) {
            return false;
        }
        // Climbing
        if (tempHeightDest > tempHeightSend) {
            // only 1 position over
            if (tempHeightDest - tempHeightSend == 1) {
                return true;
            }
            // get down or action on the flat surface
        } else if (tempHeightDest <= tempHeightSend) {
            return true;
        }

        return false;
    }

    // checks availability of another player
    private boolean isOccupiedByAnotherPlayer(final Coordinate coordinate) {
        final int tempOx = coordinate.getOx();
        final int tempOy = coordinate.getOy();

        for (int oZ = 0; oZ < this.struckture.getSizeOfColumn(coordinate); oZ++) {
            if (this.struckture.getElementFromField(new Coordinate(tempOx,
                    tempOy, oZ)) instanceof GamingFigure) {
                return false;
            }
        }

        return true;
    }

    // tower check
    private boolean checkForTower(final Coordinate coordinate) {
        final int tempOx = coordinate.getOx();
        final int tempOy = coordinate.getOy();
        int tempCntOfCuboids = 0;
        for (int oZ = 0; oZ < this.struckture.getSizeOfColumn(coordinate); oZ++) {
            if (this.struckture
                    .getElementFromField(new Coordinate(tempOx, tempOy, oZ))
                    .getName().equals("C")) {
                tempCntOfCuboids++;
            }
        }
        return tempCntOfCuboids == 3;

    }

    /**
     * @return to string
     */
    @Override
    public String toString() {

        return this.coordinateDest.toString();
    }

}
