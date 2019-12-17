package voogasalad.authoring.util;

public class Coordinate {
    private int row;
    private int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int[] getCoordinates() {
        return new int[]{row, col};
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean equalsCoords(int row, int col){
        return this.row == row && this.col == col;
    }
}
