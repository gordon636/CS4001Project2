package com.example.ehar.accelerometercs450;

/**
 * Created by Gordon on 9/14/16.
 */
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Observable;

public class LocationHandler extends Observable implements LocationListener {

    private LocationManager locationManager = null;

    public LocationHandler(Activity activity) {
        try {
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            // Update every 0s, 0m
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch(SecurityException e) {
            // Won't let me compile without try, catch
            locationManager = null;
        }
    }public void getLastKnownLocation(){
        try {
            // Get the last known location to display immediately
            Location lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            setChanged();
            notifyObservers(lastKnown);
        } catch(SecurityException e) {
            // Won't let me compile without try, catch
            locationManager = null;
        }
    }

    //Send to main activity if user moved (flinched)
    @Override
    public void onLocationChanged(Location location) {
        setChanged();
        notifyObservers(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

