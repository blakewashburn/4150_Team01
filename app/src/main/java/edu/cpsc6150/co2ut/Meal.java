package edu.cpsc6150.co2ut;


import android.widget.ImageView;
import java.util.ArrayList;

public class Meal {
    private ImageView image;
    private String date;
    private String mealPictured;
    private int impactScore;

    // Getters and Setters for private variables
    public ImageView getImage() { return image; }
    public void setImage(ImageView image) { this.image = image; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getMealPictured() { return mealPictured; }
    public void setMealPictured(String mealPictured) { this.mealPictured = mealPictured; }
    public int getImpactScore() { return impactScore; }
    public void setImpactScore(int impactScore) { this.impactScore = impactScore; }

    public static ArrayList<Meal> createMealLog() {
        ArrayList<Meal> MealLog = new ArrayList<>();
        return MealLog;
    }   //end createMealLog method
}   //end Meal class
