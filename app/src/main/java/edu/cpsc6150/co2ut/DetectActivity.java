package edu.cpsc6150.co2ut;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.DetectedActivity;

public class DetectActivity extends AppCompatActivity {

    private String TAG = DetectActivity.class.getSimpleName();
    BroadcastReceiver broadcastReceiver;

    private TextView txtActivity, txtConfidence, positive, driving, still;
    private ImageView imgActivity;


    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);
        startTracking();
        txtActivity = findViewById(R.id.txt_activity);
        txtConfidence = findViewById(R.id.txt_confidence);
        positive = findViewById(R.id.positive);
        driving = findViewById(R.id.driving);
        still = findViewById(R.id.still);
        imgActivity = findViewById(R.id.img_activity);

        positive.setVisibility(View.GONE);
        still.setVisibility(View.GONE);
        driving.setVisibility(View.GONE);


        broadcastReceiver = new BroadcastReceiver() {
            /**
             * Functionality:
             * PreConditions:
             * PostConditions:
             */
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_DETECTED_ACTIVITY)) {
                    int type = intent.getIntExtra("type", -1);
                    int confidence = intent.getIntExtra("confidence", 0);
                    handleUserActivity(type, confidence);
                }
            }   //end onReceive method
        };
        startTracking();
    }   //end onCreate method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    private void handleUserActivity(int type, int confidence) {
        String label = getString(R.string.activity_unknown);
        int icon = R.drawable.ic_still;
        String message = "";


        switch (type) {
            case DetectedActivity.IN_VEHICLE: {
                label = getString(R.string.activity_in_vehicle);
                icon = R.drawable.ic_driving;
                message = "You should consider using public transit or be more active and bike, walk or run.";
                positive.setVisibility(View.GONE);
                still.setVisibility(View.GONE);
                driving.setVisibility(View.VISIBLE);
                driving.setText(message);
                break;
            }
            case DetectedActivity.ON_BICYCLE: {
                label = getString(R.string.activity_on_bicycle);
                message = "Great going! Your droping Carbon and carbs!";
                icon = R.drawable.ic_on_bicycle;
                positive.setVisibility(View.VISIBLE);
                still.setVisibility(View.GONE);
                driving.setVisibility(View.GONE);
                positive.setText(message);
                break;
            }
            case DetectedActivity.ON_FOOT: {
                label = getString(R.string.activity_on_foot);
                icon = R.drawable.ic_walking;
                message = "Great going! Your droping Carbon and carbs!";
                positive.setVisibility(View.VISIBLE);
                still.setVisibility(View.GONE);
                driving.setVisibility(View.GONE);
                positive.setText(message);
                break;
            }
            case DetectedActivity.RUNNING: {
                label = getString(R.string.activity_running);
                icon = R.drawable.ic_running;
                message = "Great going! Your droping Carbon and carbs!";
                positive.setVisibility(View.VISIBLE);
                still.setVisibility(View.GONE);
                driving.setVisibility(View.GONE);
                positive.setText(message);
                break;
            }
            case DetectedActivity.STILL: {
                label = getString(R.string.activity_still);
                positive.setVisibility(View.GONE);
                still.setVisibility(View.VISIBLE);
                driving.setVisibility(View.GONE);
                still.setText(message);
                break;
            }
            case DetectedActivity.TILTING: {
                label = getString(R.string.activity_tilting);
                icon = R.drawable.ic_tilting;
                positive.setVisibility(View.GONE);
                still.setVisibility(View.VISIBLE);
                driving.setVisibility(View.GONE);
                still.setText(message);
                break;
            }
            case DetectedActivity.WALKING: {
                label = getString(R.string.activity_walking);
                icon = R.drawable.ic_walking;
                message = "Great going! Your droping Carbon and carbs!";
                positive.setVisibility(View.VISIBLE);
                still.setVisibility(View.GONE);
                driving.setVisibility(View.GONE);
                positive.setText(message);
                break;
            }
            case DetectedActivity.UNKNOWN: {
                label = getString(R.string.activity_unknown);
                positive.setVisibility(View.GONE);
                still.setVisibility(View.VISIBLE);
                driving.setVisibility(View.GONE);
                still.setText(message);
                break;
            }
        }   //end switch statement

        Log.e(TAG, "User activity: " + label + ", Confidence: " + confidence);

        if (confidence > Constants.CONFIDENCE) {
            txtActivity.setText(label);
            txtConfidence.setText("Confidence: " + confidence);
            imgActivity.setImageResource(icon);


        }
    }   //end handleUserActivity method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    @Override
    protected void onResume() {
        super.onResume();
        stopTracking();
        startTracking();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(Constants.BROADCAST_DETECTED_ACTIVITY));
        //startTracking();
    }   //end onResume method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }   //end onPause

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    private void startTracking() {
        Intent intent = new Intent(DetectActivity.this, BackgroundDetectedActivitiesService.class);
        startService(intent);
    }   //end startTracking method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    private void stopTracking() {
        Intent intent = new Intent(DetectActivity.this, BackgroundDetectedActivitiesService.class);
        stopService(intent);
    }   //end stopTracking method
}   //end DetectActivity Class
