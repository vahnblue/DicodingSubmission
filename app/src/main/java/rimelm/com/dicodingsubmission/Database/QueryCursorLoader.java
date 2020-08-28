package rimelm.com.dicodingsubmission.Database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class QueryCursorLoader extends AsyncTaskLoader<Cursor> {

    Cursor mCursor = null;
    Context mContext;
    Uri mUri;

    public QueryCursorLoader(@NonNull Context context,Uri uri) {
        super(context);
        mContext = context;
        mUri = uri;
    }

    @Nullable
    @Override
    public Cursor loadInBackground() {
        return mContext.getContentResolver().query(mUri, null, null, null);
    }

    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(@Nullable Cursor data) {
        mCursor = data;
        super.deliverResult(data);
    }
}
