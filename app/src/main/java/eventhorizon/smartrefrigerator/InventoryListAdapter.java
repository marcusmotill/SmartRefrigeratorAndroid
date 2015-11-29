package eventhorizon.smartrefrigerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by marcusmotill on 11/28/15.
 */
public class InventoryListAdapter extends BaseAdapter {


    private List<RefrigeratorObject> refrigeratorObjectList;
    private Context context;

    public InventoryListAdapter(List<RefrigeratorObject> refrigeratorObjectList, Context context) {
        this.refrigeratorObjectList = refrigeratorObjectList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return refrigeratorObjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return refrigeratorObjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RefrigeratorObject refrigeratorObject = (RefrigeratorObject) getItem(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.inventory_list_item, parent, false);
        TextView itemName = (TextView) rowView.findViewById(R.id.tvItemName);

        itemName.setText(refrigeratorObject.getItemName());

        return rowView;
    }


}
