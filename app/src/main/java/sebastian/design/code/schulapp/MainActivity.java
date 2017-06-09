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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener, ThirdFragment.OnFragmentInteractionListener, FourthFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    private DatabaseReference root;
    private LinkedList<Storage> newsMessageRoot = new LinkedList<>();
    private SecondFragment secondFragment = new SecondFragment();
    private ThirdFragment thirdFragment = new ThirdFragment();
    private FourthFragment fourthFragment = new FourthFragment();
    private FirstFragment firstFragment = new FirstFragment();

    FrameLayout frameLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_Start:
                    changeFragment(firstFragment);
                    return true;
                case R.id.navigation_vertretungsplan:
                    changeFragment (secondFragment);
                    return true;
                case R.id.navigation_schulaufgabenplan:
                    changeFragment(thirdFragment);
                    return true;
                case R.id.navigation_Einstellungen:
                    changeFragment(fourthFragment);
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

//        FirebaseMessaging.getInstance().subscribeToTopic("news");
//
//        dataSource = new SQLiteStorageDataSource(this, new SQLiteStorageDataSource.DataObserver() {
//            @Override
//            public void updated() {
//                dataSource.open();
//                showAllListEntries();
//                dataSource.close();
//            }
//        });

        root = FirebaseDatabase.getInstance().getReference();

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_news(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_news(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        changeFragment(firstFragment);

    }

    public HashMap<String, String> news;

    private void append_news(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        LinkedList<Storage> newsMessage = new LinkedList<>();

        while (i.hasNext()) {
            news = (HashMap<String, String>) ((DataSnapshot) i.next()).getValue();

            //Log.i("Test", news.get("message") + news.get("sender") + news.get("timestamp"));

            String newsMsg, newsSender, newsTimestamp;
            newsMsg = news.get("message");
            newsSender = news.get("sender");
            newsTimestamp = news.get("timestamp");

            Log.i("Test", newsMsg + newsSender + newsTimestamp);

            Storage storage = new Storage(newsMsg, newsTimestamp, newsSender);

            newsMessage.add(storage);
            Log.d("News", "append_news: appended 1 item");
        }


        this.newsMessageRoot = newsMessage;
        //showAllListEntries();
    }



    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.v(TAG, "onFragmentInteraction()");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void showAllListEntries () {
        LinkedList<Storage> storageList = this.newsMessageRoot;

        ArrayAdapter<Storage> storageArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_multiple_choice,
                storageList);
        Log.d(TAG, "showAllListEntries: In Storage -> " + storageList.size());

        ListView storageListView = (ListView) findViewById(R.id.listViewNews);
        storageListView.setAdapter(storageArrayAdapter);
    }
}
