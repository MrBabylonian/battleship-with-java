package Battleship;

import java.util.Scanner;
import static Battleship.FieldSymbols.*;

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

        boolean gameRunning = true;
        System.out.println("Welcome to the Battleship!");

        while (gameRunning) {

            System.out.println(gameField);
            System.out.println("Choose an option");
            System.out.println("1. Place Ships");
            System.out.println("2. Quit Game");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                gameField.placeShips();
                System.out.println(gameField);
                takeAShot();
            } else if (choice.equals("2")) {
                gameRunning = false;
            } else {
                System.out.println("Invalid choice, please try again");
            }

        }
    }

    public void takeAShot() {
        boolean isShot = false;

        System.out.println("The game starts!");

        System.out.println(fogGameField);

        while (!isShot) {

            System.out.println("Take a shot!");
            String shot  = scanner.nextLine().toUpperCase();

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

            if (gameField.field[rowCoordinate][columnCoordinate] == OCCUPIED_BY_SHIP.getSymbol()) {
                gameField.field[rowCoordinate][columnCoordinate] = HIT.getSymbol();
                fogGameField.field[rowCoordinate][columnCoordinate] = HIT.getSymbol();
                isShot = true;
                System.out.println(fogGameField);
                System.out.println("You hit a ship! \n");
            } else if (gameField.field[rowCoordinate][columnCoordinate] == FOG_OF_WAR.getSymbol()) {
                gameField.field[rowCoordinate][columnCoordinate] = MISS.getSymbol();
                fogGameField.field[rowCoordinate][columnCoordinate] = MISS.getSymbol();
                isShot = true;
                System.out.println(fogGameField);
                System.out.println("You missed! \n");
            }
        }
        System.out.println(gameField);
    }
}

