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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
}