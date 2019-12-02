package edu.cpsc6150.co2ut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MealLogActivity extends AppCompatActivity {
    private Menu menu;
    private static ArrayList<Meal> mealLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_log);

        // create meal log
        if(mealLog == null){
            mealLog = Meal.createMealLog(this);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.meal_log_fragment_container, new MealLogFragment());
        ft.commit();

    }   //end onCreate method

    public static ArrayList<Meal> getMealLog(){
        return mealLog;
    }   //end getMealLog method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_meal_log, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

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
        }
    }
}   //end MealLogActivity class
