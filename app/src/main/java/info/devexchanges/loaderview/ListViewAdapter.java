package info.devexchanges.loaderview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private boolean isLoadImage;
    private final static String IMAGE_URL = "http://i.imgur.com/cReBvDB.png";

    public ListViewAdapter(Activity context, int resource, ArrayList<String> objects, boolean isLoadImage) {
        super(context, resource, objects);
        this.activity = context;
        this.isLoadImage = isLoadImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        }  else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        if (!getItem(position).equals("")) {
            holder.countryName.setText(getItem(position));
        }
        if (isLoadImage) {
            Picasso.with(activity).load(IMAGE_URL).into(holder.imageView);
        }

        return convertView;
    }

    private class ViewHolder{

        private ImageView imageView;
        private TextView countryName;

        public ViewHolder (View view) {
            imageView = (ImageView)view.findViewById(R.id.image_view);
            countryName = (TextView)view.findViewById(R.id.text_view);
        }
    }
}
