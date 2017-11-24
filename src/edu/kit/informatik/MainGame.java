package edu.kit.informatik;

/**
 * @author Oleh Kuzmin
 * @version 1.0
 */
final class MainGame {

    private static GameField    field;
    private static GameAction   action;

    private static String[]     globalArgs;

    private static Playable     player1;
    private static Playable     player2;
    // true- Player 2,false - Player1
    private static boolean      timeToSwitch             = false;

    private static final String CMD_MOVE                 = "move";
    private static final String CMD_BUILD                = "build";
    private static final String CMD_TURN                 = "turn";
    private static final String CMD_CELL_PRINT           = "cellprint";
    private static final String CMD_PRINT                = "print";
    private static final String CMD_QUIT                 = "quit";

    private static boolean      checkCommandBuild        = false;
    private static boolean      checkCommandMove         = false;
    private static int          signalCnt                = 1;

    private static String       nickPlay2Sec;

    private static final String ERROR_MSG_EMPTY_INPUT    = "Error, your input is empty!";
    private static final String ERROR_MSG_FALSE_INPUT    = "Error, your input is false!";
    private static final String ERROR_MSG_FALSE_INIT     = "Error, initialisation is failed";
    private static final String ERROR_MSG_RULES_FALSE    = "Error, infringement rules of game!";
    private static final String ERROR_MSG_RULES_MOVEMENT = "Error, player must move and build!";

    private static final String NOTIFICATION_MSG_OK      = "OK";
    private static final String NOTIFIKATION_MSG_PLAYER1 = "P1";
    private static final String NOTIFIKATION_MSG_PLAYER2 = "P2";
    private static final String NOTIFIKATION_MSG_EMPTY   = "Empty";

    /**
     * Private constructor to avoid object generation.
     */
    private MainGame() {

    }

    private static boolean isQuitCommand(final String command) {
        return command.equals(CMD_QUIT);
    }

    /**
     * parse input
     * 
     * @param input
     *            input
     */
    private static void parseLine(final String input) {

        String tempInput = input.trim();

        if (tempInput.isEmpty()) {

            Terminal.printLine(ERROR_MSG_EMPTY_INPUT);

        } else {
            // 2 splitted string for simplify by parsing
            String[] splittedInput = tempInput.split(" ");
            String[] splittedInputSemikolon = tempInput.split(";");

            switch (splittedInput[0]) {
                case CMD_MOVE:
                    checkCommandMove = initCommandMove(splittedInputSemikolon);
                    if (checkCommandMove) {
                        Terminal.printLine(NOTIFICATION_MSG_OK);
                    } else {
                        Terminal.printLine(
                                ERROR_MSG_RULES_FALSE + "(by moving)");
                    }

                    break;
                case CMD_BUILD:

                    checkCommandBuild = initCommandBuild(
                            splittedInputSemikolon);

                    if (checkCommandBuild) {
                        Terminal.printLine(NOTIFICATION_MSG_OK);
                    } else
                        Terminal.printLine(
                                ERROR_MSG_RULES_FALSE + "(by building)");
                    break;

                case CMD_TURN:

                    if (checkCommandMove && checkCommandBuild) {

                        signalCnt++;
                        if (signalCnt % 2 == 0) {
                            timeToSwitch = true;
                            checkCommandMove = false;
                            checkCommandBuild = false;
                            Terminal.printLine(NOTIFIKATION_MSG_PLAYER2);
                        } else {
                            checkCommandMove = false;
                            timeToSwitch = false;
                            checkCommandBuild = false;
                            Terminal.printLine(NOTIFIKATION_MSG_PLAYER1);
                        }
                    } else {
                        Terminal.printLine(ERROR_MSG_RULES_MOVEMENT);
                    }

                    break;
                case CMD_CELL_PRINT:
                    boolean checkCommandCellPrint = initCommandCellPrint(
                            splittedInput);
                    if (!checkCommandCellPrint) {
                        Terminal.printLine(ERROR_MSG_FALSE_INPUT);
                    }
                    break;
                case CMD_PRINT:
                    initCommandPrint();

                    break;

                default:
                    Terminal.printLine(
                            ERROR_MSG_FALSE_INPUT + " (" + splittedInput[0]
                                    + " not supported or not found)");

                    break;

            }

        }
    }

    /**
     * init Players
     */
    static void initPlayers() {
        player2 = new Player(action, "Player2");
        player1 = new Player(action, "Player1");
    }

    static boolean initCommandBuild(String[] splittedInputSemikolon) {
        if (splittedInputSemikolon.length == 3) {

            Coordinate coordinate = null;
            try {
                coordinate = new Coordinate(
                        Integer.parseInt(splittedInputSemikolon[2]),
                        Integer.parseInt(splittedInputSemikolon[1]));
            } catch (NumberFormatException e) {
                Terminal.printLine(ERROR_MSG_FALSE_INPUT);
                return false;
            }
            String typeOfBuildElement = splittedInputSemikolon[0]
                    .replace("build", "").trim();
            if (timeToSwitch) {
                if (typeOfBuildElement.equals("C")) {
                    return player2.buildElement(coordinate,
                            new BuildElement(BuildElementEnum.CUBOID));

                } else if (typeOfBuildElement.equals("D")) {
                    return player2.buildElement(coordinate,
                            new BuildElement(BuildElementEnum.DOME));
                }
            } else {
                if (typeOfBuildElement.equals("C")) {
                    return player1.buildElement(coordinate,
                            new BuildElement(BuildElementEnum.CUBOID));

                } else if (typeOfBuildElement.equals("D")) {
                    return player1.buildElement(coordinate,
                            new BuildElement(BuildElementEnum.DOME));
                }
            }

        }
        return false;

    }

    static boolean initCommandMove(String[] splittedInputSemikolon) {

        if (splittedInputSemikolon.length == 3) {
            String nickname = splittedInputSemikolon[0].replace("move", "")
                    .trim();

            Coordinate coordinateDest = null;
            try {
                coordinateDest = new Coordinate(
                        Integer.parseInt(splittedInputSemikolon[2]),
                        Integer.parseInt(splittedInputSemikolon[1]));
            } catch (NumberFormatException e) {
                Terminal.printLine(ERROR_MSG_FALSE_INPUT);
                return false;
            }
            if (timeToSwitch) {
                return player2.move(nickname, coordinateDest);
            } else {
                return player1.move(nickname, coordinateDest);
            }

        }

        return false;
    }

    /**
     * Init command Cellprint
     * 
     * @param splittedInput
     *            input
     * @return true if successfully
     */
    static boolean initCommandCellPrint(String[] splittedInput) {
        if (splittedInput.length == 2) {
            String tempCoordinate = splittedInput[1].trim();
            String tempCoordinates[] = tempCoordinate.split(";");
            if (tempCoordinates.length == 2) {
                Coordinate coordinate = null;
                try {
                    coordinate = new Coordinate(
                            Integer.parseInt(tempCoordinates[1]),
                            Integer.parseInt(tempCoordinates[0]));
                } catch (NumberFormatException e) {
                    return false;
                }

                Object[] temp = field.cellPrint(coordinate);
                if (temp.length == 0) {
                    Terminal.printLine(NOTIFIKATION_MSG_EMPTY);
                }
                for (int i = 0; i < temp.length; i++) {
                    Terminal.printLine(temp[i].toString().trim());

                }
                return true;
            }

        }
        return false;

    }

    /**
     * Print
     */
    static void initCommandPrint() {
        Terminal.printLine(field.printStruckture());
    }

    /**
     * Init Game
     */
    static void initGame() {

        field = new GameField(new Coordinate(5, 5, 4));
        action = new GameAction(field);

    }

    /**
     * 
     * @param cordPlay1First
     *            Coodinate of the first figure(first player)
     * @param nickPlay1First
     *            Nickname of the first figure (first player)
     * @param cordPlay1Sec
     *            Coodinate of the second figure(first player)
     * @param nickPlay1Sec
     *            Nickname of the second figure(first player)
     * @param coorPlay2First
     *            Coodinate of the first figure(second player)
     * @param nickPlay2First
     *            Nickname of the figure(second player)
     * @param coorPlay2Sec
     *            Coordinate of the second figure (second player)
     * 
     *            * @return true if correct
     */
    static boolean initGameFigures(Coordinate cordPlay1First,
            String nickPlay1First, Coordinate cordPlay1Sec, String nickPlay1Sec,
            Coordinate coorPlay2First, String nickPlay2First,
            Coordinate coorPlay2Sec) {

        final GamingFigureEnum typeFig1Player = GamingFigureEnum.CUBE;
        final GamingFigureEnum typeFig2Player = GamingFigureEnum.CYLINDER;

        final boolean checkInitFirst = player1.initGame(cordPlay1First,
                new GamingFigure(typeFig1Player, nickPlay1First), cordPlay1Sec,
                new GamingFigure(typeFig1Player, nickPlay1Sec));

        final boolean checkInitSec = player2.initGame(coorPlay2First,
                new GamingFigure(typeFig2Player, nickPlay2First), coorPlay2Sec,
                new GamingFigure(typeFig2Player, nickPlay2Sec));
        if (!checkInitFirst || !checkInitSec) {
            Terminal.printLine(ERROR_MSG_FALSE_INIT);
            return false;
        }

        return checkInitFirst && checkInitSec;
    }

    /**
     * Init start figures
     * 
     * @return true if sucessfully
     */

    static boolean initFigures() {
        if (globalArgs.length != 4) {
            Terminal.printLine(ERROR_MSG_FALSE_INPUT + " " + globalArgs.length
                    + "(expected 4)");
            return false;
        }

        String[] tempPars1 = globalArgs[0].split(";");
        String[] tempPars2 = globalArgs[1].split(";");
        String[] tempPars3 = globalArgs[2].split(";");
        String[] tempPars4 = globalArgs[3].split(";");

        Coordinate cordPlay1First = null;
        Coordinate cordPlay1Sec = null;
        Coordinate coorPlay2First = null;
        Coordinate coorPlay2Sec = null;

        String nickPlay1First = tempPars1[0].trim();
        String nickPlay1Sec = tempPars2[0].trim();
        String nickPlay2First = tempPars3[0].trim();
        String nickPlay2Sec = tempPars4[0].trim();
        if (!nickPlay1First.matches("[0-9a-z]+")) {
            Terminal.printLine(ERROR_MSG_FALSE_INPUT
                    + " Name should match pattern[0-9a-z]+" + " ("
                    + nickPlay1First + ")");
            return false;
        }
        if (!nickPlay1Sec.matches("[0-9a-z]+")) {
            Terminal.printLine(ERROR_MSG_FALSE_INPUT
                    + " Name should match pattern[0-9a-z]+" + " ("
                    + nickPlay1Sec + ")");
            return false;
        }
        if (!nickPlay2First.matches("[0-9a-z]+")) {
            Terminal.printLine(ERROR_MSG_FALSE_INPUT
                    + " Name should match pattern[0-9a-z]+" + " ("
                    + nickPlay2First + ")");
            return false;
        }
        if (!nickPlay2Sec.matches("[0-9a-z]+")) {
            Terminal.printLine(
                    ERROR_MSG_FALSE_INPUT + " Name should match pattern[0-9a-z]"
                            + " (" + nickPlay2Sec + ")");
            return false;
        }

        try {
            cordPlay1First = new Coordinate(Integer.parseInt(tempPars1[2]),
                    Integer.parseInt(tempPars1[1]));

            cordPlay1Sec = new Coordinate(Integer.parseInt(tempPars2[2]),
                    Integer.parseInt(tempPars2[1]));

            coorPlay2First = new Coordinate(Integer.parseInt(tempPars3[2]),
                    Integer.parseInt(tempPars3[1]));

            coorPlay2Sec = new Coordinate(Integer.parseInt(tempPars4[2]),
                    Integer.parseInt(tempPars4[1]));
        } catch (NumberFormatException e) {
            Terminal.printLine(ERROR_MSG_FALSE_INPUT);
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            Terminal.printLine(ERROR_MSG_FALSE_INPUT);
            return false;
        }

        return initGameFigures(cordPlay1First, nickPlay1First, cordPlay1Sec,
                nickPlay1Sec, coorPlay2First, nickPlay2First, coorPlay2Sec);
    }

    /**
     * d
     * 
     * @param args
     *            d
     */
    public static void main(String[] args) {
        globalArgs = args;
        String[] tempPars4 = globalArgs[3].split(";");
        nickPlay2Sec = tempPars4[0].trim();

        initGame();
        initPlayers();
        initFigures();

        String input = Terminal.readLine();
        while (!isQuitCommand(input)) {

            parseLine(input);
            input = Terminal.readLine();

        }

    }
}
