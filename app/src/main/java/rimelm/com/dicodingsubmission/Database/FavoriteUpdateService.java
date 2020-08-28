package rimelm.com.dicodingsubmission.Database;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.Nullable;
import android.util.Log;

public class FavoriteUpdateService extends IntentService {

    private static final String TAG = FavoriteUpdateService.class.getSimpleName();

    //Intent actions
    public static final String ACTION_INSERT = TAG + ".INSERT";
    public static final String ACTION_DELETE = TAG + ".DELETE";

    public static final String EXTRA_VALUES = TAG + ".ContentValues";

    public static void insertNewFavorite(Context context, ContentValues values) {
        Intent intent = new Intent(context, FavoriteProvider.class);
        intent.setAction(ACTION_INSERT);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }

    public static void deleteFavorite(Context context, Uri uri) {
        Intent intent = new Intent(context, FavoriteProvider.class);
        intent.setAction(ACTION_DELETE);
        intent.setData(uri);
        context.startService(intent);
    }


    public FavoriteUpdateService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (ACTION_INSERT.equals(intent.getAction())) {
            ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
            performInsert(values);
        } else if (ACTION_DELETE.equals(intent.getAction())) {
            performDelete(intent.getData());
        }
    }

    private void performInsert(ContentValues values) {
        if (getContentResolver().insert(DatabaseContract.CONTENT_URI, values) != null) {
            Log.d(TAG, "Inserted new task");
        } else {
            Log.w(TAG, "Error inserting new task");
        }
    }

    private void performDelete(Uri uri) {
        int count = getContentResolver().delete(uri, null, null);

//        PendingIntent operation =
//                ReminderAlarmService.getReminderPendingIntent(this, uri);
//        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        manager.cancel(operation);

        Log.d(TAG, "Deleted "+count+" tasks");
    }

}
