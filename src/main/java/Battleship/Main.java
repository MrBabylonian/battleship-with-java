package Battleship;

public class Main {

    public static void main(String[] args) {
        GameField gameField = new GameField();
        System.out.println(gameField);
        gameField.placeShips();
    }
}