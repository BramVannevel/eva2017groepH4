package com.projecten3.eva.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.projecten3.eva.Model.DatabaseEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desktop Ben on 15/08/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private String[] allColumns = {COLUMN_CHALLENGE,COLUMN_STATE,COLUMN_DAY};
    private SQLiteDatabase db;
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "progress.db";

    public static final String TABLE_PROGRESS = "progress";
    public static final String COLUMN_CHALLENGE = "challenge";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_DAY = "day";

    private static final String DB_CREATE = "create table " + TABLE_PROGRESS + "(" + COLUMN_CHALLENGE + " text not null, " + COLUMN_DAY + " text not null, " +
            COLUMN_STATE + " text not null );";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
        //deleteAll();

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


    public boolean createProgress(String id, String state, String day){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_CHALLENGE,id);
        values.put(COLUMN_DAY,day);
        values.put(COLUMN_STATE,state);
        db.insert(TABLE_PROGRESS,null,values);
        return true;
    }


    public ArrayList<DatabaseEntry> getAll() {
        ArrayList<DatabaseEntry> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from progress", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            DatabaseEntry post = cursorToData(res);
            array_list.add(post);
            res.moveToNext();
        }
        return array_list;
    }

    public boolean insertProgress (String id, String day, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CHALLENGE,id);
        contentValues.put(COLUMN_DAY,day);
        contentValues.put(COLUMN_STATE,state);

        db.insert(TABLE_PROGRESS, null, contentValues);
        return true;
    }

    private DatabaseEntry cursorToData(Cursor cursor) {
        DatabaseEntry post = new DatabaseEntry();

        post.challenge = cursor.getString(0);
        post.day = cursor.getString(1);
        post.state = cursor.getString(2);

        return post;
    }


    public boolean updateProgress (String id, String day, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CHALLENGE, id);
        contentValues.put(COLUMN_DAY, day);
        contentValues.put(COLUMN_STATE, state);
        db.update(TABLE_PROGRESS, contentValues, "challenge = ? ", new String[] { id } );
        return true;
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from progress where challenge = '" + id + "';", null );
        return res;
    }

}
