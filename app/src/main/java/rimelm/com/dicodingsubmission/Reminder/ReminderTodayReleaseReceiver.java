package rimelm.com.dicodingsubmission.Reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import rimelm.com.dicodingsubmission.Model.Model;
import rimelm.com.dicodingsubmission.R;

public class ReminderTodayReleaseReceiver extends BroadcastReceiver {

    private final static ArrayList<Model> modelsList = new ArrayList<>();
    private RequestQueue queue;
    Context context;
    int idReminder = 1;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    final Date date = new Date();
    final String releaseToday = dateFormat.format(date);


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        getDataNotif(releaseToday);
    }

    public void getDataNotif(final String releaseToday){
        JsonObjectRequest rec = new JsonObjectRequest(Request.Method.GET, "https://api.themoviedb.org/3/discover/movie?api_key=7c10b1851b48fcd85652c7f921820eee&primary_release_date.gte="+releaseToday+"&primary_release_date.lte="+releaseToday, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                modelsList.clear();
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Model model = new Model();
                        model.setId(obj.getString("id"));
                        model.setTitle(obj.getString("title"));
                        model.setPhoto(obj.getString("poster_path"));
                        model.setDescription(obj.getString("overview"));
                        model.setDateRelease(obj.getString("release_date"));

                        if (obj.getString("release_date").equals(releaseToday)) {
                            modelsList.add(model);
                        }
                    }

                    for (Model model : modelsList){
                        if(model.getDateRelease().equalsIgnoreCase(releaseToday)){
                            sendNotification(context,model.getTitle(),model.getDateRelease(),idReminder);
                            idReminder++;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        queue = Volley.newRequestQueue(context);
        queue.add(rec);
    }

    public static void sendNotification(Context context,String title,String text, int idReminder){
        String CHANNEL_ID = "channel_02";
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_notifications_white_24dp))
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Reminder", NotificationManager.IMPORTANCE_DEFAULT);

            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        if (mNotificationManager != null) {
            mNotificationManager.notify(idReminder, mBuilder.build());
        }
    }


}
