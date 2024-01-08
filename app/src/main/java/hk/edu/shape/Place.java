package hk.edu.shape;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {
    private int id;
    private String name;
    private String description;

    public Place() {
    }

    public Place(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Place(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    // Getters and setters

    // Parcelable methods

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}