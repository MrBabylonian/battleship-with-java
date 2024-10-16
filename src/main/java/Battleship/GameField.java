package Battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static Battleship.FieldSymbols.*;

enum FieldSymbols {
    FOG_OF_WAR('~'),
    OCCUPIED_BY_SHIP('O'),
    HIT('X'),
    MISS('M');

    private final char SYMBOL;

    FieldSymbols(char SYMBOL) {
        this.SYMBOL = SYMBOL;
    }

    public char getSYMBOL() {
        return SYMBOL;
    }
}


class GameField {

    private final char[][] field = new char[10][10];

    //List of ships to be deployed by each player
    private final ArrayList<Ship> shipsOnField = new ArrayList<Ship>(List.of(
            new AircraftCarrier(),
            new Battleship(),
            new Submarine(),
            new Cruiser(),
            new Destroyer()
    ));

    //Fill up the game field with "FOG OF WAR"
    GameField() {
        for (char[] chars : field) {
            Arrays.fill(chars, FOG_OF_WAR.getSYMBOL());
        }
    }


    //Draw coordinates around the game field
    @Override
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

    void setField(int row, int col, char symbol) {
        field[row][col] = symbol;
    }

    char[][] getField() {
        return field;
    }

    ArrayList<Ship> getShipsOnField() {
        return shipsOnField;
    }

    //Get coordinates from the user and validate its format
    private String[] getCoordinateInputFromPlayer() {
        boolean validatedCoordinates = false;
        String[] inputCoordinates = null;

        //Keep prompting the user until coordinates are provided in the valid format
        while (!validatedCoordinates) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            inputCoordinates = input.toUpperCase().split(" ");
            //If the user enters more than 2 coordinates per ship return an error
            if (inputCoordinates.length != 2) {
                System.err.println("Error! Enter exactly 2 coordinates");
                continue;
            } else if (((inputCoordinates[0].length() > 3 || inputCoordinates[0].length() < 2) ||
                    !Character.isAlphabetic(inputCoordinates[0].charAt(0)) ||
                    !Character.isDigit(inputCoordinates[0].charAt(1)))
                    ||
                    ((inputCoordinates[1].length() > 3 || inputCoordinates[1].length() < 2) ||
                            !Character.isAlphabetic(inputCoordinates[1].charAt(0)) ||
                            !Character.isDigit(inputCoordinates[1].charAt(1)))
            ) {
                if ((inputCoordinates[0].length() > 2 || inputCoordinates[1].length() > 2) &&
                        (!Character.isDigit(inputCoordinates[0].charAt(2))) || !Character.isDigit(inputCoordinates[1].charAt(2))) {
                    System.err.println("Error! Enter the coordinates in the correct format. Example: 'A1' ");
                    continue;
                }
                System.err.println("Error! Enter the coordinates in the correct format. Example: 'A1' ");
                continue;
            }
            validatedCoordinates = true;
        }
        return inputCoordinates;
    }

    //Parse the player provided coordinates to user for ship placements
    private int[] parseCoordinates() {

        boolean coordinatesAreCorrectlyParsed = false;

        while (!coordinatesAreCorrectlyParsed) {
            try {
                String[] coordinates = getCoordinateInputFromPlayer();

                //Split the input into 2 pieces to extract the coordinates
                String startPosition = coordinates[0];
                String endPosition = coordinates[1];

                //Extract column indices from the numerical coordinates and deduct "1" from them
                int startingColumnIndex = Integer.parseInt(startPosition.substring(1)) - 1;
                int endingColumnIndex = Integer.parseInt(endPosition.substring(1)) - 1;

                //Convert alphabetical coordinates into row indices and store them for later use
                int startingRowIndex = startPosition.charAt(0) - 'A';
                int endingRowIndex = endPosition.charAt(0) - 'A';

                //If first entered alphabetical coordinate greater than the latter, reverse them for ship placement use
                if (startingRowIndex > endingRowIndex && startingColumnIndex == endingColumnIndex) {
                    int tempRowIndex = endingRowIndex;
                    endingRowIndex = startingRowIndex;
                    startingRowIndex = tempRowIndex;
                }

                //If first entered numerical coordinate greater than the latter, reverse them for ship placement use
                if (startingRowIndex == endingRowIndex && startingColumnIndex > endingColumnIndex) {
                    int tempPositionIndex = endingColumnIndex;
                    endingColumnIndex = startingColumnIndex;
                    startingColumnIndex = tempPositionIndex;
                }
                return new int[]{startingRowIndex, startingColumnIndex, endingRowIndex, endingColumnIndex};
            } catch (NumberFormatException e) {
                System.err.println("Error! Enter the coordinates in the correct format. Example: 'A1' ");
            }
        }
        return null;
    }

    //Check if the coordinates entered by player available for ship placement, if so, than place the ship
    private boolean canPlaceShip(int fixedIndex, int start, int end, boolean isHorizontal, Ship ship) {

            /*
            -- If the placement is horizontal, variable 'fixed' becomes the row index and,
             variable 'iterator' becomes the column index and increments
            -- If the placement is vertical, variable 'fixed' becomes the column index and,
             variable 'iterator becomes the row index and increments
             */
        for (int i = start; i <= end; i++) {
            int fixed = isHorizontal ? fixedIndex : i;
            int iterator = isHorizontal ? i : fixedIndex;
            
            /*
            System.out.printf("Checking %c%d", Math.min(field.length - 1, fixed + 1) + 'A', iterator + 1);
            System.out.printf("Checking %c%d", Math.max(0, fixed - 1) + 'A', iterator + 1);
            System.out.printf("Checking %c%d", fixed + 'A', Math.min(field.length - 1, iterator + 1) + 1);
            System.out.printf("Checking %c%d", fixed + 'A', Math.max(0, iterator - 1) + 1);
            */


            // Check if any of the entered coordinates are occupied by another ship if yes,
            // return and error and prompt the player for new coordinates
            if (field[fixed][iterator] == FieldSymbols.OCCUPIED_BY_SHIP.getSYMBOL()) {
                System.err.printf("Error! Coordinate %c%d is already occupied!%n", fixed + 'A', iterator + 1);
                return false;
                // Check if the adjacent fields are occupied by another ship if yes,
                // return an error and prompt the player for new coordinates
            } else if (field[Math.min(field.length - 1, fixed + 1)][iterator] == OCCUPIED_BY_SHIP.getSYMBOL() ||
                    field[Math.max(0, fixed - 1)][iterator] == OCCUPIED_BY_SHIP.getSYMBOL() ||
                    field[fixed][Math.min(field.length - 1, iterator + 1)] == OCCUPIED_BY_SHIP.getSYMBOL() ||
                    field[fixed][Math.max(0, iterator - 1)] == OCCUPIED_BY_SHIP.getSYMBOL()
            ) {
                System.err.println("Error! You placed it too close to another one. Try again:");
                return false;
            }
        }

        //Place the ship
        for (int i = start; i <= end; i++) {
            int fixed = isHorizontal ? fixedIndex : i;
            int iterator = isHorizontal ? i : fixedIndex;
            field[fixed][iterator] = OCCUPIED_BY_SHIP.getSYMBOL();
            Coordinate coordinate = new Coordinate(fixed, iterator); // Create a 'Coordinates' object every iteration for every coordinate
            ship.setShipCoordinates(coordinate); //Store individual ship coordinates in a 'Coordinates' type Array
        }
        return true;
    }

    //Check if the individual ship is placed and manage between horizontal and vertical placement
    private boolean placedShip(int startingRowIndex, int startingColumnIndex, int endingRowIndex, int endingColumnIndex, Ship ship) {

        //Horizontal placement
        if (startingRowIndex == endingRowIndex &&
                startingColumnIndex >= 0 &&
                endingColumnIndex < field.length) {
            return canPlaceShip(startingRowIndex, startingColumnIndex, endingColumnIndex, true, ship);
        }

        //Vertical placement
        else if (startingColumnIndex == endingColumnIndex &&
                startingRowIndex >= 0 &&
                endingRowIndex < field.length
        ) {
            return canPlaceShip(startingColumnIndex, startingRowIndex, endingRowIndex, false, ship);
        } else {
            System.err.println("Error! Wrong ship location! Try again:");
            return false;
        }
    }

    //Place all the ships required with the helps of helper methods to the field by iterating over them over the array
    void placeShips() {

        for (Ship ship : shipsOnField) {
            boolean shipPlaced = false;

            System.out.printf("Enter the coordinates for your %s (%d cells): %n", ship, ship.getLength());
            System.out.print("> ");

            //Keep prompting the user until all ships are placed on the field
            while (!shipPlaced) {

                int[] parsedCoordinates = parseCoordinates();
                int startingRowIndex = parsedCoordinates[0];
                int startingColumnIndex = parsedCoordinates[1];
                int endingRowIndex = parsedCoordinates[2];
                int endingColumnIndex = parsedCoordinates[3];

                try {
                    //Check if the length of the coordinates matches the ship length
                    //If not, prompt the player again
                    if ((endingColumnIndex - startingColumnIndex + 1 == ship.getLength()) ||
                            endingRowIndex - startingRowIndex + 1 == ship.getLength()) {
                        if (placedShip(startingRowIndex, startingColumnIndex, endingRowIndex, endingColumnIndex, ship)) {
                            shipPlaced = true;
                            System.out.printf("> %c%d %c%d %n", startingRowIndex + 'A', startingColumnIndex + 1, endingRowIndex + 'A', endingColumnIndex + 1);
                            System.out.println(this);
                        }
                    } else {
                        System.out.printf("Error! Wrong length of the %s! Try again: %n", ship);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println(e.getMessage());
                    System.err.println("Error! ");
                }
            }
        }
    }
}