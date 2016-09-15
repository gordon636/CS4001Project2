package com.example.ehar.accelerometercs450;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;

    private AccelerometerHandler ah = null;
    private LocationHandler lh = null;

    TextView lat = null;
    TextView lng = null;
    TextView latitude = null;
    TextView longitude = null;
    TextView x_accel_view = null;
    TextView y_accel_view = null;
    TextView z_accel_view = null;
    TextView x = null;
    TextView y = null;
    TextView z = null;
    ImageView imageView = null;
    int defaultColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x_accel_view = (TextView) findViewById(R.id.x_accel_view);
        y_accel_view = (TextView) findViewById(R.id.y_accel_view);
        z_accel_view = (TextView) findViewById(R.id.z_accel_view);
        x = (TextView) findViewById(R.id.x);
        y = (TextView) findViewById(R.id.y);
        z = (TextView) findViewById(R.id.z);
        lat = (TextView) findViewById(R.id.lat);
        lng = (TextView) findViewById(R.id.lng);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setImageResource(R.drawable.slubackground);

        this.ah = new AccelerometerHandler(this);
        this.ah.addObserver(this);

        // Avoid typing twice
        String permission = "android.permission.ACCESS_FINE_LOCATION";

        int check = ContextCompat.checkSelfPermission(this, permission);
        if (check == PackageManager.PERMISSION_GRANTED){
            lh = new LocationHandler(this);
            lh.addObserver(this);
            lh.getLastKnownLocation();
        }else{
            // Request permission to use location, this is default when first opening app
            ActivityCompat.requestPermissions(this, new String[]{ permission }, PERMISSION_REQUEST_FINE_LOCATION);
            // Until location use is granted, make values red
            lat.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.error));
            lng.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.error));
            latitude.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.error));
            longitude.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.error));
        }
    }


    // Method will allow lat and long values to appear if the users grants permission for app to use location
    // Without method, nothing would happen when users gives permissions to app
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // If user allows sharing of location, give location handler lat and long positions
        if(requestCode == PERMISSION_REQUEST_FINE_LOCATION){
            if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                lh = new LocationHandler(this);
                lh.addObserver(this);
                lh.getLastKnownLocation();

                //No longer want lat and long labels and values to be red
                // Use x to get normal colors since it will never be anything else
                defaultColor = x.getTextColors().getDefaultColor();
                lat.setTextColor(defaultColor);
                lng.setTextColor(defaultColor);
                latitude.setTextColor(defaultColor);
                longitude.setTextColor(defaultColor);
            }
            // Without permission, location handler should have no info
            else {
                lh = null;
            }
        }
    }


    @Override
    public void update(Observable observable, Object o) {
        //Must determine which sensor is calling function
        if(observable == ah) {
            // Update acceleration
            float[] xyz = (float[]) o;
            x_accel_view.setText(Float.toString(xyz[0]));
            y_accel_view.setText(Float.toString(xyz[1]));
            z_accel_view.setText(Float.toString(xyz[2]));
        } else if(observable == lh){
            // Update location
            latitude.setText(Double.toString(((Location) o).getLatitude()));
            longitude.setText(Double.toString(((Location) o).getLongitude()));
        }
    }

}