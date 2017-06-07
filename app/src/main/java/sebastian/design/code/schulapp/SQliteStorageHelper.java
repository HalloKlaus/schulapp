package sebastian.design.code.schulapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sebastian on 07.06.2017.
 */

public class SQliteStorageHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "StorageDBHelper";

    public SQliteStorageHelper(Context context) {
        super(context, "Storage", null, 1);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}