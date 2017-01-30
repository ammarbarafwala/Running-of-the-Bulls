package lab9;

public class Coordinate {

	int row,column;
	char coordinateValue;
	public Coordinate(int row, int column, char coordinateValue) {
		
		this.row = row;
		this.column = column;
		this.coordinateValue = coordinateValue;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public char getCoordinateValue() {
		return coordinateValue;
	}
	public void setCoordinateValue(char coordinateValue) {
		this.coordinateValue = coordinateValue;
	}
	
}
