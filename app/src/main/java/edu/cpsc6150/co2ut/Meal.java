package edu.cpsc6150.co2ut;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class Meal {
    private Drawable image;
    private String date;
    private String mealType;
    private String impactScore;

    public Meal(Drawable image, String date, String mealType, String impactScore){
        this.image = image;
        this.date = date;
        this.mealType = mealType;
        this.impactScore = impactScore;
    }   //end Meal Constructor

    // Getters and Setters for private variables
    public Drawable getImage() { return image; }
    public void setImage(Drawable image) { this.image = image; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }
    public String getImpactScore() { return impactScore; }
    public void setImpactScore(String impactScore) { this.impactScore = impactScore; }

    public static ArrayList<Meal> createMealLog(Context context) {
        ArrayList<Meal> MealLog = new ArrayList<>();

        // Create test data
        Drawable applesDrawable = ContextCompat.getDrawable(context, R.drawable.apples);
        Drawable orangesDrawable = ContextCompat.getDrawable(context, R.drawable.orange);
        Drawable watermelonDrawable = ContextCompat.getDrawable(context, R.drawable.watermelon);
        Meal meal1 = new Meal(applesDrawable, "11/20/1997 at 4:30 PM", "fruit", "2");
        Meal meal2 = new Meal(orangesDrawable, "05/21/1997 at 7:00 PM", "fruit", "2");
        Meal meal3 = new Meal(watermelonDrawable, "01/01/2014 at 7:00 AM", "fruit", "2");
        MealLog.add(meal1);
        MealLog.add(meal2);
        MealLog.add(meal3);
        return MealLog;
    }   //end createMealLog method
}   //end Meal class
