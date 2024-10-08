package Battleship;

import java.util.ArrayList;

import static Battleship.GameField.shipsOnField;

abstract class Ship {
    int length;
    ArrayList<Coordinates> shipCoordinates;
    boolean isAlive;

    Ship(int length) {
        this.length = length;
        this.isAlive = true;
        shipCoordinates = new ArrayList<Coordinates>(this.length);
    }

    //Check if a ship is sunk, if yes, delete it from the 'shipsOnfield'
    public void checkSunk() {
        if (this.shipCoordinates.isEmpty()) {
            this.isAlive = false;
            shipsOnField.remove(this);
            shipsOnField.trimToSize();
            if (shipsOnField.isEmpty()) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            } else {
                System.out.println("You sank a ship! Specify a new target: \n");
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

