package eventhorizon.smartrefrigerator;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by marcusmotill on 11/28/15.
 */
public class ScanFragment extends Fragment implements ZBarScannerView.ResultHandler {

    @Bind(R.id.scannerView)
    ZBarScannerView zBarScannerView;
    @Bind(R.id.ScannerCoordinateLayout)
    CoordinatorLayout coordinatorLayout;

    private static final int REQUEST_CAMERA = 1;
    private static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.FLASHLIGHT
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.scan_fragment, null);
        ButterKnife.bind(this, rootView);


        return rootView;
    }

    private void initScanner() {
        zBarScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        zBarScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onResume() {
        super.onResume();
        verifyStoragePermissions(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        zBarScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        Intent intent = new Intent(getActivity(), UploadActivity.class);
        intent.putExtra("qrCode", rawResult.getContents());
        startActivity(intent);
        //TODO filter for QR codes only rawResult.getBarcodeFormat().getName()

    }

    public void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    PERMISSIONS_CAMERA,
                    REQUEST_CAMERA
            );
        } else {
            initScanner();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults[0] == 0) {
            initScanner();
        } else {
            Snackbar.make(coordinatorLayout, "You must allow Camera permissions.", Snackbar.LENGTH_LONG)
                    .setAction("Allow", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verifyStoragePermissions(getActivity());
                        }
                    }).show();
        }
    }
}
