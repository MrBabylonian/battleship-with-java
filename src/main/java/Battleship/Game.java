package Battleship;

import java.util.Scanner;

class Game {

    private final Player player1;
    private final Player player2;
    private static Player currentPlayer;
    private static Player opponentPlayer;

    Game() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        currentPlayer = player2;
        opponentPlayer = player1;
    }

    static Player getCurrentPlayer() {
        return currentPlayer;
    }

    static Player getOpponentPlayer() {
        return opponentPlayer;
    }

    //Switch players at each turn
    //We are starting with 'player2' as the current player to avoid 'NullPointerException',
    //'player1' will take the first shot anyway
    private void switchPlayerTurns() {
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
            opponentPlayer = player1;
        } else if (currentPlayer.equals(player2)) {
            currentPlayer = player1;
            opponentPlayer = player2;
        }
    }

    private void gameEngine() {
        Scanner scanner = new Scanner(System.in);
        boolean isPlayer2PlacedTheirShips = false;

        System.out.printf("%s, place your ships on the game field", player1.getName());
        System.out.println();
        System.out.println(player1.gameField);
        //player1 places their ships
        player1.gameField.placeShips();
        while (!isPlayer2PlacedTheirShips) {
            System.out.println("Press Enter and pass the move to another player");
            String input = scanner.nextLine();
            if (input.equals("")) {
                System.out.printf("%s place your ships on the game field", player2.getName());
                System.out.println("\n");
                System.out.println(player2.gameField);
                //player2 places their ships
                player2.gameField.placeShips();
                isPlayer2PlacedTheirShips = true;
            }
        }
        //Keep the game running as long as there are still ships alive on the field
        while (!player1.gameField.getShipsOnField().isEmpty() &&
                !player2.gameField.getShipsOnField().isEmpty()) {
            System.out.println("Press Enter and pass the move to another player");
            System.out.println("...");
            String input = scanner.nextLine();
            if (input.equals("")) {
                //Switch players
                switchPlayerTurns();
                //Prints the field of the player being shot at
                System.out.println(opponentPlayer.fogGameField);
                System.out.println("--------------------- \n");
                //Prints the field of the player who's currently shooting
                System.out.println(currentPlayer.gameField);
                System.out.printf("%s, it's your turn: %n", currentPlayer.getName());
            } else {
                continue;
            }
            //takeAShot() returns the coordinates of the shot and it is being stored
            Coordinate shootingCoordinate = currentPlayer.takeAShot(currentPlayer, opponentPlayer); // Store coordinates of the shot
            for (int i = 0; i < opponentPlayer.gameField.getShipsOnField().size(); i++) {
                //If it is a 'hit'
                // remove the 'shootingCoordinate'
                // from the individual ship's coordinates who just got shot
                if (opponentPlayer.gameField.getShipsOnField().get(i).getShipCoordinates().removeIf(shootingCoordinate::equals)) {
                    opponentPlayer.gameField.getShipsOnField().trimToSize();
                    //Print the following message as long as the 'opponentPlayer' still has ships alive on the field
                    if (!opponentPlayer.gameField.getShipsOnField().isEmpty()) {
                        //Print out a message if a ship is hit
                        System.out.println("You hit a ship!\n");
                    }
                }
                //Remove the ship from 'shipsOnField' if it is sunk
                opponentPlayer.gameField.getShipsOnField().get(i).checkSunk(opponentPlayer.gameField.getShipsOnField());
            }
        }
        //Close the 'System.in' flow to avoid leaks before the program terminates
        scanner.close();
    }

    void start() {
        gameEngine();
    }
}

