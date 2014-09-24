package model;

public class Coordinates {
    private double latitude;
    private double longitude;

    public Coordinates() {
        latitude = longitude = 0;
    }

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String format() {
        return Double.toString(latitude) + "," + Double.toString(longitude);
    }
    public double getLat() {
        return latitude;
    }
    public double getLng() {
        return longitude;
    }

    public void setCoordinates(final double lat, final double lng) {
        this.latitude = lat;
        this.longitude = lng;
    }
}
