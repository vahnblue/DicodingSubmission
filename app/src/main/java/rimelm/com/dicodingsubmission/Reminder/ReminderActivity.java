package rimelm.com.dicodingsubmission.Reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import rimelm.com.dicodingsubmission.R;

import static rimelm.com.dicodingsubmission.Reminder.ReminderReceiver.MESSAGE;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment(getApplicationContext()))
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



    public static class SettingsFragment extends PreferenceFragmentCompat  {
        SwitchPreferenceCompat dailyReminder;
        SwitchPreferenceCompat releaseTodayReminder;


        Context context;


        SettingsFragment(Context context){
            this.context = context;
        }


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

            addPreferencesFromResource(R.xml.root_preferences);

            dailyReminder = findPreference("dailyReminder");
            releaseTodayReminder = findPreference("releaseTodayReminder");




            dailyReminder.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    if(dailyReminder.isChecked()){
                        Toast.makeText(getContext(), "Daily Reminder Off", Toast.LENGTH_SHORT).show();
                        dailyReminder.setChecked(false);
                        cancelDailyReminder(context);
                    }
                    else {
                        Toast.makeText(getContext(), "Daily Reminder On", Toast.LENGTH_SHORT).show();
                        dailyReminder.setChecked(true);
                        setDailyReminder(context,"KITA RINDU ANDA !!!");
                    }
                    return false;
                }
            });

            releaseTodayReminder.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(releaseTodayReminder.isChecked()){
                        Toast.makeText(getContext(), "Release Today Off", Toast.LENGTH_SHORT).show();
                        releaseTodayReminder.setChecked(false);
                        cancelReleaseToday(context);
                    }
                    else {
                        Toast.makeText(getContext(), "Release Today On", Toast.LENGTH_SHORT).show();
                        releaseTodayReminder.setChecked(true);
                        setReleaseTodayReminder(context);
                    }
                    return false;
                }
            });
        }

        public void setDailyReminder(Context context, String message){
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReminderReceiver.class);
            intent.putExtra(MESSAGE, message);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,14);
            calendar.set(Calendar.MINUTE, 3);
            calendar.set(Calendar.SECOND, 0);
            PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, 100, intent, 0);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        public void setReleaseTodayReminder(Context context){
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReminderTodayReleaseReceiver.class);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, 101, intent, 0);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        public void cancelDailyReminder(Context context){
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReminderReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, 0);
            alarmManager.cancel(pendingIntent);
        }

        public void cancelReleaseToday(Context context){
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReminderTodayReleaseReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent, 0);
            alarmManager.cancel(pendingIntent);
        }

    }

}