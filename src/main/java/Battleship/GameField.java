package Battleship;

import java.util.Arrays;
import java.util.Scanner;

public class GameField {
    public static final char FOG_OF_WAR = '~';
    public static final char OCCUPIED_BY_SHIP = 'O';
    public static final char HIT = 'X';
    public static final char MISS = 'M';
    public final char[][] field = new char[10][10];


    //Fill up the game field with "FOG OF WAR"
    public GameField() {
        for (char[] chars : field) {
            Arrays.fill(chars, FOG_OF_WAR);
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

    //Get coordinates from the user and validate its format
    private String[] getCoordinateInputFromUser(Ship ship) {
        boolean validatedCoordinates = false;
        String[] inputCoordinates = null;

        //Keep prompting the user until coordinates are provided in the valid format
        while (!validatedCoordinates) {
            Scanner scanner = new Scanner(System.in);
            System.out.printf("Enter the coordinates for your %s \n", ship);
            System.out.print("> ");
            String input = scanner.nextLine();
            inputCoordinates = input.toUpperCase().split(" ");
            //If the user enters more than 2 coordinates per ship return an error
            if (inputCoordinates.length > 2) {
                System.err.println("Error! Enter exactly 2 coordinates");
                continue;
            } else if (Character.isAlphabetic(inputCoordinates[0].charAt(1)) ||
                    Character.isDigit(inputCoordinates[0].charAt(0)) ||
                    Character.isAlphabetic(inputCoordinates[1].charAt(1)) ||
                    Character.isDigit(inputCoordinates[1].charAt(0))) {
                System.err.println("Error! Enter the coordinates in the correct format. Example: 'A1' ");
                continue;
            }
            validatedCoordinates = true;
        }
        return inputCoordinates;
    }

    //Parse the user provided coordinates to user for ship placements
    private int[] parseCoordinates(String[] coordinates, Ship ship) {

        //Split the input into 2 pieces to extract the coordinates
        String startPosition = coordinates[0];
        System.out.println(startPosition.charAt(0));
        String endPosition = coordinates[1];
        System.out.println(endPosition.charAt(0));
        //Extract column indices from the numerical coordinates and deduct "1" from them
        int startingColumnIndex = Integer.parseInt(startPosition.substring(1)) - 1;
        int endingColumnIndex = Integer.parseInt(endPosition.substring(1)) - 1;

        //Convert alphabetical coordinates into row indices and store them for later use
        int startRowIndex = startPosition.charAt(0) - 'A';
        int endingRowIndex = endPosition.charAt(0) - 'A';

        //If first entered alphabetical coordinate greater than the latter, reverse them for ship placement use
        if (startRowIndex > endingRowIndex && startingColumnIndex == endingColumnIndex) {
            int tempRowIndex = endingRowIndex;
            endingRowIndex = startRowIndex;
            startRowIndex = tempRowIndex;
        }

        //If first entered numerical coordinate greater than the latter, reverse them for ship placement use
        if (startRowIndex == endingRowIndex && startingColumnIndex > endingColumnIndex) {
            int tempPositionIndex = endingColumnIndex;
            endingColumnIndex = startingColumnIndex;
            startingColumnIndex = tempPositionIndex;
        }

        return new int[]{startRowIndex, startingColumnIndex, endingRowIndex, endingColumnIndex};
    }

    void placeShips() {

        //Create a list of different ships (from larger to smaller) to place them on the field in an order
        Ship[] shipsToPlace = {
                new AircraftCarrier(),
                new Battleship(),
                new Submarine(),
                new Cruiser(),
                new Destroyer()
        };

        for (Ship ship : shipsToPlace) {
            boolean shipPlaced = false;

            //Keep prompting the user until all ships are placed on the field
            while (!shipPlaced) {
                String[] coordinates = getCoordinateInputFromUser(ship);
                int[] parsedCoordinates = parseCoordinates(coordinates, ship);
                int startRowIndex = parsedCoordinates[0];
                int startingColumnIndex = parsedCoordinates[1];
                int endingRowIndex = parsedCoordinates[2];
                int endingColumnIndex = parsedCoordinates[3];

                try {
                    StringBuilder parts = new StringBuilder();

                    //Check if the length of the coordinates matches the ship length
                    //If not, prompt the user again
                    if (endingColumnIndex - startingColumnIndex + 1 == ship.length
                            || endingRowIndex - startRowIndex + 1 == ship.length) {

                        //Horizontal placement
                        if (startRowIndex == endingRowIndex &&
                                startingColumnIndex >= 0 &&
                                endingColumnIndex < field.length) {
                            for (int i = startingColumnIndex; i <= endingColumnIndex; i++) {
                                if (field[startRowIndex][i] == FOG_OF_WAR) {
                                    field[startRowIndex][i] = OCCUPIED_BY_SHIP;
                                    parts.append((char) (startRowIndex + 'A')).append(startingColumnIndex + 1).append(" ");
                                } else if (field[startRowIndex][i] == OCCUPIED_BY_SHIP) {
                                    System.err.println("Coordinate " + (char) (startRowIndex + 'A') + (startingColumnIndex + 1) + " is already occupied.");
                                }
                            }
                        }

                        //Vertical placement
                        else if (startingColumnIndex == endingColumnIndex &&
                                startRowIndex >= 0 &&
                                endingRowIndex < field.length
                        ) {
                            for (int i = startRowIndex; i <= endingRowIndex; i++) {
                                if (field[i][startingColumnIndex] == FOG_OF_WAR) {
                                    field[i][startingColumnIndex] = OCCUPIED_BY_SHIP;
                                    parts.append((char) ('A' + i)).append(startingColumnIndex + 1).append(" ");
                                } else if (field[i][startingColumnIndex] == OCCUPIED_BY_SHIP) {
                                    System.err.println("Coordinate " + (char) ('A' + i) + (startingColumnIndex + 1) + " is already occupied.");
                                }
                            }
                        } else {
                            System.err.println("Error!");
                            return;
                        }
                        shipPlaced = true;
                        //System.out.println("Length: " + coordinateLength);
                        System.out.println("> " + coordinates[0] + " " + coordinates[1] + "\n");
                        //System.out.println("Parts: " + parts);
                        System.out.println(this);
                    } else {
                        System.err.printf("\n Error! Wrong length of the %s! Try again: \n", ship);
                        System.out.println("> " + coordinates[0] + " " + coordinates[1] + "\n");
                        System.out.println(this);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println(e.getMessage());
                    System.err.println("Error!");
                }
            }
        }
    }
}