package sebastian.design.code.schulapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

/**
 * Created by Sebastian on 07.06.2017.
 */

public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FBMSG";
    private SQLiteStorageDataSource dataSource;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        Log.w(TAG, "onMessageReceived()");

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        displayNotification("Schulapp", remoteMessage.getNotification().getBody());

        dataSource = new SQLiteStorageDataSource(this);
        dataSource.open();

        Storage storage = dataSource.createNewNews(remoteMessage.getNotification().getBody(), Long.toString(remoteMessage.getSentTime()), "sender");

        Log.d(TAG, "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        Log.d(TAG, "ID: " + storage.getId() + ", Inhalt: " + storage.toString());

        Log.d(TAG, "Folgende Einträge sind in der Datenbank vorhanden:");


        Log.d(TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();

    }

    private void displayNotification(String title, String contentText){
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_notification)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(14, notification);
    }

}
