package eventhorizon.smartrefrigerator;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by marcusmotill on 12/1/15.
 */
public class QRCodesListAdapter extends RecyclerView.Adapter<QRCodesListAdapter.QRCodeItemViewHolder> {

    public static class QRCodeItemViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView itemName;
        ImageView thumbnail;
        Button bAdd;

        QRCodeItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.crQRCode);
            itemName = (TextView) itemView.findViewById(R.id.tvItemName);
            thumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            bAdd = (Button) itemView.findViewById(R.id.bAdd);
        }
    }

    private List<QRCodeObject> qrCodeObjects;
    private Context context;
    private RecyclerViewClickListener itemListener;

    public QRCodesListAdapter(List<QRCodeObject> qrCodeObjects, Context context, RecyclerViewClickListener itemListener) {
        this.qrCodeObjects = qrCodeObjects;
        this.context = context;
        this.itemListener = itemListener;
    }

    @Override
    public QRCodeItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qrcode_list_item, viewGroup, false);
        QRCodeItemViewHolder pvh = new QRCodeItemViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(QRCodeItemViewHolder inventoryItemViewHolder, final int i) {
        QRCodeObject qrCodeObject = qrCodeObjects.get(i);

        inventoryItemViewHolder.itemName.setText(qrCodeObject.getItemName());
        if (qrCodeObject.getThumbnailName() != null)
            inventoryItemViewHolder.thumbnail.setImageResource(getResourceId(qrCodeObject.getThumbnailName(), "drawable", context.getPackageName()));
        inventoryItemViewHolder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.recyclerViewListClicked(null, i, 0);
            }
        });
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
        return qrCodeObjects.size();
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