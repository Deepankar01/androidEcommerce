package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Abhinav on 6/1/2015.
 */

/**
 * This class takes care of storing the user data in SQLite database.
 * Whenever we need to get the logged in user information, we fetch from SQLite instead of making request to server.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    //Lifecycle Log.d TAG
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    /*All static variables*/
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "android_api";

    //Login table name
    private static final String TABLE_LOGIN = "login";

    //Login table columns name
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_UID = "uid";
    public static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Used for creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY , "
                + KEY_UID + " TEXT , "
                + KEY_NAME + " TEXT , "
                + KEY_EMAIL + " TEXT UNIQUE , "
                + KEY_CREATED_AT + " TEXT " +
                ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG,"Database tables created");
    }

    //To upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //First drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOGIN);

        //Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * @param name
     * @param email
     * @param uid
     * @param created_at
     */
    public void addUser(String name,String email,String uid,String created_at) {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_EMAIL,email);
        values.put(KEY_UID,uid);
        values.put(KEY_CREATED_AT,created_at);

        //Insert row
        long rowId=db.insert(TABLE_LOGIN, null,values);
        //Close db connection
        db.close();

        Log.d(TAG,"New user inserted into SQLITE:"+rowId);
    }

    /**
     * Getting user data from database
     * @return userDetails HashMap
     */
    public HashMap<String,String> getUserDetails() {
        HashMap<String ,String> user=new HashMap<String, String>();
        String selectQuery = "SELECT * FROM "+TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //Now move to first row
        cursor.moveToFirst();
        //getCount returns the no of rows in the cursor
        if(cursor.getCount()>0) {
            user.put("uid",cursor.getString(1));
            user.put("name",cursor.getString(2));
            user.put("email",cursor.getString(3));
            user.put("created_at",cursor.getString(4));
        }
        else
            Log.d(TAG,getRowCount()+" ");
        cursor.close();
        db.close();

        Log.d(TAG,"Fetched user from SQLite= "+user.toString());

        //return " user " Hashmap
        return user;
    }

    /**
     * To determine user login status. If rows are there in the table? logged in : not logged
     * @return loginStatus INT
     */
    public int getRowCount() {
        String countQuery="SELECT * FROM "+TABLE_LOGIN;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        int rowCount = cursor.getCount();

        db.close();
        cursor.close();

        //return rowCount
        return rowCount;
    }

    /**
     * Function to delete table containing user details
     */
    public void deleteUsers() {
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(TABLE_LOGIN,null,null);
        db.close();

        Log.d(TAG,"Deleted all user info from sqlite.");
    }
}