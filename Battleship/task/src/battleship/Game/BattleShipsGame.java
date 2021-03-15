package battleship.Game;

import java.util.Arrays;
import java.util.Scanner;

class BattleShipsGame {
    private static final int SIZE_BATTLE_FIELD = 10;
    private final Scanner SC = new Scanner(System.in);
    private final int[] SINGLE_COORDINATES = new int[2];
    private final char[][] fogOfWar = createBattleFiled();
    protected char[][] battleFiled;
    private int inpShipLength;
    private int cellsToSank = 17;
    private int[] coordinatesOFShip;
    private char[][] oponentField;


    protected void setOponentField(char[][] field) {
        oponentField = field;
    }

    protected void prepareFieldToGame() {
        battleFiled = createBattleFiled();
        printField(battleFiled);
        makeShips();
    }

    protected void shoot() {
        int[] shoot = getInputHandler();
        while (battleFiled[0][1] != '~') {
            System.out.println("Error! You shoot here before try again");
            shoot = getInputHandler();
        }
        isGoodPlayerShoot(shoot);
    }

    protected void isGoodPlayerShoot(int[] shoot) {
        boolean isHit = isHitOnFogOfWar(fogOfWar, oponentField);
        cellsToSank -= isHit ? 1 : 0;
        if (cellsToSank == 0) {
            System.out.println("You sank the last ship. You won. Congratulations!");
        } else {
            if (isSankShip(shoot) && cellsToSank > 0) {
                System.out.println("\nYou sank a ship! Specify a new target:");
            } else if (cellsToSank > 0) {
                System.out.println("\nYou " + (isHit ? "hit a ship!" : "missed!"));
            }
        }
    }

    protected boolean isNotEndGame() {
        return cellsToSank > 0;
    }

    private boolean isHitOnFogOfWar(char[][] fogOfWarToMark, char[][] OponentField) {
        boolean isHit = OponentField[SINGLE_COORDINATES[0]][SINGLE_COORDINATES[1]] == 'O';
        OponentField[SINGLE_COORDINATES[0]][SINGLE_COORDINATES[1]] = isHit ? 'X' : 'M';
        fogOfWarToMark[SINGLE_COORDINATES[0]][SINGLE_COORDINATES[1]] = isHit ? 'X' : 'M';
        return isHit;
    }

    private boolean isSankShip(int[] cellShoot) {              //after check good coordinate
        int letter = cellShoot[0];
        int number = cellShoot[1];

        if (number + 1 > battleFiled.length) {
            if (battleFiled[letter][number + 1] == 'O') {
                return false;
            }
        }

        if (number - 1 >= 0) {
            if (battleFiled[letter][number - 1] == 'O') {
                return false;
            }
        }

        if (letter + 1 < battleFiled.length) {
            if (battleFiled[letter + 1][number] == 'O') {
                return false;
            }
        }

        if (letter - 1 >= 0) {
            return battleFiled[letter - 1][number] != 'O';
        }
        return true;
    }

    private void makeShips() {
        Ships[] ships = Ships.values();
        for (int i = 0; i < 5; i++) {
            System.out.println("\nEnter the coordinates of the " + ships[i].getName()
                    + " (" + ships[i].getLengthShip() + " cells):");
            boolean isOK = true;
            while (isOK) {
                setCoordinatesShip();
                if (coordinatesOFShip[0] == coordinatesOFShip[2] || coordinatesOFShip[1] == coordinatesOFShip[3]) {
                    inpShipLength = getOrientationShip();
                    while (inpShipLength != ships[i].getLengthShip()) {
                        System.out.println("Error! Wrong length of the Submarine! Try again:\n");
                        setCoordinatesShip();
                        inpShipLength = getOrientationShip();
                    }
                    isOK = !isGoodPlaceForShips();
                    if (!isOK) {
                        MarkShipPositionOnBattlefield();
                        System.out.println();
                        printField(battleFiled);
                    }
                } else {
                    System.out.println("Error! Wrong ship location! Try again:\n");
                }
            }
        }
    }

    private int getOrientationShip() {
        if (coordinatesOFShip[0] == coordinatesOFShip[2]) {
            return Math.abs(coordinatesOFShip[1] - coordinatesOFShip[3]) + 1;
        } else {
            return Math.abs(coordinatesOFShip[0] - coordinatesOFShip[2]) + 1;
        }
    }

    private int[] oderCoordinates() {
        if (coordinatesOFShip[0] == coordinatesOFShip[2]) {                                 //horizontal coordinates
            return new int[]{coordinatesOFShip[0], (Math.max(coordinatesOFShip[1], coordinatesOFShip[3]))};
        } else {                                                                            //vertical coordinates
            return new int[]{Math.max(coordinatesOFShip[0], coordinatesOFShip[2]), coordinatesOFShip[1]};
        }
    }

    private void MarkShipPositionOnBattlefield() {
        int[] coordinatesToMark = oderCoordinates();
        if (coordinatesOFShip[0] == coordinatesOFShip[2]) {                                 //horizontal
            for (int i = 0; i < inpShipLength; i++) {
                battleFiled[coordinatesOFShip[0]][coordinatesToMark[1] - i] = 'O';
            }
        } else {                                                                             //vertical
            for (int i = 0; i < inpShipLength; i++) {
                battleFiled[coordinatesToMark[0] - i][coordinatesOFShip[1]] = 'O';
            }
        }
    }

    private boolean isGoodPlaceForShips() {
        int min;
        if (coordinatesOFShip[0] == coordinatesOFShip[2]) {                                  //horizontal Check
            for (int i = 0; i < inpShipLength + 2; i++) {
                try {
                    min = Math.min(coordinatesOFShip[1], coordinatesOFShip[3]);
                    if (battleFiled[coordinatesOFShip[0] + 1][min - 1 + i] != '~' ||
                            battleFiled[coordinatesOFShip[0]][min - 1 + i] != '~' ||
                            battleFiled[coordinatesOFShip[0] - 1][min - 1 + i] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        } else {                                                                 //check vertical
            min = Math.min(coordinatesOFShip[0], coordinatesOFShip[2]);
            for (int i = 0; i < inpShipLength + 2; i++) {
                try {
                    if (battleFiled[min + i - 1][coordinatesOFShip[1] + 1] != '~' ||
                            battleFiled[min + i - 1][coordinatesOFShip[1]] != '~' ||
                            battleFiled[min + i - 1][coordinatesOFShip[1] - 1] != '~') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        }
        return true;
    }

    private void setCoordinatesShip() {
        coordinatesOFShip = new int[4];
        getInputHandler();
        coordinatesOFShip[0] = SINGLE_COORDINATES[0];
        coordinatesOFShip[1] = SINGLE_COORDINATES[1];
        getInputHandler();
        coordinatesOFShip[2] = SINGLE_COORDINATES[0];
        coordinatesOFShip[3] = SINGLE_COORDINATES[1];
    }

    private int[] getInputHandler() {
        boolean isGoodCell = true;
        String inp;
        while (isGoodCell) {
            try {
                inp = SC.next().toUpperCase().trim();
                SINGLE_COORDINATES[0] = inp.charAt(0) - 64 - 1;
                SINGLE_COORDINATES[1] = Integer.parseInt(inp.substring(1)) - 1;
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Error! You entered the wrong coordinates! Wrong parse Try again:");
            }
            if (SINGLE_COORDINATES[0] < SIZE_BATTLE_FIELD && SINGLE_COORDINATES[1] < SIZE_BATTLE_FIELD) {
                isGoodCell = false;
            } else {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }
        }
        return SINGLE_COORDINATES;
    }

    private char[][] createBattleFiled() {

        char[][] createBattleFiled = new char[SIZE_BATTLE_FIELD][SIZE_BATTLE_FIELD];
        for (char[] chars : createBattleFiled) {
            Arrays.fill(chars, '~');
        }
        return createBattleFiled;
    }

    private void printField(char[][] battleFieldToPrint) {
        System.out.print(" ");
        for (int i = 0; i < SIZE_BATTLE_FIELD; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println();
        for (int i = 0; i < SIZE_BATTLE_FIELD; i++) {
            for (int j = 0; j < SIZE_BATTLE_FIELD; j++) {
                if (j == 0) {
                    System.out.printf("%s%2s", (char) ('A' + i), battleFieldToPrint[i][j]);
                } else {
                    System.out.printf("%2s", battleFieldToPrint[i][j]);
                }
            }
            System.out.println();
        }
    }

    protected void printField(FieldToPrint fieldToPrint) {
        switch (fieldToPrint) {
            case FOG_OF_WAR:
                printField(fogOfWar);
                break;
            case BATTLE_FIELD:
                printField(battleFiled);
                break;
        }
    }
}