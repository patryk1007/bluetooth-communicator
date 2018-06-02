package com.patryk1007.bluetoothtestapp.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.patryk1007.bluetoothtestapp.R;

public class ChooseActivity extends AppCompatActivity {

    final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        initButtons();
    }

    private void initButtons() {
        findViewById(R.id.choose_start_client_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChoosenActivity(new Intent(ChooseActivity.this, ScanActivity.class));
            }
        });
        findViewById(R.id.choose_start_server_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChoosenActivity(new Intent(ChooseActivity.this, ServerActivity.class));
            }
        });
    }

    private void startChoosenActivity(Intent intent) {
        boolean isAppSetUp = checkScanPermissionAdnShowErrorMessageIfNecesery();
        if (isAppSetUp) {
            startActivity(intent);
        }
    }


    private boolean checkScanPermissionAdnShowErrorMessageIfNecesery() {
        if (!isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
            return false;
        }
        if (!arePermissionGranted()) {
            requestBluetoothPermission();
            return false;
        }
        if (!isLocationEnabled()) {
            Toast.makeText(this, getString(R.string.scan_view_location_disabled_error_message), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean isBluetoothEnabled() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    private boolean arePermissionGranted() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestBluetoothPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1007);
    }

    private boolean isLocationEnabled() {
        int locationMode;
        try {
            locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            return false;
        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }
}
