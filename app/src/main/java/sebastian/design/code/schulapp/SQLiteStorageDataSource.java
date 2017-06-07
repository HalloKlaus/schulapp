package sebastian.design.code.schulapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Sebastian on 07.06.2017.
 */

public class SQLiteStorageDataSource {

    private static final String LOG_TAG = "StorageDataSource";

    private SQLiteDatabase database;
    private SQliteStorageHelper storageHelper;

    public SQLiteStorageDataSource(Context context){
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den DBHelper");
        storageHelper = new SQliteStorageHelper(context);

    }

}
