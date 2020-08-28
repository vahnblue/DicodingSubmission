package rimelm.com.dicodingsubmission.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rimelm.com.dicodingsubmission.Model.Model;
import rimelm.com.dicodingsubmission.R;

import static rimelm.com.dicodingsubmission.Database.DatabaseContract.CONTENT_URI;


public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private Cursor cursor;
    private int AppWidgetId;


    StackRemoteViewFactory(Context context,Intent intent) {
        mContext = context;
        AppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

        if (cursor != null){
            cursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();

        // querying ke database
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(cursor == null) return 0;
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Model model = getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .load("https://image.tmdb.org/t/p/w300/"+model.getPhoto())
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error","error");
        }

        rv.setImageViewBitmap(R.id.imageView,bmp);
        rv.setTextViewText(R.id.tvTitle, model.getTitle());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    public Model getItem(int position){
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Model(cursor);
    }

}
