package Battleship;

import java.util.Scanner;

import static Battleship.FieldSymbols.*;
import static Battleship.GameField.shipsOnField;

public class Game {

    final GameField gameField;
    final GameField fogGameField;
    private final Scanner scanner;

    public Game() {
        gameField = new GameField();
        fogGameField = new GameField();
        scanner = new Scanner(System.in);
    }

    public void start() {
        gameField.placeShips();
        System.out.println("The game starts! \n");
        System.out.println(fogGameField);
        System.out.println("Take a shot! \n");
        //Keep the game running as long as there are still ships alive on the field
        while (!shipsOnField.isEmpty()) {
            Coordinates shootingCoordinate = takeAShot(); // Store coordinates of the shot
            for (int i = 0; i < shipsOnField.size(); i++) {
                for (Ship ship : shipsOnField) {
                    if (ship.shipCoordinates.removeIf(shootingCoordinate::equals)) { //Remove the coordinate from the individual ship coordinates if it's a hit
                        ship.shipCoordinates.trimToSize();
                        //Print out a message if a ship is sunk
                        if (!ship.shipCoordinates.isEmpty()) {
                            System.out.println("You hit a ship! Try again: \n");
                        }
                    }
                }
                //Remove the ship from 'shipsOnField' if it is sunk
                shipsOnField.get(i).checkSunk();
            }
        }
        scanner.close();
    }

    public Coordinates takeAShot() {
        boolean isShot = false;

        while (!isShot) {

            String shot = scanner.nextLine().toUpperCase();

            //Validate the coordinates entered for a shot
            if (shot.length() > 2 || Character.isDigit(shot.charAt(0))
                    || Character.isAlphabetic(shot.charAt(1))) {
                System.err.println("Error! Enter the coordinates in the correct format. Example: 'A1' ");
                continue;
            }

            //Convert the user entered coordinates into indices
            int rowCoordinate = shot.charAt(0) - 'A';
            int columnCoordinate = Integer.parseInt(shot.substring(1)) - 1;


            //Check if coordinates are in bound
            if (rowCoordinate < 0 || columnCoordinate < 0 ||
                    rowCoordinate > gameField.field.length - 1 ||
                    columnCoordinate > gameField.field.length - 1) {
                System.err.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }

            //If a ship is hit, mark the coordinate with 'HIT' symbol
            if (gameField.field[rowCoordinate][columnCoordinate] == OCCUPIED_BY_SHIP.getSymbol()) {
                gameField.field[rowCoordinate][columnCoordinate] = HIT.getSymbol();
                fogGameField.field[rowCoordinate][columnCoordinate] = HIT.getSymbol();
                System.out.println(fogGameField);
                return new Coordinates(rowCoordinate, columnCoordinate);
                //If missed, mark the coordinate with 'MISS' symbol
            } else if (gameField.field[rowCoordinate][columnCoordinate] == FOG_OF_WAR.getSymbol()) {
                gameField.field[rowCoordinate][columnCoordinate] = MISS.getSymbol();
                fogGameField.field[rowCoordinate][columnCoordinate] = MISS.getSymbol();
                System.out.println(fogGameField);
                System.out.println("You missed. Try again: \n");
                return new Coordinates(rowCoordinate, columnCoordinate);
            } else if (gameField.field[rowCoordinate][columnCoordinate] == MISS.getSymbol() ||
                    gameField.field[rowCoordinate][columnCoordinate] == HIT.getSymbol()) {
                System.out.println(fogGameField);
            }
        }
        return null;
    }
}

