package Battleship;

import java.util.ArrayList;

abstract class Ship {
    private final int length;
    private boolean isAlive;
    private final ArrayList<Coordinate> shipCoordinates;

    Ship(int length) {
        this.length = length;
        this.isAlive = true;
        shipCoordinates = new ArrayList<Coordinate>(this.length);
    }

    int getLength() {
        return length;
    }

    ArrayList<Coordinate> getShipCoordinates() {
        return shipCoordinates;
    }

    void setShipCoordinates(Coordinate coordinate) {
        this.shipCoordinates.add(coordinate);
    }

    boolean isAlive() {
        return this.isAlive;
    }

    //Check if a ship is sunk, if yes, delete it from 'shipsOnfield'
    void checkSunk(ArrayList<Ship> shipsOnField) {
        if (this.shipCoordinates.isEmpty()) {
            this.isAlive = false;
            shipsOnField.remove(this);
            shipsOnField.trimToSize();
            if (shipsOnField.isEmpty()) {
                //Print the following message when one of the players loses all theirs ships and game finishes
                System.out.printf("%s, you sank the last ship of %s. You won. Congratulations!", Game.getCurrentPlayer().getName(), Game.getOpponentPlayer().getName());
            } else {
                //Print the following message when a ships gets sank
                System.out.printf("Nice Shot! You sank the %s of %s! \n", this, Game.getOpponentPlayer().getName());
            }
        }
    }
}

class AircraftCarrier extends Ship {

    AircraftCarrier() {
        super(5);
    }

    @Override
    public String toString() {
        return "Aircraft Carrier";
    }
}

class Battleship extends Ship {

    Battleship() {
        super(4);
    }

    @Override
    public String toString() {
        return "Battleship";
    }
}

class Submarine extends Ship {

    Submarine() {
        super(3);
    }

    @Override
    public String toString() {
        return "Submarine";
    }
}

class Cruiser extends Ship {

    Cruiser() {
        super(3);
    }

    @Override
    public String toString() {
        return "Cruiser";
    }
}

class Destroyer extends Ship {

    Destroyer() {
        super(2);
    }

    @Override
    public String toString() {
        return "Destroyer";
    }
}

