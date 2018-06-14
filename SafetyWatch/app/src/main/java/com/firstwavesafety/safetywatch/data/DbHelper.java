package com.firstwavesafety.safetywatch.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.firstwavesafety.safetywatch.data.Contract.JobEntry;
import com.firstwavesafety.safetywatch.data.Contract.WorkerEntry;

public class DbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "safety_watch.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_JOB_TABLE = "CREATE TABLE " + JobEntry.TABLE_NAME + " ("
                + JobEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + JobEntry.COLUMN_JOB_NAME + " TEXT NOT NULL, "
                + JobEntry.COLUMN_JOB_LOCATION + " TEXT NOT NULL, "
                + JobEntry.COLUMN_WORK_TYPE + " INTEGER NOT NULL, "
                + JobEntry.COLUMN_PREMIT_NUMBER + " INTEGER );";
        db.execSQL(SQL_CREATE_JOB_TABLE);

        String SQL_CREATE_WORKERS_TABLE = "CREATE TABLE " + WorkerEntry.TABLE_NAME + " ("
                + WorkerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WorkerEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + WorkerEntry.COLUMN_QUALIFICATIONS + " TEXT, "
                + WorkerEntry.COLUMN_EXPERIENCE + " INTEGER DEFAULT 0);";
        db.execSQL(SQL_CREATE_WORKERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
