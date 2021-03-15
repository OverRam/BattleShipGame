package battleship.Game;

import java.util.Scanner;

public class StarterBattleShipsGamePvP extends BattleShipsGame {
    private final BattleShipsGame playerOne = new BattleShipsGame();
    private final BattleShipsGame playerTwo = new BattleShipsGame();
    private final Scanner sc = new Scanner(System.in);

    // F3 F7 XX A1 D1 J10 J8 B9 D9 I2 J2

    public void run() {
        playersFillsField();

        playerOne.setOponentField(playerTwo.battleFiled);
        playerTwo.setOponentField(playerOne.battleFiled);

        int player = 0;
        while (playerTwo.isNotEndGame() & playerTwo.isNotEndGame()) {
            if (playerTwo.isNotEndGame() & playerOne.isNotEndGame()) {
                playerOneScreen();
                playerOne.shoot();
                ++player;
            }
            if (playerTwo.isNotEndGame() & playerOne.isNotEndGame()) {
                changePlayer();
            }
            if (playerTwo.isNotEndGame() & playerOne.isNotEndGame()) {
                playerTwoScreen();
                playerTwo.shoot();
                ++player;
            }
            if (playerTwo.isNotEndGame() & playerOne.isNotEndGame()) {
                changePlayer();
            }
        }
        System.out.println("Player " + (player % 2 + 1) + " win!");
    }

    private void changePlayer() {
        System.out.println("Press Enter and pass the move to another player\n");
        sc.nextLine();
    }

    private void playersFillsField() {
        System.out.println("Player 1, place your ships on the game field\n");
        playerOne.prepareFieldToGame();
        System.out.println("\nPress Enter and pass the move to another player\n");
        sc.nextLine();

        System.out.println("Player 2, place your ships on the game field\n");
        playerTwo.prepareFieldToGame();
        System.out.println("\nPress Enter and pass the move to another player\n");
        sc.nextLine();
    }

    private void playerOneScreen() {
        playerOne.printField(FieldToPrint.FOG_OF_WAR);
        System.out.print("---------------------\n");
        playerOne.printField(FieldToPrint.BATTLE_FIELD);
        System.out.println("\nPlayer 1, it's your turn:\n");
    }

    private void playerTwoScreen() {
        playerTwo.printField(FieldToPrint.FOG_OF_WAR);
        System.out.print("---------------------\n");
        playerTwo.printField(FieldToPrint.BATTLE_FIELD);
        System.out.println("\nPlayer 2, it's your turn:\n");
    }

}
