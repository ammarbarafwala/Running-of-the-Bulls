package lab9;

public class StreetMap {

	Coordinate[][] coordinate;

	public StreetMap(Coordinate[][] coordinate) {
		this.coordinate = coordinate;
	}

	public Coordinate[][] getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate[][] coordinate) {
		this.coordinate = coordinate;
	}
	
}
