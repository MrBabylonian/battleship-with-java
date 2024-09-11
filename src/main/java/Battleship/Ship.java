package Battleship;

abstract class Ship {
    int length;

    Ship(int length) {
        this.length = length;
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

