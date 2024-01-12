package hk.edu.shape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FavoritePlacesAdapter extends ArrayAdapter<Place> {
    private Context mContext;
    private List<Place> favoritePlaces;
    private MallDatabaseHelper databaseHelper;
 //define the variable
    public FavoritePlacesAdapter(Context context, List<Place> favoritePlaces) {
        super(context, 0, favoritePlaces);
        mContext = context;
        this.favoritePlaces = favoritePlaces;
        databaseHelper = new MallDatabaseHelper(mContext);
    }
//get the view from database
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_favorite_place, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.textview_name);
            holder.descriptionTextView = convertView.findViewById(R.id.textview_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Place favoritePlace = favoritePlaces.get(position);
        if (favoritePlace != null) {
            holder.nameTextView.setText(favoritePlace.getName());
            holder.descriptionTextView.setText(favoritePlace.getDescription());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
    }
}