package edu.kit.informatik;

/**
 * @author Oleh Kuzmin
 * @version 1.0
 *
 */
public interface Playable {

    boolean initGame(Coordinate coordinateFirstFig, GamingFigure figureFirst,
            Coordinate coordinateSecFig, GamingFigure figureSec);

    boolean move(String nickname, Coordinate coordinateDest);

    boolean buildElement(Coordinate coordinate, BuildElement build);

}
