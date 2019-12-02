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
     * Functionality:
     * PreConditions:
     * PostConditions:
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
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    public static ArrayList<Meal> createMealLog(Context context) {
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
