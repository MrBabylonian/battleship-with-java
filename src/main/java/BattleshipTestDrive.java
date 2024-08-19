public class BattleshipTestDrive {

    public static void main(String[] args) {
        GameField gameField = new GameField();
        System.out.println(gameField);
        gameField.placeShips();
        System.out.println(gameField);
        gameField.placeShips();
        System.out.println(gameField);

    }
}
