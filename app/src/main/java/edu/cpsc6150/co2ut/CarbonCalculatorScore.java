package edu.cpsc6150.co2ut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.lang.*;

public class CarbonCalculatorScore extends AppCompatActivity {
    public final int WEEKS_IN_A_YEAR = 52;
    public final int MONTHS_IN_A_YEAR = 12;
    public final double GAS_GALLONS_TO_CO2_KGS = 8.8;
    public final double KW_HOURS_TO_CO2_KGS = 0.7;
    public final double LBS_MEAT_TO_CO2_KGS = 4.67;
    public final double GARBAGE_BAGS_TO_CO2_KGS = 400;
    public final double KGS_IN_METRIC_TON = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_calculator_score);

        

        // Get answers from CarbonCalculatorActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle == null){
            return;
        }
        double answer1 = bundle.getDouble("answer1");
        Log.d("First", "answer1 is:" + answer1);
        double answer2 = bundle.getDouble("answer2");
        Log.d("First", "answer2 is:" + answer2);
        double answer3 = bundle.getDouble("answer3");
        Log.d("First", "answer3 is:" + answer3);
        double answer4 = bundle.getDouble("answer4");
        Log.d("First", "answer4 is:" + answer4);

        // Run conversions
        double answer1YearTotal = answer1 * WEEKS_IN_A_YEAR * GAS_GALLONS_TO_CO2_KGS;
        Log.d("Temp", "answer1 is:" + answer1);
        double answer2YearTotal = answer2 * MONTHS_IN_A_YEAR * KW_HOURS_TO_CO2_KGS;
        Log.d("Temp", "answer2 is:" + answer2);
        double answer3YearTotal = answer3 * WEEKS_IN_A_YEAR * LBS_MEAT_TO_CO2_KGS;
        Log.d("Temp", "answer3 is:" + answer3);
        double answer4YearTotal = answer4 * WEEKS_IN_A_YEAR * GARBAGE_BAGS_TO_CO2_KGS;
        Log.d("Temp", "answer4 is:" + answer4);

        double totalCarbonFootprint = (answer1YearTotal + answer2YearTotal +
                                      answer3YearTotal + answer4YearTotal) / KGS_IN_METRIC_TON;
        String display = Double.toString(totalCarbonFootprint);

        // Set users carbon footprint
        TextView carbonFootprintDisplay = findViewById(R.id.carbon_footprint_score);
        carbonFootprintDisplay.setText(display);
    }   //end onCreate Method
}   //end CarbonCalculatorScore Class
