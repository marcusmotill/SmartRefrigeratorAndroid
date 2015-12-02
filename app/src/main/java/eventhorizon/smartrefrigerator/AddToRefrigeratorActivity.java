package eventhorizon.smartrefrigerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by marcusmotill on 12/1/15.
 */
public class AddToRefrigeratorActivity extends AppCompatActivity implements RecyclerViewClickListener {

    @Bind(R.id.rvCodes)
    RecyclerView rvCodes;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etItemName)
    EditText etItemName;
    @Bind(R.id.bAdd)
    Button bAdd;
    @Bind(R.id.ivThumbnail)
    ImageView ivThumbnail;
    @Bind(R.id.AddToCoordinateLayout)
    CoordinatorLayout AddToCoordinateLayout;

    List<QRCodeObject> qrCodeObjects = new ArrayList<>();
    LinearLayoutManager llm;
    String thumbnailName;
    final private int THUMBNAIL_REQUEST = 4583;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_refrigerator_activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add item to Refrigerator");


        llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvCodes.setLayoutManager(llm);
        rvCodes.setHasFixedSize(true);

        ParseQuery<QRCodeObject> query = ParseQuery.getQuery(QRCodeObject.class);
        query.findInBackground(new FindCallback<QRCodeObject>() {
            public void done(List<QRCodeObject> qrCodeObjectList, ParseException e) {
                qrCodeObjects = qrCodeObjectList;
                rvCodes.setAdapter(new QRCodesListAdapter(qrCodeObjectList, getApplicationContext(), AddToRefrigeratorActivity.this));
            }
        });
    }

    @OnClick({R.id.bAdd})
    public void onAddButtonClicked(View view) {
        if (etItemName.getText().toString().length() > 0) {
            RefrigeratorObject customObject = new RefrigeratorObject();
            customObject.setItemName(etItemName.getText().toString());
            if (thumbnailName != null) {
                customObject.setThumbnailName(thumbnailName);
            }
            customObject.setIsCustom(true);
            customObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        finish();
                    } else {
                        Snackbar.make(AddToCoordinateLayout, "An error occurred.", Snackbar.LENGTH_LONG)
                                .setAction("TRY AGAIN", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onAddButtonClicked(null);
                                    }
                                }).show();
                    }
                }
            });
        }
    }

    @OnClick({R.id.ivThumbnail})
    public void onThumbnailClicked(View view) {
        startActivityForResult(new Intent(AddToRefrigeratorActivity.this, ThumbnailSelectorActivity.class), THUMBNAIL_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == THUMBNAIL_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                int resultResource = data.getIntExtra("result", 0);
                int position = data.getIntExtra("position", 0);
                ivThumbnail.setImageResource(resultResource);
                String[] thumbnailNames = getResources().getStringArray(R.array.thumbnails_array_names);
                thumbnailName = thumbnailNames[position];
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                onBackPressed();
                return true;
            }

        }

        return true;
    }

    @Override
    public void recyclerViewListClicked(View v, int position, int viewType) {
        RefrigeratorObject customObject = new RefrigeratorObject();
        QRCodeObject selectedObject = qrCodeObjects.get(position);
        customObject.setQRCode(selectedObject.getQRCode());
        customObject.setIsCustom(false);
        customObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    finish();
                } else {
                    Snackbar.make(AddToCoordinateLayout, "An error occurred.", Snackbar.LENGTH_LONG)
                            .setAction("TRY AGAIN", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onAddButtonClicked(null);
                                }
                            }).show();
                }
            }
        });
    }
}
