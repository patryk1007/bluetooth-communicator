package com.patryk1007.bluetoothtestapp.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.patryk1007.bluetoothtestapp.adapters.DevicesListAdapter;
import com.patryk1007.bluetoothtestapp.adapters.ItemClickCallback;
import com.patryk1007.bluetoothtestapp.R;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    private RecyclerView devicesList;
    private DevicesListAdapter devicesListAdapter;
    private TextView scanButton;

    private boolean isScanning;

    final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        initDevicesList();
        initButtons();
    }

    private void initDevicesList() {
        devicesList = findViewById(R.id.scan_view_devices_list);
        devicesListAdapter = new DevicesListAdapter(devicesListCallback());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        devicesList.setLayoutManager(layoutManager);
        devicesList.setAdapter(devicesListAdapter);
    }

    private ItemClickCallback devicesListCallback() {
        return new ItemClickCallback() {
            @Override
            public void onItemLick(BluetoothDevice device) {
                bluetoothStopScan();
                startCommunicationActivity(device);
            }
        };
    }

    private void startCommunicationActivity(BluetoothDevice device) {
        Intent intent = new Intent(this, ClientActivity.class);
        intent.putExtra(ClientActivity.DATA_DEVICE, device);
        startActivity(intent);
    }

    private void initButtons() {
        scanButton = findViewById(R.id.scan_view_start_scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeScanStatus();
                updateScanButton();
            }
        });

        findViewById(R.id.scan_view_paired_devices_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPairedDevices();
            }
        });
    }

    private void setPairedDevices() {
        List<BluetoothDevice> devices = new ArrayList<>(bluetoothAdapter.getBondedDevices());
        devicesListAdapter.setDevices(devices);
    }

    private void changeScanStatus() {
        if (isScanning) {
            bluetoothStopScan();
        } else {
            boolean isBluetoothReadyToScan = checkScanPermissionAdnShowErrorMessageIfNecesery();
            if (isBluetoothReadyToScan) {
                bluetoothStartScan();
            }
        }
    }

    private void updateScanButton() {
        scanButton.setText(getString(isScanning ? R.string.scan_view_scanning : R.string.scan_view_start_scan));
    }

    void bluetoothStartScan() {
        bluetoothStopScan();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(devicesScannerReceiver, filter);
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();
        isScanning = true;
    }

    void bluetoothStopScan() {
        if(isScanning) {
            bluetoothAdapter.cancelDiscovery();
            unregisterReceiver(devicesScannerReceiver);
            isScanning = false;
        }
    }

    private final BroadcastReceiver devicesScannerReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                addDeviceToDevicesList(device);
            }
        }
    };

    private void addDeviceToDevicesList(BluetoothDevice device) {
        if (devicesListAdapter != null) {
            devicesListAdapter.addDevice(device);
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
        ActivityCompat.requestPermissions(ScanActivity.this,
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
