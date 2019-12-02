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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "states";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "States_Report";
    public static final String KEY_ID = "id";
    public static final String STATE_NAME = "name";
    public static final String STATE_GRADE = "grade";
    public static final String EXTREME_HEAT = "heat";
    public static final String DROUGHT = "Drought";
    public static final String WILDFIRES = "wildfires";
    public static final String INLAND_FLOODING = "inlandflooding";
    public static final String COASTAL_FLOODING = "coastalflooding";

    public static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_NAME + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STATE_NAME + " TEXT, "
            + STATE_GRADE + " TEXT, "
            + EXTREME_HEAT + " TEXT, "
            + DROUGHT + " TEXT, "
            + WILDFIRES + " TEXT, "
            + INLAND_FLOODING + " TEXT, "
            + COASTAL_FLOODING + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //Log.d("table", CREATE_TABLE_STUDENTS);
    }   //end DatabaseHelper constructor

    /**
     * Functionality: Creates tables
     * PreConditions: needs table names and column names
     * PostConditions: Table is created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }   //end onCreate method

    /**
     * Functionality: Drop existing table and create new table if table already exists
     * PreConditions: Database name, oldversion number and newVersion number are required to create a new table
     * PostConditions: Old table should be dropped and new table should be created
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }   //end onUpgrade method

    /*public long addStudentDetail(String state, String grade, String heat, String drought, String wildfires, String iflooding, String cflooding) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(STATE_NAME, state);
        values.put(STATE_GRADE, grade);
        values.put(EXTREME_HEAT, heat);
        values.put(DROUGHT, drought);
        values.put(WILDFIRES, wildfires);
        values.put(INLAND_FLOODING, iflooding);
        values.put(COASTAL_FLOODING, cflooding);
       // insert row in students table
        long insert = db.insert(TABLE_NAME, null, values);

        return insert;
    }*/

    /**
     * Functionality: Get data from database and Update UI
     * PreConditions: requires existing populated database
     * PostConditions: Update reportcard in the UI from the database
     */
    public String[] getData(String state) {
        /*SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from States_Report where name = '"+state+"'", null );
        return res;*/
        String State = state;
        String[] name = new String[7];
        String password;
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {DatabaseHelper.STATE_NAME,DatabaseHelper.STATE_GRADE,DatabaseHelper.EXTREME_HEAT,DatabaseHelper.DROUGHT,DatabaseHelper.WILDFIRES,DatabaseHelper.INLAND_FLOODING,DatabaseHelper.COASTAL_FLOODING};
        Cursor cursor =db.query(DatabaseHelper.TABLE_NAME,columns,"name = '"+state+"' ",null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            name[0] =cursor.getString(cursor.getColumnIndex(DatabaseHelper.STATE_NAME));
            name[1] =cursor.getString(cursor.getColumnIndex(DatabaseHelper.STATE_GRADE));
            name[2] =cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXTREME_HEAT));
            name[3] =cursor.getString(cursor.getColumnIndex(DatabaseHelper.DROUGHT));
            name[4] =cursor.getString(cursor.getColumnIndex(DatabaseHelper.WILDFIRES));
            name[5] =cursor.getString(cursor.getColumnIndex(DatabaseHelper.INLAND_FLOODING));
            name[6] =cursor.getString(cursor.getColumnIndex(DatabaseHelper.COASTAL_FLOODING));
        }


        return name;
    }   //end getData method
}   //end DatabaseHelper class
