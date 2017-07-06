package com.maxgfr.traxivitytensorflow;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    private Button buttonLoadNetwork;
    private TextView textViewLoadNetwork;
    private TextView textViewDownstairs;
    private TextView textViewUpstairs;
    private TextView textViewWalking;
    private TextView textViewSitting;
    private TextView textViewJogging;
    private TextView textViewStanding;

    private static final int N_SAMPLES = 500;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private static List<Float> x;
    private static List<Float> y;
    private static List<Float> z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();

        buttonLoadNetwork.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });

        x = new ArrayList<Float>();
        y = new ArrayList<Float>();
        z = new ArrayList<Float>();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer , SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void initializeView () {
        buttonLoadNetwork = (Button) findViewById(R.id.buttonLoadNetwork);
        textViewLoadNetwork = (TextView) findViewById(R.id.textViewLoadNetwork);
        textViewDownstairs = (TextView) findViewById(R.id.textViewDownstairs);
        textViewJogging = (TextView) findViewById(R.id.textViewJogging);
        textViewSitting = (TextView) findViewById(R.id.textViewSitting);
        textViewStanding = (TextView) findViewById(R.id.textViewStanding);
        textViewUpstairs = (TextView) findViewById(R.id.textViewUpstairs);
        textViewWalking = (TextView) findViewById(R.id.textViewWalking);
    }

    private void loadNetwork () {

    }
    
    private void dispRes () {
        textViewDownstairs.setText("");
        textViewJogging.setText("");
        textViewSitting.setText("");
        textViewStanding.setText("");
        textViewUpstairs.setText("");
        textViewWalking.setText("");
    }

    private void activityPrediction() {
        if(x.size() == N_SAMPLES && y.size() == N_SAMPLES && z.size() == N_SAMPLES) {
            x.clear();
            y.clear();
            z.clear();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        activityPrediction();
        x.add(event.values[0]);
        y.add(event.values[1]);
        z.add(event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
