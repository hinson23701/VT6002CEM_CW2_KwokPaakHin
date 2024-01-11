package hk.edu.shape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MallListAdapter extends ArrayAdapter<Place> {
    private Context mContext;
    private List<Place> malls;
    private MallDatabaseHelper databaseHelper;

    public MallListAdapter(Context context, List<Place> malls) {
        super(context, 0, malls);
        mContext = context;
        this.malls = malls;
        databaseHelper = new MallDatabaseHelper(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.textViewName);
            holder.descriptionTextView = convertView.findViewById(R.id.textViewDescription);
            holder.favoriteButton = convertView.findViewById(R.id.button_favorite);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Place place = malls.get(position);
        if (place != null) {
            holder.nameTextView.setText(place.getName());
            holder.descriptionTextView.setText(place.getDescription());
        }  // Check if the place is a favorite
        if (databaseHelper.isFavorite(place)) {
            holder.favoriteButton.setText("Remove from Favorite");
        } else {
            holder.favoriteButton.setText("Add to Favorite");
        }

        // Set click listener for the favorite button
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the place is a favorite
                if (databaseHelper.isFavorite(place)) {
                    // Remove the place from favorites in the SQLite database
                    databaseHelper.removeFromFavorites(place);
                    Toast.makeText(mContext, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    // Add the place to favorites in the SQLite database
                    databaseHelper.addToFavorites(place);
                    Toast.makeText(mContext, "Added to Favorites", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged(); // Update the ListView
            }
        });


        return convertView;
    }

    @Override
    public int getCount() {
        return malls.size();
    }

    @Nullable
    @Override
    public Place getItem(int position) {
        return malls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private static class ViewHolder {
        Button favoriteButton;
        TextView nameTextView;
        TextView descriptionTextView;
    }
}