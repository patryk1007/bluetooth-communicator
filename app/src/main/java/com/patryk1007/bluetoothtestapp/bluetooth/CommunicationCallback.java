package com.patryk1007.bluetoothtestapp.bluetooth;

public interface CommunicationCallback {

    void onWrite(String message);

    void onRead(String message);

    void onWriteError(String message);

    void onReadError(String message);

    void onSocketError(String message);

}
