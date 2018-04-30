package com.example.ninja.drugstime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ninja on 25/04/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "drugdb";
    public static final String TABLENAME = "drugtb";
    public static final int DBVERSION = 1;
    public static final String DRUGID = "_ID";
    public static final String DRUGNAME = "name";
    public static final String DRUGIMAGE = "image";
    public static final String DRUGCOUNT = "count_number";
    public static final String DRUGPERIOD = "drugdb";
    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABLENAME+" ( "+DRUGID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+DRUGNAME+" TEXT , "+DRUGIMAGE+" BLOB , "+DRUGCOUNT+" INTEGER , "+DRUGPERIOD+" INTEGER );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE "+TABLENAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
