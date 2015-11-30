package eventhorizon.smartrefrigerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by marcusmotill on 11/29/15.
 */
public class ThumbnailGridViewAdapter extends BaseAdapter {

    private List<Integer> thumbnails;
    private Context context;


    public ThumbnailGridViewAdapter(List<Integer> thumbnails, Context context) {
        this.thumbnails = thumbnails;
        this.context = context;
    }


    @Override
    public int getCount() {
        return thumbnails.size();
    }

    @Override
    public Object getItem(int position) {
        return thumbnails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int imageID = (int) getItem(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.thumbnail_gridview_item, parent, false);

        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.ivThumbnail);
        thumbnail.setImageResource(imageID);

        return rowView;
    }

}
