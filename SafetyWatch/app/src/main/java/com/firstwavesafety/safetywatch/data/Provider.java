package com.firstwavesafety.safetywatch.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.firstwavesafety.safetywatch.data.Contract.JobEntry;

public class Provider extends ContentProvider{
    private static final int JOBS = 100;
        private static final int JOBS_ID = 101;

        private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_JOBS, JOBS);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_JOBS + "/#", JOBS_ID);
        }
    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper((getContext()));
        return true;
        }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                    String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case JOBS:
                cursor = database.query(JobEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                 break;
             case JOBS_ID:
                selection = JobEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(JobEntry.TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);
                break;
            default:
        throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case JOBS:
                return JobEntry.CONTENT_LIST_TYPE;
            case JOBS_ID:
                return JobEntry.CONTENT_ITEM_TYPE;
            default:
            throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case JOBS:
                return insertExercise(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case JOBS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(JobEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case JOBS_ID:
                // Delete a single row given by the ID in the URI
                selection = JobEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(JobEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if(rowsDeleted != 0){
        getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case JOBS:
                return updateExercise(uri, values, selection, selectionArgs);
            case JOBS_ID:
                selection = JobEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateExercise(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private Uri insertExercise(Uri uri, ContentValues values){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(JobEntry.TABLE_NAME, null, values);

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    private int updateExercise(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        if (values.containsKey(JobEntry.COLUMN_JOB_NAME)){
            String name = values.getAsString(JobEntry.COLUMN_JOB_NAME);
            if (name == null){
                throw new IllegalArgumentException("Job requires name");
            }
        }
        if (values.containsKey(JobEntry.COLUMN_JOB_LOCATION)){
            String name = values.getAsString(JobEntry.COLUMN_JOB_LOCATION);
            if (name == null){
                throw new IllegalArgumentException("Job requires location");
            }
        }
        if (values.containsKey(JobEntry.COLUMN_WORK_TYPE)){
            String name = values.getAsString(JobEntry.COLUMN_WORK_TYPE);
            if (name == null){
                throw new IllegalArgumentException("Job requires a type");
            }
        }
        if (values.containsKey(JobEntry.COLUMN_PREMIT_NUMBER)){
            String name = values.getAsString(JobEntry.COLUMN_PREMIT_NUMBER);
            if (name == null){
                throw new IllegalArgumentException("Job requires a permit numbewr");
            }
        }
        if (values.size() == 0){
            return 0;
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowsUpdated = db.update(JobEntry.TABLE_NAME, values, selection, selectionArgs);
        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}

