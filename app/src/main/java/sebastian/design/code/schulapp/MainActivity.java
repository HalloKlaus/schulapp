package sebastian.design.code.schulapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_Start:
                    mTextMessage.setText(R.string.title_Startseite);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_Vertretungsplan);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_Schulaufgabenplan);
                    return true;
                case R.id.navigation_Einstellungen:
                    mTextMessage.setText(R.string.title_Einstellungen);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}

