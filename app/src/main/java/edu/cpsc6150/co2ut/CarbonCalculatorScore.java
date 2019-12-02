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

    /**
     * Functionality: Calculate and display users carbon footprint score
     * PreConditions: savedInstanceState cannot be null
     * PostConditions: carbonFootprintDisplay is updated with user's calculated carbon footprint
     */
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
        double answer2 = bundle.getDouble("answer2");
        double answer3 = bundle.getDouble("answer3");
        double answer4 = bundle.getDouble("answer4");

        // Run conversions
        double answer1YearTotal = answer1 * WEEKS_IN_A_YEAR * GAS_GALLONS_TO_CO2_KGS;
        double answer2YearTotal = answer2 * MONTHS_IN_A_YEAR * KW_HOURS_TO_CO2_KGS;
        double answer3YearTotal = answer3 * WEEKS_IN_A_YEAR * LBS_MEAT_TO_CO2_KGS;
        double answer4YearTotal = answer4 * WEEKS_IN_A_YEAR * GARBAGE_BAGS_TO_CO2_KGS;

        double totalCarbonFootprint = (answer1YearTotal + answer2YearTotal +
                                      answer3YearTotal + answer4YearTotal) / KGS_IN_METRIC_TON;
        String display = Double.toString(totalCarbonFootprint);

        // Set users carbon footprint
        TextView carbonFootprintDisplay = findViewById(R.id.carbon_footprint_score);
        carbonFootprintDisplay.setText(display);
    }   //end onCreate Method
}   //end CarbonCalculatorScore Class
