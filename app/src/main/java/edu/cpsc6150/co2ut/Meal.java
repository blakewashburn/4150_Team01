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

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.io.*;
import java.util.ArrayList;

public class Meal implements java.io.Serializable{
    private Drawable image;
    private String date;
    private String mealType;
    private String impactScore;

    /**
     * Functionality: create a Meal object and assign internal variables
     * PreConditions: image && date && mealType && impactScore cannot be null
     * PostConditions: Meal object is created and internal variables are set
     */
    public Meal(Drawable image, String date, String mealType, String impactScore){
        this.image = image;
        this.date = date;
        this.mealType = mealType;
        this.impactScore = impactScore;
    }   //end Meal Constructor

    // Getters and Setters for private variables
    public Drawable getImage() { return image; }
    public String getDate() { return date; }
    public String getMealType() { return mealType; }
    public String getImpactScore() { return impactScore; }

    /**
     * Functionality: Create a mealLog and populate it with stored photos
     * PreConditions: none
     * PostConditions: mealLog is populated with user's stored meals
     */
    public static ArrayList<Meal> createMealLog() {
        ArrayList<Meal> mealLog = new ArrayList<>();
        File storageDir = MainActivity.getMealLogFile();
        Meal tempMeal;

        try{
            // Reading the object from a file
            FileInputStream file = new FileInputStream(storageDir);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            while(file.available() > 0){
                tempMeal = (Meal)in.readObject();
                mealLog.add(tempMeal);
            }

            in.close();
            file.close();
        }catch(IOException ex){
            System.out.println("IOException is caught");
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException is caught");
        }   //end try-catch block
        return mealLog;
    }   //end createMealLog method
}   //end Meal class
