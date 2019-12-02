package edu.cpsc6150.co2ut;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CarbonCalculatorActivity extends AppCompatActivity {
    // Class Variables
    public EditText answer1View;
    public EditText answer2View;
    public EditText answer3View;
    public EditText answer4View;
    public Button submitButton;

    /**
     * Functionality: sets layout file, connects front end widgets with backend
     * PreConditions: none
     * PostConditions: widgets are set and submitButton receives a listener
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_calculator);

        // connect front-end EditText views to EditText values on back-end
        answer1View = findViewById(R.id.cf_calc_answer1);
        answer2View = findViewById(R.id.cf_calc_answer2);
        answer3View = findViewById(R.id.cf_calc_answer3);
        answer4View = findViewById(R.id.cf_calc_answer4);
        submitButton = findViewById(R.id.cf_calc_submit_button);
        submitButton.setOnClickListener(submitListener);
    }   //end onCreate method

    private View.OnClickListener submitListener = new View.OnClickListener(){
        /**
         * Functionality: save user answers and pass to CarbonCalculatorScore activity
         * PreConditions: view && answer1View && answer2View && answer3View && answer4View
         *                  cannot be null
         * PostConditions: answers are saved in bundle and CarbonCalculatorScore activity is started
         */
        @Override
        public void onClick(View view){
            // Check that user gave answers for all questions
            if(answer1View.getText().toString().equals("") ||
               answer2View.getText().toString().equals("") ||
               answer3View.getText().toString().equals("") ||
               answer4View.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Please answer all questions before hitting submit", Toast.LENGTH_SHORT).show();
                return;
            }else {
                // Pull out user's answers and send to CarbonCalculatorScore activity
                double answer1 = Double.parseDouble(answer1View.getText().toString());
                double answer2 = Double.parseDouble(answer2View.getText().toString());
                double answer3 = Double.parseDouble(answer3View.getText().toString());
                double answer4 = Double.parseDouble(answer4View.getText().toString());
                Intent intent = new Intent(CarbonCalculatorActivity.this, CarbonCalculatorScore.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("answer1", answer1);
                bundle.putDouble("answer2", answer2);
                bundle.putDouble("answer3", answer3);
                bundle.putDouble("answer4", answer4);
                intent.putExtras(bundle);
                startActivity(intent);
            }   //end if-else
        }   //end onClick method
    };  //end calcCarbonListener method
}   //end CarbonCalculatorActivity class
