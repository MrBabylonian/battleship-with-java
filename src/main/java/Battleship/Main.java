package Battleship;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.gameField);
        game.gameField.placeShips();
        game.takeAShot();
    }
}