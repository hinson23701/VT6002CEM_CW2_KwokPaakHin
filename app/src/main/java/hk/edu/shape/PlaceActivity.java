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

        databaseHelper.insertMall(new Place("V city", "V city hosts 130 brands and restaurants in a convenient location adjacent to MTR Tuen Mun Station and Tuen Mun Light Rail Station. The mall’s Bella Beauty Zone features over 20 shops, and foodies will enjoy the first floor Food Spot, where cuisines include Chinese, Japanese, Western and more"));

        List<Place> malls = databaseHelper.getAllMalls();
        adapter = new MallListAdapter(this, malls);
        listView.setAdapter(adapter);



    }
}
