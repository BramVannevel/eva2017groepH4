package com.projecten3.eva.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.projecten3.eva.Model.Day;

import java.util.ArrayList;

/**
 * Created by Desktop Ben on 15/08/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private String[] allColumns = {COLUMN_DAYOFTHEWEEK,COLUMN_STATE,COLUMN_DAY};
    private SQLiteDatabase db;
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 14;
    public static final String DATABASE_NAME = "progress.db";

    public static final String TABLE_PROGRESS = "progress";
    public static final String COLUMN_DAYOFTHEWEEK = "dayOfTheWeek";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_DAY = "day";

    private static final String DB_CREATE = "create table " + TABLE_PROGRESS + "(" + COLUMN_DAYOFTHEWEEK + " text not null, " + COLUMN_DAY + " integer not null, " +
            COLUMN_STATE + " integer not null );";
    private static final String DB_DROP = "drop table " + TABLE_PROGRESS + ";";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS);
        onCreate(db);
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void dropTable(){
        db = this.getWritableDatabase();
        try {
            db.execSQL("delete from " + TABLE_PROGRESS);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public ArrayList<Day> getAll() {
        ArrayList<Day> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from progress", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Day post = cursorToData(res);
            array_list.add(post);
            res.moveToNext();
        }
        return array_list;
    }

    public boolean insertProgress (String id, String day, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DAYOFTHEWEEK,id);
        contentValues.put(COLUMN_DAY,day);
        contentValues.put(COLUMN_STATE,state);

        db.insert(TABLE_PROGRESS, null, contentValues);
        return true;
    }

    private Day cursorToData(Cursor cursor) {
        Day post = new Day();

        post.setDayOfTheWeek(cursor.getString(0));
        post.setWhichDayOfTheChallenge(cursor.getInt(1));
        post.setCompleted(cursor.getInt(2));

        return post;
    }

    public boolean updateState(String day, int state){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATE, state);
        db.update(TABLE_PROGRESS, contentValues, "day = ?", new String[] { day });
        return true;
    }

    public Day getDay(int dag){
        Day day = new Day();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from progress where day = '" + dag + "';", null);

        if(res.moveToFirst()){
            Log.e("cursor","niet empty");
        } else {
            Log.e("cursor","empty");
        }
        day = cursorToData(res);
        return day;
    }


}
