package sebastian.design.code.schulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView title = (TextView) findViewById(R.id.navigation_vertretungsplan);
        title.setText("this is the second activity for programmers sake");

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch (item.getItemId()) {
                    case R.id.navigation_Start:
                        Intent intent0 = new Intent(SecondActivity.this, MainActivity.class);
                        startActivity(intent0);

                        return true;
                    case R.id.navigation_vertretungsplan:
                        Intent intent1 = new Intent (SecondActivity.this, SecondActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_schulaufgabenplan:
                        Intent intent2 = new Intent(SecondActivity.this, ThirdActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.navigation_Einstellungen:
                        Intent intent3 = new Intent(SecondActivity.this, FourthActivity.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }

        };
    }
}

