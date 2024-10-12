package Battleship;

import java.util.Scanner;

import static Battleship.FieldSymbols.*;

class Player {

    private final String name;
    GameField gameField;
    GameField fogGameField;

    Player(String name) {
        this.name = name;
        gameField = new GameField();
        fogGameField = new GameField();
    }

    String getName() {
        return name;
    }

    Coordinate takeAShot(Player currentPlayer, Player opponentPlayer) {
        boolean isShot = false;
        Scanner scanner = new Scanner(System.in);
        while (!isShot) {

            try {
                String shot = scanner.nextLine().toUpperCase();

                //Validate the coordinates entered for a shot
                if (shot.length() < 2 || shot.length() > 3 || !Character.isAlphabetic(shot.charAt(0)) ||
                        !Character.isDigit(shot.charAt(1))) {
                    if (shot.length() > 2 && !Character.isDigit(shot.charAt(2))) {
                        System.err.println("Error! Enter the coordinates in the correct format. Example: 'A1' ");
                        continue;
                    }
                    System.err.println("Error! Enter the coordinates in the correct format. Example: 'A1' ");
                    continue;
                }

                //Convert the user entered coordinates into indices
                int rowCoordinate = shot.charAt(0) - 'A';
                int columnCoordinate = Integer.parseInt(shot.substring(1)) - 1;

                //Check if coordinates are in bound
                if (rowCoordinate < 0 || columnCoordinate < 0 ||
                        rowCoordinate > opponentPlayer.gameField.getField().length - 1 ||
                        columnCoordinate > opponentPlayer.gameField.getField().length - 1) {
                    System.err.println("Error! You entered the wrong coordinates! Try again:");
                    continue;
                }

                //If a ship is hit, mark the coordinate with 'HIT' symbol
                if (opponentPlayer.gameField.getField()[rowCoordinate][columnCoordinate] == OCCUPIED_BY_SHIP.getSYMBOL()) {
                    opponentPlayer.gameField.setField(rowCoordinate, columnCoordinate, HIT.getSYMBOL());
                    return new Coordinate(rowCoordinate, columnCoordinate);
                    //If missed, mark the coordinate with 'MISS' symbol
                } else if (opponentPlayer.gameField.getField()[rowCoordinate][columnCoordinate] == FOG_OF_WAR.getSYMBOL()) {
                    opponentPlayer.gameField.setField(rowCoordinate, columnCoordinate, MISS.getSYMBOL());
                    System.out.println("You missed!\n");
                    return new Coordinate(rowCoordinate, columnCoordinate);
                } else if (opponentPlayer.gameField.getField()[rowCoordinate][columnCoordinate] == MISS.getSYMBOL() ||
                        opponentPlayer.gameField.getField()[rowCoordinate][columnCoordinate] == HIT.getSYMBOL()) {
                    System.out.println("You already shot here!\n");
                    return new Coordinate(rowCoordinate, columnCoordinate);
                }
            } catch (NumberFormatException e) {
                System.err.println("Error! Enter the coordinates in the correct format. Example: 'A1' ");
            }
        }
        return null;
    }
}

