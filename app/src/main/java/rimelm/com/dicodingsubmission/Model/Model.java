package rimelm.com.dicodingsubmission.Model;

import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import rimelm.com.dicodingsubmission.Database.DatabaseContract;

import static rimelm.com.dicodingsubmission.Database.DatabaseContract.getColumnString;

public class Model extends RealmObject implements Parcelable {

    public static final String PROPERTY_TITLE = "title";


    @PrimaryKey
    private String id;
    private String title;
    private String photo;
    private String description;
    private String dateRelease;
    private String typeCatalog;


    public Model() {
    }

    public Model(String id, String title, String photo, String description, String dateRelease) {
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.description = description;
        this.dateRelease = dateRelease;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeCatalog() {
        return typeCatalog;
    }

    public void setTypeCatalog(String typeCatalog) {
        this.typeCatalog = typeCatalog;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }


    public Model(Cursor cursor){
        this.id = getColumnString(cursor, DatabaseContract.TaskColumns._ID);
        this.title = getColumnString(cursor, DatabaseContract.TaskColumns.TITLE);
        this.description = getColumnString(cursor,DatabaseContract.TaskColumns.DESCRIPTION);
        this.photo = getColumnString(cursor, DatabaseContract.TaskColumns.PHOTO);
        this.dateRelease = getColumnString(cursor,DatabaseContract.TaskColumns.DATE_RELEASE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.photo);
        dest.writeString(this.description);
        dest.writeString(this.dateRelease);
    }

    protected Model(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.photo = in.readString();
        this.description = in.readString();
        this.dateRelease = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel source) {
            return new Model(source);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}
