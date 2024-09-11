package Battleship;

import java.util.Scanner;

public class Game {
    private final GameField gameField;
    private final Scanner scanner;

    public Game() {
        gameField = new GameField();
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
            } else if (choice.equals("2")) {
                gameRunning = false;
            } else {
                System.out.println("Invalid choice, please try again");
            }

        }
    }
}