package com.patryk1007.bluetoothtestapp.activities;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import com.patryk1007.bluetoothtestapp.bluetooth.ClientConnectionManager;

public class ClientActivity extends CommunicationActivity {

    public static final String DATA_DEVICE = "data.device.information";

    private ClientConnectionManager clientConnectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConnection();
        connectToDevice();
    }

    private void initConnection() {
        BluetoothDevice device = getDeviceData();
        if (device != null) {
            clientConnectionManager = new ClientConnectionManager(device, connectionCallback);
        }
    }

    private void connectToDevice() {
        if (clientConnectionManager != null) {
            clientConnectionManager.start();
        }
    }

    private BluetoothDevice getDeviceData() {
        return getIntent().getParcelableExtra(DATA_DEVICE);
    }

}
