package hk.edu.shape;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MallDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "malls.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MALLS = "malls";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";

    private static final String TABLE_FAVORITES = "favorites";

    public MallDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_MALLS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT" +
                ")";
        db.execSQL(createTableQuery);

        String createFavoritesTableQuery = "CREATE TABLE " + TABLE_FAVORITES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT" +
                ")";
        db.execSQL(createFavoritesTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MALLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);

        // Recreate the tables
        onCreate(db);
        // Handle database version upgrades if needed
    }

    public void insertMall(Place mall) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, mall.getName());
        values.put(COLUMN_DESCRIPTION, mall.getDescription());
        db.insert(TABLE_MALLS, null, values);
        db.close();
    }

    public List<Place> getAllMalls() {
        List<Place> malls = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MALLS,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            do {
                Place mall = new Place();
                if (idIndex >= 0) {
                    mall.setId(cursor.getInt(idIndex));
                }
                if (nameIndex >= 0) {
                    mall.setName(cursor.getString(nameIndex));
                }
                if (descriptionIndex >= 0) {
                    mall.setDescription(cursor.getString(descriptionIndex));
                }
                malls.add(mall);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return malls;
    }

    public boolean isFavorite(Place mall) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_FAVORITES + " WHERE " + COLUMN_ID + " = " + mall.getId();
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isFavorite;
    }

    public void addToFavorites(Place mall) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, mall.getId());
        values.put(COLUMN_NAME, mall.getName());
        values.put(COLUMN_DESCRIPTION, mall.getDescription());
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    public void removeFromFavorites(Place mall) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_ID + " = " + mall.getId(), null);
        db.close();
    }

    public List<Place> getFavoritePlaces() {
        List<Place> favoritePlaces = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);

            do {
                Place place = new Place();
                if (idIndex >= 0) {
                    place.setId(cursor.getInt(idIndex));
                }
                if (nameIndex >= 0) {
                    place.setName(cursor.getString(nameIndex));
                }
                if (descriptionIndex >= 0) {
                    place.setDescription(cursor.getString(descriptionIndex));
                }
                favoritePlaces.add(place);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return favoritePlaces;
    }
}