package rimelm.com.dicodingsubmission.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import rimelm.com.dicodingsubmission.Model.Model;

public class FavoriteProvider extends ContentProvider {

    private static final int FAVORITE = 100;
    private static final int FAVORITE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        // content://com.example.rgher.realmtodo/tasks
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_FAVORITE,
                FAVORITE);

        // content://com.example.rgher.realmtodo/tasks/id
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_FAVORITE + "/#",
                FAVORITE_WITH_ID);
    }

    @Override
    public boolean onCreate() {
        //Innitializing RealmDB
        Realm.init(getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new MyRealmMigration())
                .build();
        Realm.setDefaultConfiguration(config);

        //manageCleanupJob();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        int match = sUriMatcher.match(uri);

        //Get Realm Instance
        Realm realm = Realm.getDefaultInstance();
        MatrixCursor myCursor = new MatrixCursor( new String[]{
                DatabaseContract.TaskColumns._ID, DatabaseContract.TaskColumns.TITLE
                , DatabaseContract.TaskColumns.DESCRIPTION, DatabaseContract.TaskColumns.PHOTO
                , DatabaseContract.TaskColumns.DATE_RELEASE
        });

        try {
            switch (match) {
                //Expected "query all" Uri: content://com.example.rgher.realmtodo/tasks

                case FAVORITE:
                    RealmResults<Model> tasksRealmResults = realm.where(Model.class).findAll();
                    for (Model model : tasksRealmResults) {
                        Object[] rowData = new Object[]{
                                model.getId(), model.getTitle(), model.getDescription()
                                , model.getPhoto(), model.getDateRelease()};
                        myCursor.addRow(rowData);
                        Log.v("RealmDB", model.toString());
                    }
                    break;

                //Expected "query one" Uri: content://com.example.rgher.realmtodo/tasks/{id}
                case FAVORITE_WITH_ID:
                    Integer id = Integer.parseInt(uri.getPathSegments().get(1));
                    Model model = realm.where(Model.class).equalTo("id", id).findFirst();
                    myCursor.addRow(new Object[]{
                            model.getId(),
                            model.getTitle(),
                            model.getDescription(),
                            model.getPhoto(),
                            model.getDateRelease()
                    });
                    Log.v("RealmDB", model.toString());
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }


            myCursor.setNotificationUri(getContext().getContentResolver(), uri);
        } finally {
            realm.close();
        }
        return myCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable final ContentValues contentValues) {
        //COMPLETE: Expected Uri: content://com.example.rgher.realmtodo/tasks

        //final SQLiteDatabase taskDb = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        //Get Realm Instance
        Realm realm = Realm.getDefaultInstance();
        try {
            switch (match) {
                case FAVORITE:
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            Number currId = realm.where(Model.class).max(DatabaseContract.TaskColumns._ID);
                            Integer nextId = (currId == null) ? 1 : currId.intValue() + 1;

                            Model model = realm.createObject(Model.class, nextId);
                            model.setTitle(contentValues.get(DatabaseContract.TaskColumns.TITLE).toString());
                            model.setDescription(contentValues.get(DatabaseContract.TaskColumns.DESCRIPTION).toString());
                            model.setPhoto(contentValues.get(DatabaseContract.TaskColumns.PHOTO).toString());
                            model.setPhoto(contentValues.get(DatabaseContract.TaskColumns.DATE_RELEASE).toString());

                        }
                    });
                    returnUri = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI, '1');
                    break;

                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            getContext().getContentResolver().notifyChange(uri, null);
        }finally {
            realm.close();
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        Realm realm = Realm.getDefaultInstance();
        try {
            switch (sUriMatcher.match(uri)) {
                case FAVORITE:
                    selection = (selection == null) ? "1" : selection;
                    RealmResults<Model> tasksRealmResults = realm.where(Model.class).equalTo(selection, Integer.parseInt(selectionArgs[0])).findAll();
                    realm.beginTransaction();
                    tasksRealmResults.deleteAllFromRealm();
                    count++;
                    realm.commitTransaction();
                    break;
                case FAVORITE_WITH_ID:
                    Integer id = Integer.parseInt(String.valueOf(ContentUris.parseId(uri)));
                    Model myTask = realm.where(Model.class).equalTo("id", id).findFirst();
                    realm.beginTransaction();
                    myTask.deleteFromRealm();
                    count++;
                    realm.commitTransaction();
                    break;
                default:
                    throw new IllegalArgumentException("Illegal delete URI");
            }
        } finally {
            realm.close();
        }
        if (count > 0) {
            //Notify observers of the change
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

// Example of REALM migration
class MyRealmMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if (oldVersion != 0) {
            schema.create(DatabaseContract.TABLE_FAVORITE)
                    .addField(DatabaseContract.TaskColumns._ID, Integer.class)
                    .addField(DatabaseContract.TaskColumns.TITLE, String.class)
                    .addField(DatabaseContract.TaskColumns.DESCRIPTION, String.class)
                    .addField(DatabaseContract.TaskColumns.PHOTO, String.class)
                    .addField(DatabaseContract.TaskColumns.DATE_RELEASE, String.class);
            oldVersion++;
        }

    }
}
