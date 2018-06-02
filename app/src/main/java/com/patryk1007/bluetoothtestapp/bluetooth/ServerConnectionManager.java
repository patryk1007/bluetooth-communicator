package com.patryk1007.bluetoothtestapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.patryk1007.bluetoothtestapp.data.DataHelper;

import java.io.IOException;

public class ServerConnectionManager extends Thread {

    private final BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private final BluetoothAdapter bluetoothAdapter;
    private final ConnectionCallback callback;

    public ServerConnectionManager(BluetoothAdapter bluetoothAdapter, ConnectionCallback callback) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.serverSocket = prepareSocket();
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
            socket = serverSocket.accept();
        } catch (IOException connectException) {
            closeConnection();
            return;
        }
        callback.onConnected(socket, null);
    }

    public void closeConnection() {
        try {
            serverSocket.close();
            callback.onDisconnected();
        } catch (IOException e) {
            callback.onError("Could not close the client socket");
        }
    }
}
