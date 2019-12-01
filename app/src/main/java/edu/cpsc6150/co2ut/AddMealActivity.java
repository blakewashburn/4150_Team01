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
import android.graphics.LightingColorFilter;
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
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddMealActivity extends AppCompatActivity {

    private final int REQUEST_EXTERNAL_WRITE_PERMISSIONS = 0;
    private final int REQUEST_TAKE_PHOTO = 1;

    private String mPhotoPath;
    private ImageView photoToTake;
    private Button savePhoto;
    public ArrayList<Meal> mealLog;

    
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "stepping into function");
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            displayPhoto();
            addPhotoToGallery();
            savePhoto.setEnabled(true);
        }
    }   //end onActivityResult method

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("onRequestPermissionResult", "stepping into function");
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


    private boolean hasExternalWritePermission() {
        Log.d("hasExternalWritePermission", "stepping into function");

        // Get permission to write to external storage
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this,
                permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { permission }, REQUEST_EXTERNAL_WRITE_PERMISSIONS);
            return false;
        }

        return true;
    }

    public void takePhoto(View view) {
        Log.d("takePhoto", "stepping into function");
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
    }

    private File createImageFile() throws IOException {
        Log.d("createImageFile", "stepping into function");

        // Create a unique image filename
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFilename = "photo_" + timeStamp + ".jpg";

        // Create the file in the Pictures directory on external storage
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFilename);
        return image;
    }

    private void displayPhoto() {
        Log.d("displayPhoto", "stepping into function");
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
    }

    private void addPhotoToGallery() {
        Log.d("addPhotoToGallery", "stepping into function");

        // Send broadcast to Media Scanner about new image file

        //TODO: add photo to MealLog list
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(mPhotoPath);
        Uri fileUri = Uri.fromFile(file);
        mediaScanIntent.setData(fileUri);
        sendBroadcast(mediaScanIntent);
    }

    public void savePhoto(View view) {
        Log.d("savePhoto", "stepping into function");
        // Don't allow Save button to be pressed while image is saving
        savePhoto.setEnabled(false);

        // Save in background thread
        SaveBitmapTask saveTask = new SaveBitmapTask();
        saveTask.execute();
    }

    private boolean saveAlteredPhoto() {
        Log.d("saveAlteredPhoto", "stepping into function");
        // Read original image
        Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath, null);

        // Create a new bitmap with the same dimensions as the original
        Bitmap alteredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap
                .getConfig());

        // Draw original bitmap on canvas and apply the color filter
        Canvas canvas = new Canvas(alteredBitmap);
        Paint paint = new Paint();
        //LightingColorFilter colorFilter = new LightingColorFilter(mMultColor, mAddColor);
        //paint.setColorFilter(colorFilter);
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
        }
    }

    // NOTE: Progress bar should be added
    private class SaveBitmapTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            return saveAlteredPhoto();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(AddMealActivity.this, "photo saved", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(AddMealActivity.this, "photo not saved", Toast.LENGTH_LONG).show();
            }

            savePhoto.setEnabled(true);
        }
    }

}   //end AddMealActivity class
