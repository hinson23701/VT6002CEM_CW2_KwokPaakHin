package hk.edu.shape;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class PlaceActivity extends Activity {

    private ListView listView;
    private MallListAdapter adapter;
    private MallDatabaseHelper databaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_place);
        listView = findViewById(R.id.listview);
        databaseHelper = new MallDatabaseHelper(this);

        // Check if the data already exists in the database
        if (databaseHelper.getAllMalls().isEmpty()) {
            // Add the data to the database if it does not exist
            databaseHelper.insertMall(new Place("V city", "V city hosts 130 brands and restaurants in a convenient location adjacent to MTR Tuen Mun Station and Tuen Mun Light Rail Station. The mall’s Bella Beauty Zone features over 20 shops, and foodies will enjoy the first floor Food Spot, where cuisines include Chinese, Japanese, Western and more"));
            databaseHelper.insertMall(new Place("TM Plaza", "One of the largest shopping malls in the Northwest New Territories with 1000000 square feet of retail space and 400 shops of all varieties."));
            databaseHelper.insertMall(new Place("Go Go Mall", " The Go Go Mall is the 1/F commercial space above the Eldo Court Shopping Centre along Tuen Mun Heung Sze Wui Road"));
            databaseHelper.insertMall(new Place("H.A.N.D.S", " Address\n" +
                    "\n" +
                    ": 2A Tuen Mun Heung Sze Wui Road, Tuen Mun\n" +
                    "\n" +
                    "Opening Hour\n" +
                    "\n" +
                    ": 6am-12am"));
            databaseHelper.insertMall(new Place("Citywalk", " Modern retail complex with 200 stores & restaurants, plus a movie theatre & a park-like courtyard."
                    ));
            databaseHelper.insertMall(new Place("D Park", "Sprawling, family-friendly shopping center with 140 stores, eateries & entertainment facilities."));
        }

        List<Place> malls = databaseHelper.getAllMalls();
        adapter = new MallListAdapter(this, malls);
        listView.setAdapter(adapter);
    }

    public void OnClick(View view) {
        Intent intent = new Intent(PlaceActivity.this, FavoritePlacesActivity.class);
        startActivity(intent);
    }
}
