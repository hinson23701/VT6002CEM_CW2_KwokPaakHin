package hk.edu.shape;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
        }

        List<Place> malls = databaseHelper.getAllMalls();
        adapter = new MallListAdapter(this, malls);
        listView.setAdapter(adapter);
    }
}
