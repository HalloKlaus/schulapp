package sebastian.design.code.schulapp;

import android.content.Intent;
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
                case R.id.navigation_vertretungsplan:
                    final SecondFragment dashboardFragment = new SecondFragment();
                    changeFragment(dashboardFragment);
                    Intent intent0 = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent0);
                    return true;
                case R.id.navigation_schulaufgabenplan:
                    final SecondFragment notificationFragment = new SecondFragment();
                    changeFragment(notificationFragment);
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
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

        dataSource = new SQLiteStorageDataSource(this, new SQLiteStorageDataSource.DataObserver() {
            @Override
            public void updated() {
                dataSource.open();
                showAllListEntries();
                dataSource.close();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
        showAllListEntries();
        dataSource.close();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.v(TAG, "onFragmentInteraction()");
    }



    public void showAllListEntries () {
        List<Storage> storageList = dataSource.getAllStorages();

        ArrayAdapter<Storage> storageArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_multiple_choice,
                storageList);

        ListView storageListView = (ListView) findViewById(R.id.list_view);
        storageListView.setAdapter(storageArrayAdapter);
    }


}
