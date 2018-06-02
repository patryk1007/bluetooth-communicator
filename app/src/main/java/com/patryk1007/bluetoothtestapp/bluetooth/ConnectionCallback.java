package com.patryk1007.bluetoothtestapp.bluetooth;

import android.bluetooth.BluetoothDevice;

public interface ConnectionCallback {

    void onConnecting();
    void onWaitingForDevice();
    void onConnected(BluetoothDevice device);
    void onDisconnected();
    void onError(String errorMessage);
}
