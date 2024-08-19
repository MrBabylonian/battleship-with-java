import java.util.Arrays;
import java.util.Scanner;

public class GameField {
    public static final char fogOfWar = '~';
    public static final char occupiedByShip = 'O';
    public static final char hit = 'X';
    public static final char miss = 'M';
    private final char[][] field = new char[10][10];

    //Fill up the game field with "Fog of war"
    public GameField() {
        for (char[] chars : field) {
            Arrays.fill(chars, fogOfWar);
        }
    }

    //Draw coordinates around the game field
    public final String toString() {
        StringBuilder builder = new StringBuilder(" ");
        for (int i = 0; i < field.length; i++) {
            builder.append(" ").append(i + 1);
        }
        builder.append("\n");
        for (int i = 0; i < field.length; i++) {
            builder.append((char) ('A' + i));
            for (int j = 0; j < field.length; j++) {
                builder.append(" ").append(field[i][j]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }


    void placeShips() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the coordinates of the ship:");
            System.out.print("> ");

            String input = scanner.nextLine();
            //If the user enters more than 2 coordinates per ship return an error
            if (input.split(" ").length > 2) {
                System.out.println("Error!");
                return;
            }

            //Split the input into 2 pieces to extract the coordinates
            String startPosition = input.split(" ")[0].toUpperCase();
            String endPosition = input.split(" ")[1].toUpperCase();

            //Extract column indices from the numerical coordinates and deduct "1" from them
            int startingPositionIndex = Integer.parseInt(startPosition.substring(1)) - 1;
            int endingPositionIndex = Integer.parseInt(endPosition.substring(1)) - 1;

            //Store numerical coordinates for column indices for later use
            char startingPositionRowLetter = startPosition.charAt(0);
            char endingPositionRowLetter = endPosition.charAt(0);

            //Convert alphabetical coordinates into row indices and store them for later use
            int startRowIndex = startingPositionRowLetter - 'A';
            int endingRowIndex = endingPositionRowLetter - 'A';

            //If first entered alphabetical coordinate greater than the latter, reverse them for ship placement use
            if (startRowIndex > endingRowIndex && startingPositionIndex == endingPositionIndex) {
                int tempRowIndex = endingRowIndex;
                endingRowIndex = startRowIndex;
                startRowIndex = tempRowIndex;
            }

            //If first entered numerical coordinate greater than the latter, reverse them for ship placement use
            if (startRowIndex == endingRowIndex && startingPositionIndex > endingPositionIndex) {
                int tempPositionIndex = endingPositionIndex;
                endingPositionIndex = startingPositionIndex;
                startingPositionIndex = tempPositionIndex;
            }

            int shipLength = 0;
            StringBuilder parts = new StringBuilder();

            //Horizontal placement
            if (startRowIndex == endingRowIndex &&
                    startingPositionIndex >= 0 &&
                    endingPositionIndex < field.length) {
                for (int i = startingPositionIndex; i <= endingPositionIndex; i++) {
                    if (field[startRowIndex][i] == fogOfWar) {
                        field[startRowIndex][i] = occupiedByShip;
                        shipLength++;
                        parts.append(startingPositionRowLetter).append(i + 1).append(" ");
                    } else if (field[startRowIndex][i] == occupiedByShip) {
                        System.out.println("Coordinate " + startingPositionRowLetter + (i + 1) + " is already occupied.");
                        return;
                    }
                }
            //Vertical placement
            } else if (startingPositionIndex == endingPositionIndex &&
                    endingRowIndex < field.length) {
                for (int i = startRowIndex; i <= endingRowIndex; i++) {
                    if (field[i][startingPositionIndex] == fogOfWar) {
                        field[i][startingPositionIndex] = occupiedByShip;
                        shipLength++;
                        parts.append((char) ('A' + i)).append(startingPositionIndex + 1).append(" ");
                    } else if (field[i][startingPositionIndex] == occupiedByShip) {
                        System.out.println("Coordinate " + (char) ('A' + i) + (startingPositionIndex + 1) + " is already occupied.");
                        return;
                    }
                }
            } else {
                System.out.println("Error!");
                return;
            }
            System.out.println("Length: " + shipLength);
            System.out.println("> " + startPosition + " " + endPosition);
            System.out.println("Parts: " + parts);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("Error!");
        }
    }
}
