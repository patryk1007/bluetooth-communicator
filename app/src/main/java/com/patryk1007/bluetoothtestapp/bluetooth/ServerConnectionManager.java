package com.patryk1007.bluetoothtestapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;

import com.patryk1007.bluetoothtestapp.DataHelper;

import java.io.IOException;

public class ServerConnectionManager extends Thread {

    private final BluetoothServerSocket socket;
    private final BluetoothAdapter bluetoothAdapter;
    private final ConnectionCallback callback;

    public ServerConnectionManager(BluetoothAdapter bluetoothAdapter, ConnectionCallback callback) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.socket = prepareSocket();
        this.callback = callback;
    }

    private BluetoothServerSocket prepareSocket() {
        try {
            return bluetoothAdapter.listenUsingRfcommWithServiceRecord("Test123", DataHelper.APP_UUID);
        } catch (IOException e) {
            callback.onError("Socket's create() method failed");
        }
        return null;
    }

    public void run() {
        try {
            callback.onConnecting();
            socket.accept();
        } catch (IOException connectException) {
            closeConnection();
            return;
        }
        callback.onConnected(null);
    }

    public void closeConnection() {
        try {
            socket.close();
            callback.onDisconnected();
        } catch (IOException e) {
            callback.onError("Could not close the client socket");
        }
    }
}
