package eventhorizon.smartrefrigerator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by marcusmotill on 11/28/15.
 */
public class InventoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.rvInventoryList)
    RecyclerView rvInvertoryList;

    @Bind(R.id.srlInventory)
    SwipeRefreshLayout mSwipeRefreshLayout;

    LinearLayoutManager llm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inventory_fragment, null);
        ButterKnife.bind(this, rootView);

        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvInvertoryList.setLayoutManager(llm);
        rvInvertoryList.setHasFixedSize(true);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        getInventory();

        return rootView;
    }

    private void getInventory() {
        ParseQuery<RefrigeratorObject> query = ParseQuery.getQuery(RefrigeratorObject.class);
        query.findInBackground(new FindCallback<RefrigeratorObject>() {
            public void done(List<RefrigeratorObject> refrigeratorObjectList, ParseException e) {
                mSwipeRefreshLayout.setRefreshing(false);
                rvInvertoryList.setAdapter(new InventoryListAdapter(refrigeratorObjectList, getContext()));
            }
        });
    }

    @Override
    public void onRefresh() {
        getInventory();
    }
}
