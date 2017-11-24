/**
 * 
 */
package edu.kit.informatik;

/**
 * @author Oleh Kuzmin
 * @version 1.0
 */
public class Player implements Playable {

    private String       name;
    private GameAction   action;
    // saves time coordinates movement
    private GamingFigure figureFirst;
    private GamingFigure figureSecond;

    /**
     * 
     * @param action
     *            action
     * @param name
     *            name
     */
    public Player(final GameAction action, final String name) {
        this.action = action;
        this.name = name;

    }

    @Override
    public boolean move(final String nickname, final Coordinate coordinateDest) {
        try {
            if (this.figureFirst.getNickname().equals(nickname)) {
                if (action.moveFigureTo(this.figureFirst.getCoordinate(),
                        coordinateDest)) {
                    this.figureFirst.setCoordinate(coordinateDest);
                    return true;
                }

            } else if (this.figureSecond.getNickname().equals(nickname)) {
                if (action.moveFigureTo(this.figureSecond.getCoordinate(),
                        coordinateDest)) {
                    this.figureSecond.setCoordinate(coordinateDest);
                    return true;
                }

            }
        } catch (NullPointerException e) {
            return false;
        }

        return false;

    }

    @Override
    public boolean buildElement(final Coordinate coordinate,
            final BuildElement build) {

        return action.buildElement(coordinate, build);
    }

    /**
     * @param coordinateFirstFig
     *            coordinate of first figure
     * @param figureFirst
     *            first figure
     * @param coordinateSecFig
     *            coodinate of second figure
     * @param figureSec
     *            first figure
     * @return true if correcctly
     */
    @Override
    public boolean initGame(final Coordinate coordinateFirstFig,
            final GamingFigure figureFirst, final Coordinate coordinateSecFig,
            final GamingFigure figureSec) {
        final boolean checkFirts = this.action.initGame(coordinateFirstFig,
                figureFirst);
        final boolean checkSec = this.action.initGame(coordinateSecFig,
                figureSec);
        if (checkSec && checkFirts) {
            this.figureFirst = figureFirst;
            this.figureSecond = figureSec;
            figureFirst.setCoordinate(coordinateFirstFig);
            figureSec.setCoordinate(coordinateSecFig);

            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.name;
    }
}
