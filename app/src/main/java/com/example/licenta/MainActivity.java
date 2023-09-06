package com.example.licenta;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ONESIGNAL_APP_ID = "a836eb27-1c34-4a97-b8c5-284a4becb06e";


    private BottomNavigationView bottomNavigation;

    private HomeFragment homeFragment;
    private SettingsFragment settingsFragment;
    private SearchFragment searchFragment;
    private NotificationAdapter notificationAdapter;
    private List<AppNotification> notificationsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        // optIn will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        //OneSignal.getUser().getPushSubscription().optIn();

        //Set handler for opening notifications
        /*OneSignal.setNotificationOpenedHandler(result -> {
            OSNotificationOpenedResult openedResult = (OSNotificationOpenedResult) result;
            JSONObject data = openedResult.getNotification().getAdditionalData();
            if (data != null) {
                String title = data.optString("title", null);
                String description = data.optString("description", null);
                String category = data.optString("category", null);
                if (title != null && description != null && category != null) {
                    //Add the notification to the notification list
                    AppNotification appNotification = new AppNotification(title, description, category);
                    notificationsList.add(appNotification);
                    //Update the adapter to reflect the changes
                    notificationAdapter.notifyDataSetChanged();
                }
            }
        });*/

        //Initialize fragments
        homeFragment = new HomeFragment();
        settingsFragment = new SettingsFragment();
        searchFragment = new SearchFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contentFrame, homeFragment);
        fragmentTransaction.commit();

        bottomNavigation = findViewById(R.id.bottomNavigation);
        //Set the first item (Home) as selected when the app starts
        bottomNavigation.setSelectedItemId(R.id.action_home);

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.action_home) {
                selectedFragment = homeFragment;
            } else if (item.getItemId() == R.id.action_settings) {
                selectedFragment = settingsFragment;
            } else if (item.getItemId() == R.id.action_search) {
                selectedFragment = searchFragment;
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.contentFrame, selectedFragment);
            transaction.commit();

            return true;
        });
    }

    public interface NotificationHandler {
        void onNotificationReceived(AppNotification notification);
    }
}