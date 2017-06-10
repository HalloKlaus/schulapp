package sebastian.design.code.schulapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements  FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener, ThirdFragment.OnFragmentInteractionListener, FourthFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";
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

        changeFragment(firstFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.v(TAG, "onFragmentInteraction()");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
