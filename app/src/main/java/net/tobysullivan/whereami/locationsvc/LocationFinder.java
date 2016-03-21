package net.tobysullivan.whereami.locationsvc;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationFinder implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient = null;
    private Location location = null;

    public LocationFinder(Context ctx) {
        createGoogleAPIClient(ctx);
    }

    private void createGoogleAPIClient(Context ctx) {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(ctx)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public boolean isReady() {
        return this.location != null;
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            android.location.Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            float lat = (float)lastLocation.getLatitude();
            float lng = (float)lastLocation.getLongitude();
            location = new Location(lat, lng);
        } catch (SecurityException ex) {
            // Ignore
            // TODO: Handle properly
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
