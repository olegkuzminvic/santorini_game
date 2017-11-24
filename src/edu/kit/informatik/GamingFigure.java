package edu.kit.informatik;

/**
 * This class created gaming figures
 * 
 * @author Oleh Kuzmin
 * @version 1.0
 */
final class GamingFigure extends GameObject {
    private String     nickname;
    private Coordinate coordinate;

    /**
     * 
     */
    public GamingFigure() {

    }

    /**
     * 
     * @param figure
     *            figure
     * @param nickname
     *            name
     */
    public GamingFigure(final GamingFigureEnum figure, final String nickname) {
        if (figure == GamingFigureEnum.CUBE) {
            new GamingFigure();
            this.setName("Cube");
            this.nickname = nickname;

        } else if (figure == GamingFigureEnum.CYLINDER) {
            new GamingFigure();
            this.setName("Cylinder");
            this.nickname = nickname;
        }

    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * @param nickname
     *            the nickname to set
     */
    public void setNickname(final String nickname) {

        this.nickname = nickname;

    }

    @Override
    public String toString() {
        return String.format("%s", this.nickname);
    }

    /**
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    /**
     * @param coordinate
     *            the coordinate to set
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
