package com.patryk1007.bluetoothtestapp.bluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CommunicationManager extends Thread {

    private final BluetoothSocket socket;
    private final InputStream inStream;
    private final OutputStream outStream;
    private CommunicationCallback callback;

    public CommunicationManager(BluetoothSocket socket, CommunicationCallback callback) {
        this.socket = socket;
        this.callback = callback;
        inStream = getInputStream();
        outStream = getOutputStream();
    }

    private InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            callback.onSocketError("Error occurred when creating input stream");
        }
        return null;
    }

    private OutputStream getOutputStream() {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            callback.onSocketError("Error occurred when creating output stream");
        }
        return null;
    }

    public void run() {
        while (true) {
            try {
                byte[] messageBuffer = new byte[1024];
                int numBytes = inStream.read(messageBuffer);
                String message = new String(messageBuffer, StandardCharsets.UTF_8);
                callback.onRead(message);
            } catch (IOException e) {
                callback.onReadError("Input stream was disconnected " + e.getMessage());
                break;
            }
        }
    }

    public void writeMessage(String message) {
        byte[] bytes = message.getBytes(Charset.forName("UTF-8"));
        try {
            outStream.write(bytes);
            callback.onWrite(message);
        } catch (IOException e) {
            callback.onWriteError("Error occurred when sending data " + e.getMessage());
        }
    }
}
