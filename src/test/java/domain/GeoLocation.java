package domain;

public class GeoLocation {
    double lat;
    double lng;
    public GeoLocation() {

    }
    public GeoLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getlng() {
        return lng;
    }

    public void setlng(double lng) {
        this.lng = lng;
    }
}
