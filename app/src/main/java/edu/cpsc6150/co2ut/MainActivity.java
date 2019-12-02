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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static File mealLogFile;

    /**
     * Functionality: sets layout file, connects front end widgets with backend
     * PreConditions: none
     * PostConditions: mealLog file is created to save future meals
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button detectActivity = findViewById(R.id.detect_activity_button_homepage);
        Button calcCarbonFootprint = findViewById(R. id.carbon_calc_button_homepage);
        Button mealLog = findViewById(R.id.meal_log_button_homepage);
        Button reportCard = findViewById(R.id.report_card_button_homepage);

        detectActivity.setOnClickListener(detectActivityListener);
        calcCarbonFootprint.setOnClickListener(calcCarbonListener);
        mealLog.setOnClickListener(mealLogListener);
        reportCard.setOnClickListener(reportCardListener);

        //check if Meal log file already exists, if not create meal log file
        File rootFile = getFilesDir();
        String mealLogFileName = rootFile.toString() + "/MealLog";
        mealLogFile = new File(mealLogFileName);
        if(!mealLogFile.exists()){
            mealLogFile.mkdir();
        }
    }   //end onCreate method

    /**
     * Functionality: get mealLog file
     * PreConditions: none
     * PostConditions: mealLog file is returned to caller
     */
    public static File getMealLogFile(){
        return mealLogFile;
    }


    private View.OnClickListener detectActivityListener = new View.OnClickListener(){
        /**
         * Functionality: handle a click of the detectActivity button
         * PreConditions: view cannot be null
         * PostConditions: DetectActivity activity is started
         */
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, DetectActivity.class));
        }   //end onClick
    };  //end detectActivitiyListener

    private View.OnClickListener calcCarbonListener = new View.OnClickListener(){
        /**
         * Functionality: handle a click of the calcCarbonFootprint button
         * PreConditions: view cannot be null
         * PostConditions: CarbonCalculatorActivity activity is started
         */
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, CarbonCalculatorActivity.class));
        }   //end onClick
    };  //end calcCarbonListener

    private View.OnClickListener mealLogListener = new View.OnClickListener(){
        /**
         * Functionality: handle a click of the mealLog button
         * PreConditions: view cannot be null
         * PostConditions: MealLogActivity activity is started
         */
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, MealLogActivity.class));
        }   //end onClick
    };  //end mealLogListener

    private View.OnClickListener reportCardListener = new View.OnClickListener(){
        /**
         * Functionality: handle a click of the reportCard button
         * PreConditions: view cannot be null
         * PostConditions: ReportCardActivity activity is started
         */
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, ReportCardActivity.class));
        }   //end onClick
    };  //end reportCardListener
}   //end MainActivityClass
