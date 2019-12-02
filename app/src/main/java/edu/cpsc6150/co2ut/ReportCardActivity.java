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

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportCardActivity extends AppCompatActivity {
    private static final String TAG = ReportCardActivity.class.getSimpleName();

    @BindView(R.id.location_result)
    TextView txtLocationResult;

    @BindView(R.id.btn_start_location_updates)
    Button btnStartUpdates;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    private Boolean mRequestingLocationUpdates;

    private DatabaseHelper databaseHelper;
    private TextView statedisplay, gradedisplay, heat, drought, wildfires, inlandFlooding,coastalFlooding;

    /**
     * Functionality: Instantiates the DatabaseHelper class and other UI Components like textviews
     * PreConditions: DatabaseHelper and textviews must be declared
     * PostConditions: Textviews are assigned and DatabaseHelper initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_card);
        ButterKnife.bind(this);


        databaseHelper = new DatabaseHelper(this);
        statedisplay = (TextView) findViewById(R.id.statedisplay);
        gradedisplay = (TextView) findViewById(R.id.gradedisplay);
        heat = (TextView) findViewById(R.id.heat);
        drought = (TextView) findViewById(R.id.drought);
        wildfires = (TextView) findViewById(R.id.wildfires);
        inlandFlooding = (TextView) findViewById(R.id.inlandFlooding);
        coastalFlooding = (TextView) findViewById(R.id.coastalFlooding);
        // initialize the necessary libraries
        init();

        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);
    }   //end onCreate method


    /**
     * Functionality: Initializes the necessary libraries to get location updates
     * PreConditions: Library's required for the location updates must be declared
     * PostConditions: All necessary Libraries are intialized
     */
    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            /**
             * Functionality: Updates Last location on receiving location updates
             * PreConditions: Needs to receive locationResult as a parameter
             * PostConditions: Return updated last location to the UI
             */

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();


                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }   //end init method

    /**
     * Functionality: Restores the savedState of the activity
     * PreConditions: Needs the instance state to be saved
     * PostConditions: Retrieve the saved state and update the UI
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }
        }
        updateLocationUI();
    }   //end restoreValuesFromBundle method



    /**
     * Functionality: Update the UI displaying the location data
     * PreConditions: Requires the current location
     * PostConditions: Updates the UI with the current location and the states report card from the database
     */
    String state;
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            try{
                List<Address> addresses = gcd.getFromLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 1);
                if (addresses.size() > 0) {
                    state = addresses.get(0).getAdminArea();
                    String output[] = databaseHelper.getData(state);
                    statedisplay.setText("  State: "+output[0]);
                    gradedisplay.setText("  Average: "+output[1]);
                    heat.setText("  Extreme Heat: "+output[2]);
                    drought.setText("  Drought: "+output[3]);
                    wildfires.setText("  Wildfires: "+output[4]);
                    inlandFlooding.setText("  Inland Flooding: "+output[5]);
                    coastalFlooding.setText("  Coastal Flooding: "+output[6]);
                    /*Cursor rs = databaseHelper.getData(state);
                    if (rs.moveToFirst()){
                        // do the work

                        String username = rs.getString(1);
                        String password = rs.getString(2);

                        //String nam = rs.getString(rs.getColumnIndex(DatabaseHelper.STATE_NAME));
                        //String phon = rs.getString(rs.getColumnIndex(DatabaseHelper.STATE_GRADE));

                        statedisplay.setText(username);
                        gradedisplay.setText(password);

                    }*/

                    //arrayList = databaseHelper.getAllStudentsList();
                    //tvnames.setText("");
                /*for (int i = 0; i < arrayList.size(); i++){
                     tvnames.setText(tvnames.getText().toString()+", "+arrayList.get(i));
                }*/

                }
                else {
                    // do your stuff
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }   //end try-catch block
            txtLocationResult.setText(
                    getString(R.string.welcome_message,state)
            );


            // location last updated time
        }
    }   //end updateLocationUI method

    /**
     * Functionality: Saves the Activities current state
     * PreConditions: Needs the values of mCurrentLocation and mRequestingLocationUpdates
     * PostConditions: The Instance state should be saved to the Bundle
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
    }   //end onSaveInstanceState method




    /**
     * Functionality: Starting location updates
     * PreConditions: Check whether location settings are satisfied
     * PostConditions: Location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }   //end onSuccess method
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(ReportCardActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }   //end try-catch block
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(ReportCardActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }   //end switch statement
                        updateLocationUI();
                    }   //end onFailure method
                });
    }   //end startLocationUpdates method

    /**
     * Functionality: OnClick will start receiving location updates
     * PreConditions: Permissions must be granted
     * PostConditions: Location Updates started
     */
    @OnClick(R.id.btn_start_location_updates)
    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {

                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }   //end onPermissionGranted method

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }   //end onPermuissionDenied method

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }   //end onPermissionRationaleShouldBeShown method
                }).check();
    }   //end startLocationButtonClick method


    /**
     * Functionality: Stop or pause location updates when the app pauses
     * PreConditions: FusedLocationClient must be intialized
     * PostConditions: Stop Location updates
     */
    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    /**
                     * Functionality:
                     * PreConditions:
                     * PostConditions:
                     */
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();

                    }
                });
    }   //end stopLocationUpdates method

    /**
     * Functionality: Check whether user has granted permissions or not
     * PreConditions: Needs requestCode, resultCode and data as parameters
     * PostConditions: Log the ActivityResult as an error log
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }   //end switch statement
                break;
        }   //end switch statement
    }   //end onActivityResult method

    /**
     * Functionality: Open settings if permissions is denied
     * PreConditions: Settings URI
     * PostConditions: Open Application details settings page
     */
    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }   //end openSettings method

    /**
     * Functionality: Check for permissions again onResume
     * PreConditions: App must be paused or stopped before this function is called
     * PostConditions: onResume the locationupdates must be resume and the UI must be updated
     */
    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }
        updateLocationUI();
    }   //end onResume()

    /**
     * Functionality: Checks if permission to access location is granted
     * PreConditions: Permission must be mentioned in the manifest file
     * PostConditions: Return permission granted if Uses-Permission is mentioned in the activity file
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }   //end checkPermissions method

    /**
     * Functionality: Stops location updates when the application is paused
     * PreConditions: Application must be paused
     * PostConditions: Location updates must be stopped
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }   //end onPause method
}   //end ReportCardActivity class
