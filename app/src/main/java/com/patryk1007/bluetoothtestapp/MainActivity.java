package com.patryk1007.bluetoothtestapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView devicesList;
    private DevicesListAdapter devicesListAdapter;

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
        devicesListAdapter = new DevicesListAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        devicesList.setLayoutManager(layoutManager);
        devicesList.setAdapter(devicesListAdapter);
    }

    private void initButtons() {
        findViewById(R.id.scan_view_start_scan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothStartScan();
            }
        });

        findViewById(R.id.scan_view_paired_devices_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    void bluetoothStartScan() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(devicesScannerReceiver, filter);
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();
    }

    void bluetoothStopScan() {
        bluetoothAdapter.cancelDiscovery();
        unregisterReceiver(devicesScannerReceiver);

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
}
