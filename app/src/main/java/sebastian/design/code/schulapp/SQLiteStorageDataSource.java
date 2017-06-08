package sebastian.design.code.schulapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static java.security.AccessController.getContext;

/**
 * Created by Sebastian on 07.06.2017.
 */

public class SQLiteStorageDataSource extends Observable {

    private static final String LOG_TAG = "StorageDataSource";
    private static final String INSERT_ID = "IID";
    private final List<DataObserver> observers = new ArrayList<>();

    private String[] columns = {
            SQliteStorageHelper.COLUMN_ID,
            SQliteStorageHelper.COLUMN_MESSAGE,
            SQliteStorageHelper.COLUMN_SENDER,
            SQliteStorageHelper.COLUMN_TIME
    };

    public interface DataObserver {
        void updated();
    }

    private SQLiteDatabase database;
    private SQliteStorageHelper storageHelper;

    public SQLiteStorageDataSource(Context context, DataObserver observer){
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den DBHelper");
        storageHelper = new SQliteStorageHelper(context);
        observers.add(observer);
    }

    public void open(){
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = storageHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close(){
        storageHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public Storage createNewNews(String msg, String time, String sender){

        ContentValues values = new ContentValues();
        values.put(SQliteStorageHelper.COLUMN_MESSAGE, msg);
        values.put(SQliteStorageHelper.COLUMN_TIME, time);
        values.put(SQliteStorageHelper.COLUMN_SENDER, sender);

        long insertId = database.insert(SQliteStorageHelper.TABLE_STORAGE_LIST, null, values);

        Cursor cursor = database.query(SQliteStorageHelper.TABLE_STORAGE_LIST,
                        columns, SQliteStorageHelper.COLUMN_ID + "=" + insertId,
                        null, null, null, null);

        cursor.moveToFirst();
        Storage storage = cursorToStorage(cursor);
        cursor.close();

        Log.i(INSERT_ID, "Die Insert_ID des Datensatzes ist: " + insertId);
        for (DataObserver observer : observers) {
            observer.updated();
        }
        return storage;
    }


    private Storage cursorToStorage(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(SQliteStorageHelper.COLUMN_ID);
        int idMessage = cursor.getColumnIndex(SQliteStorageHelper.COLUMN_MESSAGE);
        int idTime = cursor.getColumnIndex(SQliteStorageHelper.COLUMN_TIME);
        int idSender = cursor.getColumnIndex(SQliteStorageHelper.COLUMN_SENDER);

        String message = cursor.getString(idMessage);
        String time = cursor.getString(idTime);
        long id = cursor.getLong(idIndex);
        String sender = cursor.getString(idSender);

        Storage storage = new Storage(message, time,sender, id);

        return storage;
    }

    public List<Storage> getAllStorages(){
        List<Storage> storageList = new ArrayList<>();

        Cursor cursor = database.query(SQliteStorageHelper.TABLE_STORAGE_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        Storage storage;

        while (!cursor.isAfterLast()){
            storage = cursorToStorage(cursor);
            storageList.add(storage);
            Log.d(LOG_TAG, "ID: " + storage.getId() + ", Inhalt: " + storage.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return storageList;
    }


}
