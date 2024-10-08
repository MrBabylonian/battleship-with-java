package Battleship;

import java.util.Objects;

public class Coordinates {
    int row;
    int column;

    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
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
        Coordinates that = (Coordinates) obj;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column); // Use Objects.hash for consistent hashcode
    }
}
