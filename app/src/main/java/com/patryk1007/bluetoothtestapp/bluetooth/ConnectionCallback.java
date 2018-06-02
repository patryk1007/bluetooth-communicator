package com.patryk1007.bluetoothtestapp.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public interface ConnectionCallback {

    void onConnecting();

    void onWaitingForDevice();

    void onConnected(BluetoothSocket socket, BluetoothDevice device);

    void onDisconnected();

    void onError(String errorMessage);
}
