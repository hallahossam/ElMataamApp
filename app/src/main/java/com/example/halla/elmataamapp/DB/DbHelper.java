package com.example.halla.elmataamapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 12/5/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String  databaseName = "Elmat3am";
    DbSchema mDbSchema = new DbSchema();
    SQLiteDatabase mSQLiteDatabase;

    public DbHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(mDbSchema.createTableUser);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+mDbSchema.tableUser);
        onCreate(sqLiteDatabase);
    }
    public boolean register(String email, String pass){
        boolean Yes;
        boolean check = checked(email);
        if(check == true){
            Yes = false;
        }
        else{
            Yes = true;
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDbSchema.email,email);
        contentValues.put(mDbSchema.password,pass);

        mSQLiteDatabase = getWritableDatabase();
        mSQLiteDatabase.insert(mDbSchema.tableUser, null, contentValues);
        mSQLiteDatabase.close();
        }
        return Yes;
    }
    public String login(String email, String pass){
        mSQLiteDatabase = getReadableDatabase();
        String result = "";
        String [] arg = {email, pass};
        Cursor c = mSQLiteDatabase.rawQuery("select id from Users where email like ? and password like ?",arg);
        if(c.getCount()!= 0){
            c.moveToFirst();
            result = c.getString(0);
        }
        return  result;
    }
    public boolean checked(String email){
        mSQLiteDatabase = getReadableDatabase();
        boolean result = false;
        String [] arg = {email};
        Cursor c = mSQLiteDatabase.rawQuery("select id from Users where email like ?",arg);
        if(c.getCount()!= 0){
            result = true;
        }
        return result;
    }
}
