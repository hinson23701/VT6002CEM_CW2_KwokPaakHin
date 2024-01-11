package hk.edu.shape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_route);

        listView = findViewById(R.id.listView);
        adapter = new RouteListAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

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

                        // Use the extracted values as needed
                        Log.d("Route", "Route Number: " + routeNumber);
                        Log.d("Route", "Origin: " + origin);
                        Log.d("Route", "Destination: " + destination);

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
            Log.d("FetchBusRoutesTask", "Retrieved " + routes.size() + " routes");
            displayBusRoutes(routes);
        }
    }
}