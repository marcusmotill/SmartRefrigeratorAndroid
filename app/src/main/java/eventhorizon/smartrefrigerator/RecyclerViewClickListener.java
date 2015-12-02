package eventhorizon.smartrefrigerator;

import android.view.View;

/**
 * Created by marcusmotill on 11/30/15.
 */
public interface RecyclerViewClickListener {

    void recyclerViewListClicked(View v, int position, int viewType);
}