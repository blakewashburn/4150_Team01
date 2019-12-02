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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddMealActivity extends AppCompatActivity {

    private final int REQUEST_EXTERNAL_WRITE_PERMISSIONS = 0;
    private final int REQUEST_TAKE_PHOTO = 1;

    private String mPhotoPath;
    private ImageView photoToTake;
    private Button savePhoto;
    private String date;
    private String mealType = "Meat";
    private String impactScore = "5";
    public ArrayList<Meal> mealLog;

    /**
     * Functionality: onCreate sets the layout file and connects front end widgets to the backend
     * PreConditions: none
     * PostConditions: mealLog is set to the mealLog from MainActivity, savePhoto button cannot
     *                  be interacted with.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        photoToTake = findViewById(R.id.add_photo_meal_log);
        savePhoto = findViewById(R.id.save_photo_button);
        savePhoto.setEnabled(false);

        // get current status of mealLog
        mealLog = MealLogActivity.getMealLog();
    }   //end onCreate Method

    /**
     * Functionality: Receives photo and starts the process of saving photo and displaying to user
     * PreConditions: requestCode, resultCode, data cannot be null
     * PostConditions: photo is displayed to user, date photo is taken is saved, save photo button
     *                  can be interacted with now
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            date =  new SimpleDateFormat("MM/dd/yyyy").format(new Date());
            displayPhoto();
            addPhotoToGallery();
            savePhoto.setEnabled(true);
        }
    }   //end onActivityResult method

    /**
     * Functionality: receives permission to use camera from user and calls takePhoto to capture an
     *                  image for the user
     * PreConditions: requestcode, permissions, grantResults cannot be null
     * PostConditions: photo is taken
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_WRITE_PERMISSIONS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto(null);
                }
                return;
            }
        }   //end switch statement
    }   //end onRequestPermissionResult method

    /**
     * Functionality: checks if permissions are granted to user camera. if not, requests permission
     * PreConditions: none
     * PostConditions: returns true if permissions are granted. Otherwise, function returns false
     */
    private boolean hasExternalWritePermission() {
        // Get permission to write to external storage
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this,
                permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { permission }, REQUEST_EXTERNAL_WRITE_PERMISSIONS);
            return false;
        }
        return true;
    }   //end hasExternalWritePermission method

    /**
     * Functionality: facilitates the capturing of an image for the user
     * PreConditions: view cannot be null
     * PostConditions: Camera is opened to capture an image, space is reserved to save the image
     */
    public void takePhoto(View view) {
        if (!hasExternalWritePermission()) return;

        // Create implicit intent to take photo
        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoCaptureIntent.resolveActivity(getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                mPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }

            // If the File was successfully created, start camera app
            if (photoFile != null) {

                // Create content URI to grant camera app write permission to photoFile
                Uri photoUri = FileProvider.getUriForFile(this,
                        "edu.cpsc6150.co2ut.fileprovider",
                        photoFile);

                // Add content URI to intent and start camera app
                photoCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(photoCaptureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }   //end takePhoto method

    /**
     * Functionality: sets apart storage to use later to store an image in
     * PreConditions: none
     * PostConditions: returns a storage location where an image can be saved
     */
    private File createImageFile() throws IOException {
        // Create a unique image filename
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFilename = "photo_" + timeStamp + ".jpg";

        // Create the file in the Pictures directory on external storage
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFilename);
        return image;
    }   //end createImageFile method

    /**
     * Functionality: displays the captured image back to the user for view
     * PreConditions: photoToTake && mPhotoPath cannot be null
     * PostConditions: photoToTake is set for viewing
     */
    private void displayPhoto() {
        // Get ImageView dimensions
        int targetW = photoToTake.getWidth();
        int targetH = photoToTake.getHeight();

        // Get bitmap dimensions
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a smaller bitmap that fills the ImageView
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath, bmOptions);

        // Display smaller bitmap
        photoToTake.setImageBitmap(bitmap);
    }   //end displayPhoto method

    /**
     * Functionality: Photo is added to photo gallery
     * PreConditions: mPhotoPath cannot be null
     * PostConditions: broadcast is sent to Media Scanner with new image file
     */
    private void addPhotoToGallery() {
        // Send broadcast to Media Scanner about new image file
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(mPhotoPath);
        Uri fileUri = Uri.fromFile(file);
        mediaScanIntent.setData(fileUri);
        sendBroadcast(mediaScanIntent);
    }   //end addPhotoToGallery method

    /**
     * Functionality: Save photo using SaveBitmapTask and save meal to internal storage
     * PreConditions: savePhoto && view cannot be null
     * PostConditions: meal is added to mealLog, MealLogActivity is started for user to view their
     *                  updated mealLog
     */
    public void savePhoto(View view) {
        // Don't allow Save button to be pressed while image is saving
        savePhoto.setEnabled(false);

        Meal newMeal = new Meal(photoToTake.getDrawable(), date, mealType, impactScore);
        saveObjectInternally(newMeal);
        mealLog.add(0, newMeal);
        // Save in background thread
        SaveBitmapTask saveTask = new SaveBitmapTask();
        saveTask.execute();

        Intent intent = new Intent(AddMealActivity.this, MealLogActivity.class);
        startActivity(intent);
    }   //end savePhoto method

    /**
     * Functionality: Save changes to Photo if necessary
     * PreConditions: mPhotoPath cannot be null
     * PostConditions: Photo is saved to imageFile
     */
    private boolean saveAlteredPhoto() {
        // Read original image
        Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath, null);

        // Create a new bitmap with the same dimensions as the original
        Bitmap alteredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap
                .getConfig());

        // Draw original bitmap on canvas and apply the color filter
        Canvas canvas = new Canvas(alteredBitmap);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, 0, 0, paint);

        // Save altered bitmap over the original image
        File imageFile = new File(mPhotoPath);
        try {
            FileOutputStream outStream = new FileOutputStream(imageFile);
            alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }   //end try-catch
    }   //end saveAlteredPhoto method

    /**
     * Functionality: saves meal to internal storage for retrieval by MealLogActivity
     * PreConditions: meal cannot be null
     * PostConditions: meal is saved to mealLogFile from MainActivity
     */
    private void saveObjectInternally(Meal meal){
        File storageDir = MainActivity.getMealLogFile();

        try {
            // Saving of object in a file
            FileOutputStream file = new FileOutputStream(storageDir);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // serialize object
            out.writeObject(meal);

            out.close();
            file.close();

        }catch(IOException ex) {
            Log.d("AddMealActivity - saveObjectInternally", "Exception Caught");
        }   //end try-catch block
    }   //end saveObjectInternally method

    private class SaveBitmapTask extends AsyncTask<Void, Void, Boolean> {

        /**
         * Functionality: call saveAlteredPhoto to save the captured image
         * PreConditions: none
         * PostConditions: photo is saved
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            return saveAlteredPhoto();
        }   //end doInBackground method

        /**
         * Functionality: inform the user of the storage status of the photo
         * PreConditions: result cannot be null
         * PostConditions: savePhoto button can be interacted with
         */
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(AddMealActivity.this, "photo saved", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(AddMealActivity.this, "photo not saved", Toast.LENGTH_LONG).show();
            }
            savePhoto.setEnabled(true);
        }   //end onPostExecute method
    }   //end SaveBitmapTask Class

    /**
     * Functionality: update mealType and impactScore variables based on choices of the user
     * PreConditions: view cannot be null
     * PostConditions: mealType && impactScore are updated
     */
    public void onRadioButtonClicked(View view) {
        // Which radio button was selected?
        switch (view.getId()) {
            case R.id.radio_button_meat:
                mealType = "Meat";
                impactScore = "5";
                break;
            case R.id.radio_button_starch:
                mealType = "Starch";
                impactScore = "3";
                break;
            case R.id.radio_button_vegetable:
                mealType = "Vegetable";
                impactScore = "1";
                break;
            case R.id.radio_button_fruit:
                mealType = "Fruit";
                impactScore = "2";
                break;
        }   //end switch statement
    }   //end onRadioButtonClicked method
}   //end AddMealActivity class
