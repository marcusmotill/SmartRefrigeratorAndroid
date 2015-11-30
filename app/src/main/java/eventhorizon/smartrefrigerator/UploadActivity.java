package eventhorizon.smartrefrigerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.parse.ParseException;
import com.parse.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by marcusmotill on 11/29/15.
 */
public class UploadActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etItemName)
    EditText etItemName;
    @Bind(R.id.bUpload)
    RelativeLayout bUpload;
    @Bind(R.id.UploadCoordinateLayout)
    CoordinatorLayout uploadCoordinateLayout;
    @Bind(R.id.uploadThumbnailContainer)
    RelativeLayout uploadThumbnailContainer;
    @Bind(R.id.ivUploadItemThumbnail)
    ImageView ivUploadItemThumbnail;

    String QRCode;
    String thumbnailName;
    final private int THUMBNAIL_REQUEST = 4583;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity_main);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            QRCode = getIntent().getExtras().getString("qrCode");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload new QR code");
    }

    @OnClick({R.id.bUpload})
    public void onUploadButtonClicked(View view) {
        if (etItemName.getText().toString().length() > 0) {
            QRCodeObject qrCodeObject = new QRCodeObject();
            qrCodeObject.setQRCode(QRCode);
            qrCodeObject.setItemName(etItemName.getText().toString());
            if (thumbnailName != null) {
                qrCodeObject.setThumbnailName(thumbnailName);
            }
            qrCodeObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        finish();
                    } else {
                        Snackbar.make(uploadCoordinateLayout, "An error occurred.", Snackbar.LENGTH_LONG)
                                .setAction("TRY AGAIN", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onUploadButtonClicked(null);
                                    }
                                }).show();
                    }
                }
            });
        }
    }

    @OnClick({R.id.uploadThumbnailContainer})
    public void onUploadThumbnailContainerClicked(View view) {
        startActivityForResult(new Intent(UploadActivity.this, ThumbnailSelectorActivity.class), THUMBNAIL_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == THUMBNAIL_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                int resultResource = data.getIntExtra("result", 0);
                int position = data.getIntExtra("position", 0);
                ivUploadItemThumbnail.setImageResource(resultResource);
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
}
