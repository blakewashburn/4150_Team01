package edu.cpsc6150.co2ut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calcCarbonFootprint = findViewById(R. id.carbon_calc_button_homepage);
        Button mealLog = findViewById(R.id.meal_log_button_homepage);
        Button reportCard = findViewById(R.id.report_card_button_homepage);

        calcCarbonFootprint.setOnClickListener(calcCarbonListener);
        mealLog.setOnClickListener(mealLogListener);
        reportCard.setOnClickListener(reportCardListener);
    }

    // Carbon Footprint Calculator Listener
    private View.OnClickListener calcCarbonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, CarbonCalculatorActivity.class));
        }   //end onClick
    };  //end aboutButtonClickListener

    // Meal Log Listener
    private View.OnClickListener mealLogListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, MealLogActivity.class));
        }   //end onClick
    };  //end aboutButtonClickListener

    // Location Based Report Card Listener
    private View.OnClickListener reportCardListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            startActivity(new Intent(MainActivity.this, ReportCardActivity.class));
        }   //end onClick
    };  //end aboutButtonClickListener
}
