package eventhorizon.smartrefrigerator;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by marcusmotill on 11/29/15.
 */
public class ThumbnailSelectorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView gvThumbnails;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    List<Integer> thumbnailIDs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thumbnail_selector_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select a new thumbnail");

        gvThumbnails = (GridView) findViewById(R.id.gvThumbnails);
        TypedArray imgs = getResources().obtainTypedArray(R.array.thumbnails_array_resources);
        thumbnailIDs = new ArrayList<>();
        for (int i = 0; i < imgs.length(); i++) {
            thumbnailIDs.add(imgs.getResourceId(i, 0));
        }
        imgs.recycle();
        gvThumbnails.setOnItemClickListener(this);
        gvThumbnails.setAdapter(new ThumbnailGridViewAdapter(thumbnailIDs, getApplicationContext()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
                return true;
            }

        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", thumbnailIDs.get(position));
        returnIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
