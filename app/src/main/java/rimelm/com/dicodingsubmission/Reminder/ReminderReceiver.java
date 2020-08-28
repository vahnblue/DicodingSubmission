package rimelm.com.dicodingsubmission.Reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;


import androidx.core.app.NotificationCompat;
import rimelm.com.dicodingsubmission.R;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String MESSAGE = "message";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(MESSAGE);
        String title =  "My Dicoding Submision";

        sendNotification(context, title, message);
    }

    public static void sendNotification(Context context,String title,String text){
        String CHANNEL_ID = "channel_01";
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
            mNotificationManager.notify(100, mBuilder.build());
        }
    }


}
