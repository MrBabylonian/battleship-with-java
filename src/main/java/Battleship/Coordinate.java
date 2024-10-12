package Battleship;

import java.util.Objects;

//This class can be converted into a 'record class' for time saving, but it has been left this way for better readability
class Coordinate {

    private final int row;
    private final int column;

    Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    int getRow() {
        return this.row;
    }

    int getColumn() {
        return this.column;
    }

    //Override the 'equals' method to be able to compare coordinates for shooting function
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) obj;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column); // Use Objects.hash for consistent hashcode
    }
}
