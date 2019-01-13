package kasibhatla.dev.easynav;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.common.util.LogUtils;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.model.MapTilesType;

import java.io.File;

public class MainActivity extends AppCompatActivity
                implements HomeFragment.OnFragmentInteractionListener, BlankMapFragment.OnFragmentInteractionListener{
    private static final String TAG = "main-activity";

    private File dataFolder = new File (Environment.getExternalStorageDirectory(), "/Android/data/kasibhatla" +
            ".dev.easynav/");
    private File logFolder = new File(dataFolder, "Logs/");

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG, "This callback was used");
    }

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    startFragment(new HomeFragment());
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    startFragment(new BlankMapFragment());
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startLoggingEverything(true);
        setContentView(R.layout.activity_main);
        startFragment(new HomeFragment());
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

     protected boolean startFragment(Fragment fragment){
        if(fragment != null){
            Log.i(TAG, "Attempting to start the fragment");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //HomeFragment fragment1 = new HomeFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
            fragmentTransaction.commit();
            return true;
        }
        else{
            Log.i(TAG, "Blank fragment passed");
            return false;
        }
    }

    protected void startLoggingEverything(boolean youShouldLog){
        if(youShouldLog){
            LogUtils.enableLogs(Log.VERBOSE);
        }
        else{
            //critical logging always available
            LogUtils.enableLogs(Log.ERROR);
        }
        File logFile = new File(logFolder, "log.log");
        if(!dataFolder.exists()){
            dataFolder.mkdir();
            logFolder.mkdir();
        }
        try{
            LogUtils.LogFileCollector.collectLogsToFile(logFile + "");
            LogUtils.registerCrashObserver(getApplicationContext(), Uri.parse("file://" + logFolder + "1"));
        }catch(Exception e){
            e.printStackTrace();
            Log.i(TAG, "error generating tomtom log file");
        }
    }

}


