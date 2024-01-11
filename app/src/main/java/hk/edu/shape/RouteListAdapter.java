package hk.edu.shape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RouteListAdapter extends ArrayAdapter<Route> {

    private Context context;
    private List<Route> routes;

    public RouteListAdapter(Context context, List<Route> routes) {
        super(context, R.layout.list_item_route, routes);
        this.context = context;
        this.routes = routes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_route, null);
        }

        Route route = routes.get(position);

        if (route != null) {
            TextView textViewRouteNumber = view.findViewById(R.id.textViewRouteNumber);
            TextView textViewOrigin = view.findViewById(R.id.textViewOrigin);
            TextView textViewDestination = view.findViewById(R.id.textViewDestination);

            if (textViewRouteNumber != null) {
                textViewRouteNumber.setText(route.getRouteNumber());
            }

            if (textViewOrigin != null) {
                textViewOrigin.setText("Origin: " + route.getOrigin());
            }

            if (textViewDestination != null) {
                textViewDestination.setText("Destination: " + route.getDestination());
            }
        }

        return view;
    }
}
