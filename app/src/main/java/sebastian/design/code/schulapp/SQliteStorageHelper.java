package sebastian.design.code.schulapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Sebastian on 07.06.2017.
 */

public class SQliteStorageHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "StorageDBHelper";
    public static final String DB_NAME = "Storage.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_STORAGE_LIST = "news";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_SENDER = "sender";

    public static final Uri CONTENT_URI = Uri.parse("schulapp://sebastian.design.code.schulapp/database-update");

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_STORAGE_LIST +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIME + " TEXT NOT NULL, " +
                COLUMN_MESSAGE + " TEXT NOT NULL, " +
                COLUMN_SENDER + " TEXT NOT NULL);";


    public SQliteStorageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}