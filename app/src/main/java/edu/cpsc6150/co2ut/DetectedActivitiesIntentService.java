/**
 * Team Name: Team_01
 * Team Member1 Name: Blake Washburn
 * Team Member1 CUID: C89257841
 * Team Member1 email: bwashbu@g.clemson.edu
 *
 * Team Member2: Stephen Carvalho
 * Team Member2 CUID: C70675411
 * Team Member2 email: scarval@g.clemson.edu
 *
 * Citations:
 *
 * App Icon: https://www.flaticon.com/home
 *
 * States Page Icons: https://www.flaticon.com/home
 *
 * Detect Activity Code Inspired from: https://developers.google.com/location-context/activity-recognition
 *
 * Meal Log Code Inspired from: https://learn.zybooks.com/zybook/CLEMSONCPSC4150PlaueFall2019/chapter/5/section/2
 * and https://learn.zybooks.com/zybook/CLEMSONCPSC4150PlaueFall2019/chapter/9/section/5
 *
 * SQLite Database Code Inspired from: https://developer.android.com/training/data-storage/sqlite
 * and https://learn.zybooks.com/zybook/CLEMSONCPSC4150PlaueFall2019/chapter/6/section/4
 *
 * FusedLocation Provider and Location Services code inspired from: https://learn.zybooks.com/zybook/CLEMSONCPSC4150PlaueFall2019/chapter/9/section/6
 * and https://developer.android.com/training/location
 *
 * Carbon Footprint Calculator Metrics: https://www.epa.gov/energy/greenhouse-gases-equivalencies-calculator-calculations-and-references
 */
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
    public DetectedActivitiesIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }   //end DetectedActivitiesIntentService constructor
    @Override
    public void onCreate() {
        super.onCreate();
    }   //end onCreate method

    /**
     * Functionality: Handle the detected activity intent
     * PreConditions: Detected activity must be passed through the intent
     * PostConditions: The detected activity is logged with the type and confidence of the activity
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
     * Functionality: Broadcast the detected activity by putting it into an intent
     * PreConditions: DetectedActivity must be passed as an argument
     * PostConditions: Broadcast the type and confidence of the detected activity as an intent
     */
    private void broadcastActivity(DetectedActivity activity) {
        Intent intent = new Intent(Constants.BROADCAST_DETECTED_ACTIVITY);
        intent.putExtra("type", activity.getType());
        intent.putExtra("confidence", activity.getConfidence());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }   //end broadcastActivity method
}   //end DetectedActivitiesIntentService Class

