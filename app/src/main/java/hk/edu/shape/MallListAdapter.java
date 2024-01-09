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

public class MallListAdapter extends ArrayAdapter<Place> {
    private Context mContext;
    private List<Place> malls;

    public MallListAdapter(Context context, List<Place> malls) {
        super(context, 0, malls);
        mContext = context;
        this.malls = malls;
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Place place = malls.get(position);
        if (place != null) {
            holder.nameTextView.setText(place.getName());
            holder.descriptionTextView.setText(place.getDescription());
        }

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
        TextView nameTextView;
        TextView descriptionTextView;
    }
}