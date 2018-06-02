package com.patryk1007.bluetoothtestapp.activities;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

import com.patryk1007.bluetoothtestapp.bluetooth.ServerConnectionManager;

public class ServerActivity extends CommunicationActivity {

    final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private ServerConnectionManager serverConnectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConnection();
        connectToDevice();
    }

    private void initConnection() {
        serverConnectionManager = new ServerConnectionManager(bluetoothAdapter, connectionCallback);
    }

    private void connectToDevice() {
        if (serverConnectionManager != null) {
            serverConnectionManager.start();
        }
    }
}
