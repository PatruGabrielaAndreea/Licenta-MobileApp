package com.example.licenta;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.onesignal.OneSignal;

import java.util.Calendar;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        //Set the notification time
        //Get reference to ListPreference
        ListPreference listPreference = findPreference("notification_time");
        if (listPreference != null) {
            //Setting the listener for changes in ListPreference
            listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                    savePreferredNotificationTime((String) newValue);

                    return true; //Return true to enable saving
                }
            });
        }

        //Set the Theme
        /*ListPreference themePreference = findPreference("theme");
        themePreference.setOnPreferenceChangeListener((preference, newValue) -> {
            String selectedTheme = (String) newValue;
            getActivity().setTheme(getThemeId(selectedTheme));
            getActivity().recreate();

            return true;
        });*/

        //Get the SwitchPreferenceCompat
        SwitchPreferenceCompat allowVibrationPreference = findPreference("allow_vibration");

        //Set up a listener to detect preference changes
        allowVibrationPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean allowVibration = (Boolean) newValue;

            if (allowVibration) {
                //Enable vibaration
                enableVibration();
            } else {
                //Disable Vibartion
                disableVibration();
            }

            return true;
        });

        /* //Get the SwitchPreferenceCompat
        SwitchPreferenceCompat enableNotificationsPreference = findPreference("notifications");

        //Set up a listener to detect preference changes
        enableNotificationsPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean enableNotifications = (Boolean) newValue;

            if (enableNotifications) {
                //Enable notifications
                enableNotifications();
            } else {
                //Disable notifications
                disableNotifications();
            }

            return true;
        }); */
    }

    private void savePreferredNotificationTime(String time) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("preferred_notification_time", time);
        editor.apply();
    }

    /*private int getThemeId(String themeName) {
        switch (themeName) {
            case "AppTheme.Light":
                return R.style.AppTheme_Light;
            case "AppTheme.Dark":
                return R.style.AppTheme_Dark;
            default:
                return R.style.AppTheme_Light;
        }
    }*/

    private void enableVibration() {
        Vibrator vibrator = (Vibrator) requireActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }
    }

    private void disableVibration() {
        Vibrator vibrator = (Vibrator) requireActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            // Cancel any ongoing vibration
            vibrator.cancel();
        }
    }

}