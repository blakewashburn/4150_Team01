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


    /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/
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

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //Log.d("table", CREATE_TABLE_STUDENTS);
    }   //end DatabaseHelper constructor

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }   //end onCreate method

    /**
     * Functionality:
     * PreConditions:
     * PostConditions:
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
     * Functionality:
     * PreConditions:
     * PostConditions:
     */
    public String[] getData(String state) {
        /*SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from States_Report where name = '"+state+"'", null );
        return res;*/
        String State = state;
        String[] name = new String[2];
        String password;
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {DatabaseHelper.STATE_NAME,DatabaseHelper.STATE_GRADE};
        Cursor cursor =db.query(DatabaseHelper.TABLE_NAME,columns,"name = '"+state+"' ",null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            name[0] =cursor.getString(cursor.getColumnIndex(DatabaseHelper.STATE_NAME));
            name[1] =cursor.getString(cursor.getColumnIndex(DatabaseHelper.STATE_GRADE));
        }   //end while loop

        return name;
    }   //end getData method
}   //end DatabaseHelper class
