package hk.edu.shape;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class FavoritePlacesActivity extends AppCompatActivity {
    private ListView listView;
    private FavoritePlacesAdapter adapter;
    private MallDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_places);

        listView = findViewById(R.id.listview);
        databaseHelper = new MallDatabaseHelper(this);

        List<Place> favoritePlaces = databaseHelper.getFavoritePlaces();
        adapter = new FavoritePlacesAdapter(this, favoritePlaces);
        listView.setAdapter(adapter);
    }
}