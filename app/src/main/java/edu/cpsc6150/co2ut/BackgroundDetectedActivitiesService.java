package edu.cpsc6150.co2ut;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class BackgroundDetectedActivitiesService extends Service {
    private static final String TAG = BackgroundDetectedActivitiesService.class.getSimpleName();
    private Intent mIntentService;
    private PendingIntent mPendingIntent;
    private ActivityRecognitionClient mActivityRecognitionClient;

    IBinder mBinder = new BackgroundDetectedActivitiesService.LocalBinder();

    public class LocalBinder extends Binder {
        /**
         * Functionality:
         * PreConditions:
         * PostConditions:
         */
        public BackgroundDetectedActivitiesService getServerInstance() {
            return BackgroundDetectedActivitiesService.this;
        }   //end getServerInstance method
    }   //end LocalBinder class

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    public BackgroundDetectedActivitiesService() {
    }   //end BackgroundDetectedActivitiesService method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mActivityRecognitionClient = new ActivityRecognitionClient(this);
        mIntentService = new Intent(this, DetectedActivitiesIntentService.class);
        mPendingIntent = PendingIntent.getService(this, 1, mIntentService, PendingIntent.FLAG_UPDATE_CURRENT);
        requestActivityUpdatesButtonHandler();
    }   //end onCreate method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }   //end onBind method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }   //end onStartCommand method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    public void requestActivityUpdatesButtonHandler() {
        Task<Void> task = mActivityRecognitionClient.requestActivityUpdates(
                Constants.DETECTION_INTERVAL_IN_MILLISECONDS,
                mPendingIntent);

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            /**
             * Functionality:
             * PreConditions:
             * PostConditions:
             */
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(getApplicationContext(),
                        "Successfully requested activity updates",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            /**
             * Functionality:
             * PreConditions:
             * PostConditions:
             */
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Requesting activity updates failed to start",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }   //end requestActivityUpdatesButtonHandler method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    public void removeActivityUpdatesButtonHandler() {
        Task<Void> task = mActivityRecognitionClient.removeActivityUpdates(
                mPendingIntent);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            /**
             * Functionality:
             * PreConditions:
             * PostConditions:
             */
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(getApplicationContext(),
                        "Removed activity updates successfully!",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            /**
             * Functionality:
             * PreConditions:
             * PostConditions:
             */
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to remove activity updates!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }   //end removeActivityUpdatesButtonHandler method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        removeActivityUpdatesButtonHandler();
    }   //end onDestroy
}   //end BackgroundDetectedActivitiesService class

