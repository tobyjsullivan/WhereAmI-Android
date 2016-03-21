package net.tobysullivan.whereami.locationsvc;

public class Location {
    private float latitude, longitude;

    public Location(float lat, float lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public float getLongitude() {
        return longitude;
    }

}
