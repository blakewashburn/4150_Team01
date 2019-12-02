package edu.cpsc6150.co2ut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

public class MealLogActivity extends AppCompatActivity {
    private static ArrayList<Meal> mealLog;

    /**
     * Functionality: set layout file, create mealLog if necessary, and inflate mealLog fragment
     * PreConditions: savedInstanceState cannot be null
     * PostConditions: mealLog has been populated, mealLog fragment is inflated to the view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_log);

        // create meal log
        if(mealLog == null){
            mealLog = Meal.createMealLog();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.meal_log_fragment_container, new MealLogFragment());
        ft.commit();
    }   //end onCreate method

    /**
     * Functionality: return the mealLog
     * PreConditions: none
     * PostConditions: mealLog is returned to caller
     */
    public static ArrayList<Meal> getMealLog(){
        return mealLog;
    }   //end getMealLog method

    /**
     * Functionality: inflate the app bar
     * PreConditions: menu cannot be null
     * PostConditions: app bar menu is inflated to the view
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_meal_log, menu);
        return super.onCreateOptionsMenu(menu);
    }   //end onCreateOptionsMenu method

    /**
     * Functionality: handle items selected from the app bar
     * PreConditions: item cannot be null
     * PostConditions: AddMealActivity activity is started
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Determine which menu option was chosen
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(MealLogActivity.this, AddMealActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }   //end switch statement
    }   //end onOptionsItemSelected method
}   //end MealLogActivity class
