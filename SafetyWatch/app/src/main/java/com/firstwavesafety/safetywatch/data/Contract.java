package com.firstwavesafety.safetywatch.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class Contract {

    private Contract(){}

    public static final String CONTENT_AUTHORITY = "com.firstwavesafety.safetywatch";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_JOBS = "jobs";

    public static final class JobEntry implements BaseColumns{

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_JOBS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_JOBS;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_JOBS);
        public final static String TABLE_NAME = "jobs";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_JOB_NAME = "name";
        public final static String COLUMN_JOB_LOCATION = "location";
        public final static String COLUMN_WORK_TYPE = "type";
        public final static String COLUMN_PREMIT_NUMBER = "permit_number";

        public static final int WORK_TYPE_UNKNOWN = 0;
        public static final int WORK_TYPE_SPARK_WATCH = 1;
        public static final int WORK_TYPE_CSE_WATCH = 2;
        public static final int WORK_TYPE_AREA_WATCH = 3;
        public static final int WORK_TYPE_BOTTLE_WATCH = 4;
    }
}
