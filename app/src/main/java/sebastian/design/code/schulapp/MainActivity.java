package sebastian.design.code.schulapp;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SecondFragment.OnFragmentInteractionListener {

    private static final String db_log_tag = "DB_Log";
    private static final String TAG = "MainActivity";

    private SQLiteStorageDataSource dataSource;

    FrameLayout frameLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_Start:
                    final SecondFragment secondFragment = new SecondFragment();
                    changeFragment(secondFragment);
                    return true;
                case R.id.navigation_dashboard:
                    final SecondFragment dashboardFragment = new SecondFragment();
                    changeFragment(dashboardFragment);
                    return true;
                case R.id.navigation_notifications:
                    final SecondFragment notificationFragment = new SecondFragment();
                    changeFragment(notificationFragment);
                    return true;
                case R.id.navigation_Einstellungen:
                    final SecondFragment settingsFragment = new SecondFragment();
                    changeFragment(settingsFragment);
                    return true;
            }
            return false;
        }

    };

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FBID","Refreshed Token:" + refreshedToken);


        FirebaseMessaging.getInstance().subscribeToTopic("news");

        dataSource = new SQLiteStorageDataSource(this);

        Log.d(db_log_tag, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        Storage shoppingMemo = dataSource.createNewNews("Tstfabirkat", "12:30", "Sender");
        Log.d(db_log_tag, "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        Log.d(db_log_tag, "ID: " + shoppingMemo.getId() + ", Inhalt: " + shoppingMemo.toString());

        Log.d(db_log_tag, "Folgende Einträge sind in der Datenbank vorhanden:");
        showAllListEntries();


        Log.d(db_log_tag, "Die Datenquelle wird geschlossen.");
        dataSource.close();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.v(TAG, "onFragmentInteraction()");
    }

    private void showAllListEntries () {
        List<Storage> shoppingMemoList = dataSource.getAllStorages();

        ArrayAdapter<Storage> shoppingMemoArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_multiple_choice,
                shoppingMemoList);

        ListView shoppingMemosListView = (ListView) findViewById(R.id.list_view);
        shoppingMemosListView.setAdapter(shoppingMemoArrayAdapter);
    }
}
