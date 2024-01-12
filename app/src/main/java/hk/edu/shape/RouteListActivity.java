package hk.edu.shape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RouteListActivity extends AppCompatActivity {

    private ListView listView;
    private RouteListAdapter adapter;
    private List<Route> allRoutes;
    private EditText editTextSearch;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_route);

        listView = findViewById(R.id.listView);
        adapter = new RouteListAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editTextSearch.getText().toString();
                filterRoutes(query);
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                filterRoutes(query);
            }
        });

        fetchBusRoutes();
    }

    private void fetchBusRoutes() {
        String url = "\n" +
                "https://data.etabus.gov.hk/v1/transport/kmb/route/";

        new FetchBusRoutesTask().execute(url);
    }

    private void displayBusRoutes(List<Route> routes) {
        adapter.clear();
        adapter.addAll(routes);
        adapter.notifyDataSetChanged();
    }

    private void filterRoutes(String query) {
        List<Route> filteredRoutes = new ArrayList<>();

        for (Route route : allRoutes) {
            if (route.getRouteNumber().contains(query) ||
                    route.getOrigin().contains(query) ||
                    route.getDestination().contains(query)) {
                filteredRoutes.add(route);
            }
        }

        displayBusRoutes(filteredRoutes);
    }

    private class FetchBusRoutesTask extends AsyncTask<String, Void, List<Route>> {

        @Override
        protected List<Route> doInBackground(String... urls) {
            String urlString = urls[0];
            List<Route> routes = new ArrayList<>();

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonResponse.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String routeNumber = jsonObject.getString("route");
                        String origin = jsonObject.getString("orig_en");
                        String destination = jsonObject.getString("dest_en");

                        Route route = new Route(routeNumber, origin, destination);
                        routes.add(route);
                    }

                    reader.close();
                } else {
                    Log.e("FetchBusRoutesTask", "HTTP response code: " + responseCode);
                }

                connection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return routes;
        }

        @Override
        protected void onPostExecute(List<Route> routes) {
                    allRoutes = routes;
            displayBusRoutes(routes);
        }
    }
}