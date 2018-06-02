package com.patryk1007.bluetoothtestapp.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.patryk1007.bluetoothtestapp.data.DataHelper;

import java.io.IOException;

public class ClientConnectionManager extends Thread {

    private final BluetoothSocket socket;
    private final BluetoothDevice device;
    private final ConnectionCallback callback;

    public ClientConnectionManager(BluetoothDevice device, ConnectionCallback callback) {
        this.device = device;
        this.socket = prepareSocket();
        this.callback = callback;
    }

    private BluetoothSocket prepareSocket() {
        try {
            return device.createRfcommSocketToServiceRecord(DataHelper.APP_UUID);
        } catch (IOException e) {
            callback.onError("Socket's create() method failed");
        }
        return null;
    }

    public void run() {
        try {
            callback.onConnecting();
            socket.connect();
        } catch (IOException connectException) {
            closeConnection();
            return;
        }
        callback.onConnected(socket, device);
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
