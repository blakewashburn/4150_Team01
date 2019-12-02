package edu.cpsc6150.co2ut;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;

public class DetectedActivitiesIntentService  extends IntentService {

    protected static final String TAG = DetectedActivitiesIntentService.class.getSimpleName();

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    public DetectedActivitiesIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }   //end DetectedActivitiesIntentService constructor

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }   //end onCreate method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

        // Get the list of the probable activities associated with the current state of the
        // device. Each activity is associated with a confidence level, which is an int between
        // 0 and 100.
        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();

        for (DetectedActivity activity : detectedActivities) {
            Log.i(TAG, "Detected activity: " + activity.getType() + ", " + activity.getConfidence());
            broadcastActivity(activity);
        }   //end for loop
    }   //end onHandleIntent method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    private void broadcastActivity(DetectedActivity activity) {
        Intent intent = new Intent(Constants.BROADCAST_DETECTED_ACTIVITY);
        intent.putExtra("type", activity.getType());
        intent.putExtra("confidence", activity.getConfidence());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }   //end broadcastActivity method
}   //end DetectedActivitiesIntentService Class

