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
     * Functionality:
     * PreConditions:
     * PostConditions:
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
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    public static File getMealLogFile(){
        return mealLogFile;
    }

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    private View.OnClickListener detectActivityListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, DetectActivity.class));
        }   //end onClick
    };  //end detectActivitiyListener

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    private View.OnClickListener calcCarbonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, CarbonCalculatorActivity.class));
        }   //end onClick
    };  //end calcCarbonListener

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    private View.OnClickListener mealLogListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, MealLogActivity.class));
        }   //end onClick
    };  //end mealLogListener

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    private View.OnClickListener reportCardListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, ReportCardActivity.class));
        }   //end onClick
    };  //end reportCardListener
}   //end MainActivityClass
