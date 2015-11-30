package eventhorizon.smartrefrigerator;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by marcusmotill on 11/28/15.
 */

public class InventoryListAdapter extends RecyclerView.Adapter<InventoryListAdapter.InventoryItemViewHolder> {

    public static class InventoryItemViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView itemName;
        ImageView thumbnail;

        InventoryItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvInventoryItem);
            itemName = (TextView) itemView.findViewById(R.id.tvItemName);
            thumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
        }
    }

    private List<RefrigeratorObject> refrigeratorObjectList;
    private Context context;

    public InventoryListAdapter(List<RefrigeratorObject> refrigeratorObjectList, Context context) {
        this.refrigeratorObjectList = refrigeratorObjectList;
        this.context = context;
    }

    @Override
    public InventoryItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inventory_list_item, viewGroup, false);
        InventoryItemViewHolder pvh = new InventoryItemViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(InventoryItemViewHolder inventoryItemViewHolder, int i) {
        RefrigeratorObject refrigeratorObject = refrigeratorObjectList.get(i);

        inventoryItemViewHolder.itemName.setText(refrigeratorObject.getItemName());
        if (refrigeratorObject.getThumbnailName() != null)
            inventoryItemViewHolder.thumbnail.setImageResource(getResourceId(refrigeratorObject.getThumbnailName(), "drawable", context.getPackageName()));

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return refrigeratorObjectList.size();
    }


    public int getResourceId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}

