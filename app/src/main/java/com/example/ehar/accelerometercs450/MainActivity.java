package com.example.ehar.accelerometercs450;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Observer;

public class MainActivity extends AppCompatActivity {

    TextView z_accel_view = null;
    private AccelerometerHandler ah = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.z_accel_view =
                (TextView) findViewById(R.id.z_accel_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.ah = new AccelerometerHandler(this);
    }

    public void new_accel_z_value(float z) {
        this.z_accel_view.setText(Float.toString(z));
    }


}
